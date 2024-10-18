package com.ngeneration.miengine.graphics;

import com.ngeneration.miengine.math.Matrix;
import com.ngeneration.miengine.math.Vector3;

public abstract class Camera {

	public Matrix projection = new Matrix();
	public Matrix view = new Matrix();
	public Matrix combined = new Matrix();
	public float viewportWidth;
	public float viewportHeight;
	public Vector3 position = new Vector3();

	public Camera() {
		view.setToIdentity();
		projection.setToIdentity();
	}

	/**
	 * Recalculates the projection and view matrix of this camera and the Frustum
	 * planes.
	 */
	public abstract void update();

	/**
	 * Recalculates the projection and view matrix of this camera and the Frustum
	 * planes if updateFrustum is true.
	 * 
	 * @param updateFrustrum
	 */
	public abstract void update(boolean updateFrustrum);

}
