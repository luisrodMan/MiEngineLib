package com.ngeneration.miengine.graphics;

import com.ngeneration.miengine.math.RectangleI;

public class Sprite {

	private float x, y;
	private float scaleX = 1, scaleY = 1;
	private float originX = 0, originY = 0;
	private float rotation;
	private RectangleI region = new RectangleI();
	private Texture texture;

	public Sprite(Texture texture) {
		this.texture = texture;
		region.set(0, 0, texture.getWidth(), texture.getHeight());
	}

	public void setSize(float width, float height) {
		scaleX = width / region.getWidth();
		scaleY = height / region.getHeight();
	}

	public float getWidth() {
		return region.getWidth()  * scaleX;
	}

	public float getHeight() {
		return region.getHeight() * scaleY;
	}

	public float getY() {
		return y;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void draw(SpriteBatch spriteBatch) {
		spriteBatch.drawTexture(texture, x, y, region.x, region.y, region.width, region.height, originX, originY,
				scaleX, scaleY, rotation);
	}

	public void translateY(float f) {
		y += f;
	}

	public void translateX(float f) {
		x += f;
	}

	public void setCenterX(float x) {
	}

}
