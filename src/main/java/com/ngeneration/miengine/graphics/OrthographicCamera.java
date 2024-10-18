package com.ngeneration.miengine.graphics;

public class OrthographicCamera extends Camera {

	public OrthographicCamera() {

	}

	public OrthographicCamera(float viewportWidth, float viewportHeight) {

	}

	public void setToOrtho(boolean yDown) {

	}

	/**
	 * Sets this camera to an orthographic projection, centered at (viewportWidth/2,
	 * viewportHeight/2), with the y-axis pointing up or down.
	 * 
	 * @param yDown
	 * @param viewportWidth
	 * @param viewportHeight
	 */
	public void setToOrtho(boolean yDown, float viewportWidth, float viewportHeight) {
		projection.setToOrtho(0, viewportWidth, 0, viewportHeight, -10, 10);
	}

	public void translate(float x, float y) {

	}

	@Override
	public void update() {
		update(false);
	}

	@Override
	public void update(boolean updateFrustrum) {
		setToOrtho(false, viewportWidth, viewportHeight);
		view.setToTranslate(-position.x, -position.y, -position.z);
		combined.set(projection).mul(view);
	}

}
