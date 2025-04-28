package com.ngeneration.miengine.graphics;

import com.ngeneration.miengine.math.Matrix;
import com.ngeneration.miengine.math.Point;
import com.ngeneration.miengine.math.Vector2;
import com.ngeneration.miengine.math.Vector3;

public class OrthographicCamera extends Camera {

	private boolean yDown;

	public OrthographicCamera() {

	}

	public OrthographicCamera(float viewportWidth, float viewportHeight) {
		setToOrtho(false, viewportWidth, viewportHeight);
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
		this.yDown = yDown;
		this.viewportWidth = viewportWidth;
		this.viewportHeight = viewportHeight;
		projection.setToOrtho(0, viewportWidth, 0, viewportHeight, -10, 10);
	}

	public void translate(float x, float y) {

	}

	public void toLocal(Vector3 world) {
		view.transform(world);
	}

	private static Vector3 helper1 = new Vector3();
	private static Vector3 helper2 = new Vector3();

	public void ToWorld(Vector2 local) {
		toLocal(helper1.set(Vector3.ZERO));
		local.sub(helper1);
		local.scl(1.0f / scale.x);
	}

	public void ToWorld(Vector3 local) {
		toLocal(helper1.set(Vector3.ZERO));
		local.sub(helper1);
		local.mul(1.0f / scale.x);
	}

	public void zoom(Vector2 local, float scale, float centeringFactor) {
		zoom(helper2.set(local, 0), scale, centeringFactor);
	}

	public void zoom(Vector3 local, float scale, float centeringFactor) {
		zoom(local.x, local.y, local.z, scale, centeringFactor);
	}

	public void zoom(float x, float y, float z, float scale, float centeringFactor) {
		var world = helper2.set(x, y, z);
		ToWorld(world);
		// to world
		position.set(world);
		update();
		super.scale.set(scale);
		update();

		var newWorld = getWorld2D(x, y);

		position.sub(newWorld.sub(position));
		update();
	}

	public Vector2 getWorld2D(Vector3 local) {
		return getWorld2D(local.x, local.y);
	}

	public Vector2 getWorld2D(Vector2 local) {
		return getWorld2D(local.x, local.y);
	}

	public Vector2 getWorld2D(Point point) {
		return getWorld2D(point.getX(), point.getY());
	}

	public Vector2 getWorld2D(float x, float y) {
		ToWorld(helper2.set(x, y, 0));
		return new Vector2().set(helper2);
	}

	public Vector3 getLocal(float x, float y) {
		toLocal(helper2.set(x, y, 0));
		return helper2.cpy();
	}

	public Vector3 getLocal(Vector3 world) {
		toLocal(helper2.set(world));
		return helper2.cpy();
	}

	public void toLocal(Vector2 world) {
		toLocal(helper2.set(world, 0));
		world.set(helper2);
	}

	public Vector2 getLocal(Vector2 world) {
		toLocal(helper2.set(world, 0));
		return helper2.toVec2();
	}

	@Override
	public void update() {
		update(false);
	}

	@Override
	public void update(boolean updateFrustrum) {
		setToOrtho(false, viewportWidth, viewportHeight);
		view.setToTranslate(-position.x, -position.y, -position.z);
		var m = new Matrix();
		m.setScale(scale);
		view = view.mul(m);
//		view.setScale(scale);
		combined.set(view).mul(projection);
	}

}
