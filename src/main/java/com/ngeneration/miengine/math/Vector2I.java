package com.ngeneration.miengine.math;

public class Vector2I {

	public Vector2I(int width, int height) {
		this.x = width;
		this.y = height;
	}

	public Vector2I() {
	}

	public int x, y;

	public void set(int mx, int my) {
		this.x = mx;
		this.y = my;
	}

}
