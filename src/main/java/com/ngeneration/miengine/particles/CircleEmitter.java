package com.ngeneration.miengine.particles;

import com.ngeneration.miengine.math.MathUtils;
import com.ngeneration.miengine.math.Vector2;

public class CircleEmitter extends EmitterShape2D {

	public float radius = 100;

	@Override
	public void getPosition(float x1, float x2, float sx, float sy, float rotation, Vector2 result) {
		result.set(MathUtils.random(0, radius), 0).rotate(MathUtils.random(360.0f)).add(x1, x2);
	}
}
