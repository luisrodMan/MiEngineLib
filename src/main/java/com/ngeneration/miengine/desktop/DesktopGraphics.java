package com.ngeneration.miengine.desktop;

import com.ngeneration.miengine.graphics.Graphics;

public class DesktopGraphics implements Graphics {

	private int width;
	private int height;
	private float deltaTime;
	private float time;

	public DesktopGraphics(int width, int height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public float getDeltaTime() {
		return deltaTime;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	public void setDelta(float delta) {
		this.deltaTime = delta;
	}

	public void setTime(float time) {
		this.time = time;
	}

}
