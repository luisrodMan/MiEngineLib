package com.ngeneration.miengine.particles;

import com.ngeneration.miengine.math.Vector2;

public class PointEmmiter extends EmitterShape2D {

	@Override
	public void getPosition(float x1, float x2, float sx, float sy, float rotation, Vector2 result) {
		result.set(x1, x2);
	}

}
