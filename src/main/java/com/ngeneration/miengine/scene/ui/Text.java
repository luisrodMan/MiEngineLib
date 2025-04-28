package com.ngeneration.miengine.scene.ui;

import com.ngeneration.miengine.graphics.Color;
import com.ngeneration.miengine.graphics.Shader;
import com.ngeneration.miengine.graphics.SpriteBatch;
import com.ngeneration.miengine.graphics.font.BitmapFontResource;
import com.ngeneration.miengine.scene.SpriteRenderer;

public class Text extends CanvasComponent {

	public static final int LEFT = 1, RIGHT = 2, CENTER = 3;

	private static BitmapFontResource lastFont;
	public BitmapFontResource font;
	public float fontSize = 36;
	public String text = "text";
	private Shader defaultShader;

	public int horizontalAlign = CENTER;

	private int textWidth = 0;

	public Text() {
		this("");
	}

	public Text(String text) {
		this.text = text;
		color = Color.WHITE.cpy();
		if (lastFont != null && lastFont.isValidReference()) {
			font = lastFont;
			fontSize = lastFont.getFont().getMetadata().getSize();
		}
		size.set(100, 50);
		updateTextWidth();
	}

	@Override
	public void onAttached() {
		updateTextWidth();
	}

	@Override
	public void onPropertyUpdated(String propertyName) {
		if (propertyName.equals("font"))
			lastFont = this.font;
		updateTextWidth();
	}

	private void updateTextWidth() {
		textWidth = 0;
		if (text != null && font != null)
			textWidth = font.getFont().getStringWidth(text, fontSize);
	}

	@Override
	public void render() {
		if (defaultShader == null) {
			defaultShader = new Shader(SpriteBatch.vertexShaderSource, "#version 330 core\r\n" + "\r\n"
					+ "in vec4 finalColor;\r\n" + "in vec2 TexCord;\r\n" + "\r\n" + "uniform sampler2D texture1;\r\n"
					+ "\r\n" + "out vec4 frag_color;\r\n" + "const float width = 0.5;\r\n"
					+ "const float edge = 0.1;\r\n" + "const float borderWidth = 0.0;\r\n" // .4 glow effect xd
					+ "const float borderEdge = 0.4;\r\n" // .5
					+ "const vec4 borderColor = vec4(1.0, 0.0, 0.0, 1.0);\r\n"
					+ "const vec2 offset = vec2(0.006, 0.006);" // for no effect set to 0 and borderWidth =0
					+ "\r\n" + "void main() {\r\n" + "	float distance = 1.0 - texture(texture1, TexCord).a;\r\n"
					+ "	float alpha = 1.0 - smoothstep(width, width + edge, distance);\r\n"
					+ "	float distance2 = 1.0 - texture(texture1, TexCord + offset).a;\r\n"
					+ "	float borderAlpha = 1.0 - smoothstep(borderWidth, borderWidth + borderEdge, distance2);\r\n"
					+ " float overallAlpha = alpha + (1.0 - alpha) * borderAlpha;"
					+ " vec4 overallColor = mix(borderColor, finalColor, alpha/overallAlpha);" + "	\r\n"
					+ "	frag_color = vec4(overallColor.rgb, overallAlpha);\r\n" + "}");
		}

		var batch = SpriteRenderer.getBatch();
		batch.begin(defaultShader);

		if (font != null && text != null && font.isValidReference()) {

			float x = -pivot.x * size.x;
//			batch.fillRect(x + transform.getLocationX(), transform.getLocationY() - pivot.y * size.y, size.x, size.y);

			if (horizontalAlign == CENTER) {
				x += (size.x - textWidth) * 0.5f;
			}

			x += transform.getLocationX();

			// material
			batch.setColor(color);
			font.getFont().drawString(batch, x, transform.getLocationY() - pivot.y * size.y, text, fontSize);
		}

		batch.end();
	}

}