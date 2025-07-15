package com.ngeneration.miengine.particles;

import com.ngeneration.miengine.math.MathUtils;
import com.ngeneration.miengine.math.Vector2;

public class BoxEmitter extends EmitterShape2D {

	public float width = 200;
	public float height = 200;

	@Override
	public void getPosition(float x, float y, float sx, float sy, float rotation, Vector2 result) {
		result.x = MathUtils.random(-width * 0.5f, width * 0.5f) * sx;
		result.y = MathUtils.random(-height * 0.5f, height * 0.5f) * sy;
		result.rotate(rotation).add(x, y);
	}

}
