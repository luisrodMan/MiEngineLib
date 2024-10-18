package com.ngeneration.miengine.math;

public class Vector2 {

	public float x, y;

	public Vector2(float x, float y) {
		set(x, y);
	}

	public Vector2() {
	}

	public void set(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

}
