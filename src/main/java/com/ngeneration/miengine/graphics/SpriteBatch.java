package com.ngeneration.miengine.graphics;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_SHORT;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import com.ngeneration.miengine.graphics.font.BitmapFont;
import com.ngeneration.miengine.math.MathUtils;
import com.ngeneration.miengine.math.Matrix;
import com.ngeneration.miengine.math.Rectangle;
import com.ngeneration.miengine.math.Vector2;

public class SpriteBatch {

	private static final int FLOATS_BY_TEXTURE = 4 * 2 + 4 * 2 + 4 * 4;// vertices, uv, color
	private static ByteBuffer buffer;

	private Color color = Color.WHITE;
	static {
		buffer = ByteBuffer.allocateDirect(4 * 4/* pixels by size */).order(ByteOrder.nativeOrder());
		buffer.put((byte) (255));
		buffer.put((byte) (255));
		buffer.put((byte) (255));
		buffer.put((byte) (255));
		buffer.flip();
	}
	private Texture pixel = new Texture(buffer, 1, 1, Texture.CHANNELS_RGBA);

	private float[] vertices;
	private int verticesIndex = 0;

	private FloatBuffer floatBuffer;

	private int VAO;
	private int VBO;
	private int EBO;

	private float tx;

	private float ty;

	private BitmapFont font;

	private int activeTexture = -1;

	private Rectangle scissor;

	private float penSize = 1;

	private Matrix projectionMatrix;

	private boolean rendering;
	private static Shader defaultShader;
	private Shader shader;

	public void setFont(BitmapFont font) {
		this.font = font;
	}

	public SpriteBatch() {
		this(100);
	}

	public SpriteBatch(int maxTextures) {
		if (defaultShader == null)
			defaultShader = new Shader(vertexShaderSource, fragmentShaderSource);
		shader = defaultShader;

		vertices = new float[maxTextures * FLOATS_BY_TEXTURE];
		floatBuffer = ByteBuffer.allocate(vertices.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
		short[] indices = new short[maxTextures * 6];
		for (int i = 0, j = 0; i < maxTextures * 4; i += 4) {
			indices[j++] = (short) i;
			indices[j++] = (short) (i + 1);
			indices[j++] = (short) (i + 2);
			indices[j++] = (short) (i + 2);
			indices[j++] = (short) (i + 3);
			indices[j++] = (short) (i);
		}

		EBO = glGenBuffers();
		VBO = glGenBuffers();
		VAO = glGenVertexArrays();

		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EBO);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);

		glBindVertexArray(VAO);

		glBindBuffer(GL_ARRAY_BUFFER, VBO);
		glVertexAttribPointer(0, 2, GL_FLOAT, false, 8 * 4, 0);
		glEnableVertexAttribArray(0);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 8 * 4, 2 * 4);
		glEnableVertexAttribArray(1);
		glVertexAttribPointer(2, 4, GL_FLOAT, false, 8 * 4, 4 * 4);
		glEnableVertexAttribArray(2);

//		glBindVertexArray(VAO);
//		glEnableVertexAttribArray(0);
//		glBindBuffer(GL_ARRAY_BUFFER, VBO);
//		glVertexAttribPointer(0, 2, GL_FLOAT, false, 3*4, 0);

		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EBO);

		glBindVertexArray(0);
		glActiveTexture(GL_TEXTURE0); // activate the texture unit first before binding texture
	}

	public void setProjectionMatrix(Matrix matrix) {
		this.projectionMatrix = matrix;
	}

	public void setTranslation(Vector2 location) {
		setTranslation(location.getX(), location.getY());
	}

//	/**
//	 * Top left screen cords.
//	 * 
//	 * @param x
//	 * @param y
//	 * @param width
//	 * @param height
//	 */
//	public void setScissor(int x, int y, int width, int height) {
//		scissor = new Rectangle(x, y, width, height);
//		glScissor(x, this.height - y - height, width, height);
//	}
//
//	public void setScissor(RectangleI rect) {
//		setScissor(rect.x, rect.y, rect.width, rect.height);
//	}

	public Rectangle getScissor() {
		return scissor == null ? null : new Rectangle(scissor);
	}

	public void setTranslation(float x, float y) {
		this.tx = x;
		this.ty = y;
	}

	public float getTranslationX() {
		return tx;
	}

	public float getTranslationY() {
		return ty;
	}

	public void drawString(float x, float y, String text, float size) {
		if (font != null) {
			font.drawString(this, x, y, text, size);
		}
	}

	public void fillRect(float x, float y, float width, float height) {
		drawTexture(pixel, x, y, width, height);
	}

	public void setPenSize(float size) {
		if (size < 0)
			throw new RuntimeException("invalid pen size: " + size);
		this.penSize = size;
	}

	public void drawTexture(Texture image, float x, float y, float width, float height) {
		drawTexture(image, x, y, 0, 0, image.getWidth(), image.getHeight(), width, height);
	}

	public void drawLine(Vector2 point1, Vector2 point2) {
		drawLine(point1.x, point1.y, point2.x, point2.y);
	}

	public void drawLine(float x1, float y1, float x2, float y2) {
		float a = x2 - x1;
		float b = y2 - y1;
		float length = (float) Math.sqrt(a * a + b * b);
		float angle = (float) (Math.atan2(b, a) * 180 / Math.PI);
		drawTexture(pixel, x1, y1, 0, 0, 1, 1, 0, 0.5f, length, penSize, -angle);
	}

	public void drawCircle(float x, float y, float radius, int fidelity) {
		fidelity *= 3;
		var circleArray = getArrayCircle(fidelity);
		for (int j = 0; j < 4; j++) {
			for (int i = 1; i < circleArray.length; i++) {
				int v = (j % 3 == 0 ? 1 : -1);
				drawLine(x + circleArray[i - 1][0] * radius * v, y + circleArray[i - 1][1] * radius * (j > 1 ? -1 : 1),
						x + circleArray[i][0] * radius * v, y + circleArray[i][1] * radius * (j > 1 ? -1 : 1));
			}
		}
	}

	private float[][] getArrayCircle(int fidelity) {
		float[][] array = new float[fidelity + 1][2];
		float step = (float) (Math.PI / 2 / fidelity);
		for (int i = 1; i < array.length - 1; i++) {
			float cos = (float) Math.cos(step * (i));
			float sin = (float) Math.sin(step * (i));
			array[i][0] = (1) * cos + (0) * -sin;
			array[i][1] = (1) * sin + (0) * cos;
		}
		array[0][0] = 1;
		array[0][1] = 0;
		array[array.length - 1][0] = 0;
		array[array.length - 1][1] = 1;
		return array;
	}

	public void fillRect(float x1, float y1, Color color1, float x2, float y2, Color color2, float x3, float y3,
			Color color3, float x4, float y4, Color color4) {
		bindTexture(pixel);
		vertices[verticesIndex++] = tx + x1;
		vertices[verticesIndex++] = ty + y1;
		vertices[verticesIndex++] = 0;
		vertices[verticesIndex++] = 0;
		vertices[verticesIndex++] = color1.getRed();
		vertices[verticesIndex++] = color1.getGreen();
		vertices[verticesIndex++] = color1.getBlue();
		vertices[verticesIndex++] = color1.getAlpha();
		vertices[verticesIndex++] = tx + x2;
		vertices[verticesIndex++] = ty + y2;
		vertices[verticesIndex++] = 0;
		vertices[verticesIndex++] = 1;
		vertices[verticesIndex++] = color2.getRed();
		vertices[verticesIndex++] = color2.getGreen();
		vertices[verticesIndex++] = color2.getBlue();
		vertices[verticesIndex++] = color2.getAlpha();
		vertices[verticesIndex++] = tx + x3;
		vertices[verticesIndex++] = ty + y3;
		vertices[verticesIndex++] = 1;
		vertices[verticesIndex++] = 1;
		vertices[verticesIndex++] = color3.getRed();
		vertices[verticesIndex++] = color3.getGreen();
		vertices[verticesIndex++] = color3.getBlue();
		vertices[verticesIndex++] = color3.getAlpha();
		vertices[verticesIndex++] = tx + x4;
		vertices[verticesIndex++] = ty + y4;
		vertices[verticesIndex++] = 1;
		vertices[verticesIndex++] = 0;
		vertices[verticesIndex++] = color4.getRed();
		vertices[verticesIndex++] = color4.getGreen();
		vertices[verticesIndex++] = color4.getBlue();
		vertices[verticesIndex++] = color4.getAlpha();
		if (verticesIndex >= vertices.length)
			flush();
	}

	public void fillTriangle(int x1, int y1, Color color1, int x2, int y2, Color color2, int x3, int y3, Color color3) {
		bindTexture(pixel);
		vertices[verticesIndex++] = tx + x1;
		vertices[verticesIndex++] = ty + y1;
		vertices[verticesIndex++] = 0;
		vertices[verticesIndex++] = 0;
		vertices[verticesIndex++] = color1.getRed();
		vertices[verticesIndex++] = color1.getGreen();
		vertices[verticesIndex++] = color1.getBlue();
		vertices[verticesIndex++] = color1.getAlpha();
		vertices[verticesIndex++] = tx + x2;
		vertices[verticesIndex++] = ty + y2;
		vertices[verticesIndex++] = 0;
		vertices[verticesIndex++] = 1;
		vertices[verticesIndex++] = color2.getRed();
		vertices[verticesIndex++] = color2.getGreen();
		vertices[verticesIndex++] = color2.getBlue();
		vertices[verticesIndex++] = color2.getAlpha();
		vertices[verticesIndex++] = tx + x3;
		vertices[verticesIndex++] = ty + y3;
		vertices[verticesIndex++] = 1;
		vertices[verticesIndex++] = 1;
		vertices[verticesIndex++] = color3.getRed();
		vertices[verticesIndex++] = color3.getGreen();
		vertices[verticesIndex++] = color3.getBlue();
		vertices[verticesIndex++] = color3.getAlpha();
		vertices[verticesIndex++] = tx + x3;
		vertices[verticesIndex++] = ty + y3;
		vertices[verticesIndex++] = 1;
		vertices[verticesIndex++] = 1;
		vertices[verticesIndex++] = color3.getRed();
		vertices[verticesIndex++] = color3.getGreen();
		vertices[verticesIndex++] = color3.getBlue();
		vertices[verticesIndex++] = color3.getAlpha();
		if (verticesIndex >= vertices.length)
			flush();
	}

	public void drawTexture(Texture image, float x, float y, int sx, int sy, int sw, int sh, float width,
			float height) {
		drawTexture(image, x, y, sx, sy, sw, sh, 0, 0, (float) width / sw, (float) height / sh, 0);
	}

	public void drawTexture(Texture image, float x, float y, int sx, int sy, int sw, int sh, float originx,
			float originy, float scalex, float scaley, float rotation) {
		bindTexture(image);
		x += tx;
		y += ty;
		// image from left top to right bottom
		float tw = image.getWidth();
		float th = image.getHeight();
		float uvL = sx / tw;
		float uvR = uvL + sw / tw;
		float uvB = sy / th;
		float uvT = uvB + sh / th;

		// x cos sin
		// y -sin cos
		// 90 cos 0 sin 1
		// 0,100 -> 0 + 100, 0 + 0 -> 100,0
		float radians = (float) (-rotation * MathUtils.TO_RADIANS);
		float cos = (float) Math.cos(radians);
		float sin = (float) Math.sin(radians);

		float rt = (sw - originx) * scalex;
		float yt = -originy * scaley;
		float ll = -originx * scalex;
		float rb = (sh - originy) * scaley;

		vertices[verticesIndex++] = x + (rt * cos + yt * -sin);
		vertices[verticesIndex++] = y + (rt * sin + yt * cos);
		vertices[verticesIndex++] = uvR;
		vertices[verticesIndex++] = uvB;
		vertices[verticesIndex++] = color.getRed();
		vertices[verticesIndex++] = color.getGreen();
		vertices[verticesIndex++] = color.getBlue();
		vertices[verticesIndex++] = color.getAlpha();

		vertices[verticesIndex++] = x + (rt * cos + rb * -sin);
		vertices[verticesIndex++] = y + (rt * sin + rb * cos);
		vertices[verticesIndex++] = uvR;
		vertices[verticesIndex++] = uvT;
		vertices[verticesIndex++] = color.getRed();
		vertices[verticesIndex++] = color.getGreen();
		vertices[verticesIndex++] = color.getBlue();
		vertices[verticesIndex++] = color.getAlpha();

		vertices[verticesIndex++] = x + (ll * cos + rb * -sin);
		vertices[verticesIndex++] = y + (ll * sin + rb * cos);
		vertices[verticesIndex++] = uvL;
		vertices[verticesIndex++] = uvT;
		vertices[verticesIndex++] = color.getRed();
		vertices[verticesIndex++] = color.getGreen();
		vertices[verticesIndex++] = color.getBlue();
		vertices[verticesIndex++] = color.getAlpha();

		vertices[verticesIndex++] = x + (ll * cos + yt * -sin);
		vertices[verticesIndex++] = y + (ll * sin + yt * cos);
		vertices[verticesIndex++] = uvL;
		vertices[verticesIndex++] = uvB;
		vertices[verticesIndex++] = color.getRed();
		vertices[verticesIndex++] = color.getGreen();
		vertices[verticesIndex++] = color.getBlue();
		vertices[verticesIndex++] = color.getAlpha();

		if (verticesIndex >= vertices.length) {
			flush();
		}
	}

	private void bindTexture(Texture texture) {
		if (activeTexture != texture.getTexID())
			flush();
		glBindTexture(GL_TEXTURE_2D, texture.getTexID());
		activeTexture = texture.getTexID();
	}

	public void flush() {

		if (verticesIndex < 1)
			return;

		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

//		
//		floatBuffer.clear();
////		floatBuffer.put(vertices);
////		floatBuffer.flip();
//		
//		// 2. copy our vertices array in a vertex buffer for OpenGL to use
		glBindBuffer(GL_ARRAY_BUFFER, VBO);
		glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);

		glUniform1i(glGetUniformLocation(shader.getProgram(), "texture1"), 0); // set it manually

//		System.out.println("loc: " + glGetUniformLocation(shaderProgram, "transform1ddd"));
		if (projectionMatrix == null)
			projectionMatrix = new Matrix();
		glUniformMatrix4fv(0, false, projectionMatrix.m);

		glBindVertexArray(VAO);
		int count = 6 * (verticesIndex / FLOATS_BY_TEXTURE);
		glDrawElements(GL_TRIANGLES, count, GL_UNSIGNED_SHORT, 0);

		glBindVertexArray(0);
		verticesIndex = 0;
	}

	public void drawRect(float x, float y, float width, float height, float rotation, float originX, float originY) {
		x += tx;
		y += ty;
		if (rotation == 0) {
			drawRect(x - originX, y - originY, width, height);
			return;
		}

		var vector1 = new Vector2();
		var vector2 = new Vector2();
		float xx = -originX;
		float yy = -originY;

		vector1.set(xx, yy).rotate(rotation).add(x, y);
		vector2.set(xx + width, yy).rotate(rotation).add(x, y);
		drawLine(vector1, vector2);
		vector1.set(xx + width, yy + height).rotate(rotation).add(x, y);
		drawLine(vector2, vector1);
		vector2.set(xx, yy + height).rotate(rotation).add(x, y);
		drawLine(vector1, vector2);
		vector1.set(xx, yy).rotate(rotation).add(x, y);
		drawLine(vector2, vector1);
	}

	public void drawRect(float x, float y, float width, float height) {
		drawLine(x, y, x + width, y);
		drawLine(x, y, x, y + height);
		drawLine(x + width, y, x + width, y + height);
		drawLine(x, y + height, x + width, y + height);
	}

	public Vector2 getTranslation() {
		return new Vector2(tx, ty);
	}

	public void begin() {
		begin(defaultShader);
	}

	public void begin(Shader shader) {
		rendering = true;
		if (shader == null)
			shader = defaultShader;
		this.shader = shader;
		flush();
		shader.begin();
	}

	public void end() {
		flush();
		shader.end();
		rendering = false;
	}

	public boolean isRendering() {
		return rendering;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return new Color(color);
	}

	public static final String vertexShaderSource = "#version 330 core\n" + "layout (location = 0) in vec2 aPos;\n"
			+ "layout (location = 1) in vec2 aUV;\n" + "layout (location = 2) in vec4  aColor;\n"
			+ "out vec4 finalColor;\n" + "out vec2 TexCord;\n" + "uniform mat4 transform;\n" + "void main()\n" + "{\n"
			+ "   " + "   	TexCord = aUV;" + "	    finalColor = aColor;\n"
			+ "   	gl_Position = transform * vec4(aPos, 1.0f, 1.0f);\n" + "}";
	public static final String fragmentShaderSource = "#version 330 core\r\n" + "in vec4 finalColor;\r\n"
			+ "in vec2 TexCord;\n" + "uniform sampler2D texture1;\r\n"

			+ "out vec4 frag_color;\r\n" + "\r\n" + "void main()\r\n" + "{\r\n "
			+ "    frag_color = texture(texture1, TexCord) * finalColor;\r\n" + "}";

}
