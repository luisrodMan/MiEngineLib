package com.ngeneration.miengine.graphics;

import static org.lwjgl.opengl.GL11.glViewport;

import com.ngeneration.miengine.Engine;
import com.ngeneration.miengine.math.RectangleI;
import com.ngeneration.miengine.math.Vector2;

/**
 * Manages a Camera and determines how world coordinates are mapped to and from
 * the screen.
 */
public class FitViewport {

	private RectangleI viewport = new RectangleI();
	private float worldWidth;
	private float worldHeight;
	private Camera camera;

	/**
	 * Creates a new viewport using a new OrthographicCamera.
	 * 
	 * @param bx
	 * @param by
	 */
	public FitViewport(float worldWidth, float worldHeight) {
		this(worldWidth, worldHeight, new OrthographicCamera());
	}

	public FitViewport(float worldWidth, float worldHeight, Camera camera) {
		this.worldWidth = worldWidth;
		this.worldHeight = worldHeight;
		this.camera = camera;
		camera.viewportWidth = worldWidth;
		camera.viewportHeight = worldHeight;
		update(Engine.graphics.getWidth(), Engine.graphics.getHeight(), false);
	}

	/**
	 * Configures this viewport's screen bounds using the specified screen size and
	 * calls apply(boolean).
	 * 
	 * @param screenWidth
	 * @param screenHeight
	 * @param b
	 */
	public void update(int screenWidth, int screenHeight, boolean b) {
		// recalculate viewport xddx

		// calculate viewport
		float scaleX = screenWidth / worldWidth;
		float scaleY = screenHeight / worldHeight;
		System.out.println("viewport: " + worldWidth + "," + worldHeight);
		System.out.println("viewport2: " + screenWidth + "," + screenHeight);
		if (scaleX < scaleY) {
			float vheight = worldHeight * scaleX;
			viewport.set(0, (int) ((screenHeight - vheight) * 0.5f), screenWidth, (int) vheight);
		} else {
			float vwidth = worldWidth * scaleY;
			viewport.set((int) ((screenWidth - vwidth) * 0.5f), 0, (int) vwidth, screenHeight);
		}
		apply(b);
	}

	public float getScale() {
		return viewport.width / (float) getWorldWidth();
	}

	public float getWorldWidth() {
		return worldWidth;
	}

	public float getWorldHeight() {
		return worldHeight;
	}

	public Camera getCamera() {
		return camera;
	}

	public void unproject(Vector2 touchPos) {

	}

	public void apply() {
		apply(false);
	}

	/**
	 * Applies the viewport to the camera and sets the glViewport.
	 * 
	 * @param center
	 */
	public void apply(boolean center) {
		// glViewport

		camera.update();
		glViewport(viewport.x, viewport.y, viewport.width, viewport.height);
	}

	public void setCamera(com.ngeneration.miengine.scene.Camera camera) {
		this.camera = camera.getCamera();
	}

}
