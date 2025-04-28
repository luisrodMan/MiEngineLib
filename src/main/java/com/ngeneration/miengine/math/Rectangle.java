package com.ngeneration.miengine.math;

import lombok.ToString;

@ToString
public class Rectangle {

	public float x;
	public float y;
	public float width;
	public float height;

	public Rectangle() {
	}

	public Rectangle(float width, float height) {
		this.width = width;
		this.height = height;
	}

	public Rectangle(Rectangle other) {
		set(other.x, other.y, other.width, other.height);
	}

	public Rectangle(float x, float y, float width, float height) {
		set(x, y, width, height);
	}

	public Rectangle set(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		return this;
	}

	public boolean contains(Vector2 pofloat) {
		return contains(pofloat.getX(), pofloat.getY());
	}

	public boolean contains(float x, float y) {
		return x >= this.x && x <= this.x + width && y >= this.y && y <= this.y + height;
	}

	public Vector2 getLocation() {
		return new Vector2(x, y);
	}

	public boolean intersects(Rectangle other) {
		return Math.max(x + width, other.x + other.width) - Math.min(x, other.x) < width + other.width
				&& Math.max(y + height, other.y + other.height) - Math.min(y, other.y) < height + other.height;
	}

	public Rectangle setSize(float width, float height) {
		set(x, y, width, height);
		return this;
	}

	public Rectangle set(RectangleI other) {
		return set(other.x, other.y, other.getWidth(), other.getHeight());
	}

	public Rectangle set(Rectangle other) {
		return set(other.x, other.y, other.width, other.height);
	}

	public Rectangle setLocation(Vector2 location) {
		return setLocation(location.getX(), location.getY());
	}

	public Rectangle setLocation(float x2, float y2) {
		return set(x2, y2, width, height);
	}

	public Rectangle clamp(Rectangle other) {
		float x2 = MathUtils.clamp(x + width, other.x, other.x + other.width);
		float y2 = MathUtils.clamp(y + height, other.y, other.y + other.height);
		x = MathUtils.clamp(x, other.x, other.x + other.width);
		y = MathUtils.clamp(y, other.y, other.y + other.height);
		width = x2 - x;
		height = y2 - y;
		return this;
	}

	public Vector2 getDimension() {
		return new Vector2(width, height);
	}

	public boolean overlaps(Rectangle dropRectangle) {
		return intersects(dropRectangle);
	}

	public Rectangle set(Vector3 location, float size) {
		set(location.x, location.y, size, size);
		return this;
	}

	public void set(float[] values) {
		set(values[0], values[1], values[2], values[3]);
	}

	public Vector2 getSize() {
		return new Vector2(width, height);
	}

}
