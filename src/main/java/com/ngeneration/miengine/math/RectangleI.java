package com.ngeneration.miengine.math;

import lombok.ToString;

@ToString
public class RectangleI {

	public int x;
	public int y;
	public int width;
	public int height;

	public RectangleI() {

	}

	public RectangleI(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public RectangleI(RectangleI other) {
		set(other.x, other.y, other.width, other.height);
	}

	public RectangleI(int x, int y, int width, int height) {
		set(x, y, width, height);
	}

	public RectangleI set(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		return this;
	}

	public boolean contains(Point point) {
		return contains(point.getX(), point.getY());
	}

	public boolean contains(int x, int y) {
		return x >= this.x && x <= this.x + width && y >= this.y && y <= this.y + height;
	}

	public Point getLocation() {
		return new Point(x, y);
	}

	public boolean intersects(RectangleI other) {
		return Math.max(x + width, other.x + other.width) - Math.min(x, other.x) < width + other.width
				&& Math.max(y + height, other.y + other.height) - Math.min(y, other.y) < height + other.height;
	}

	public RectangleI setSize(int width, int height) {
		set(x, y, width, height);
		return this;
	}

	public RectangleI set(RectangleI other) {
		return set(other.x, other.y, other.width, other.height);
	}

	public RectangleI setLocation(Point location) {
		return setLocation(location.getX(), location.getY());
	}

	public RectangleI setLocation(int x2, int y2) {
		return set(x2, y2, width, height);
	}

	public RectangleI clamp(RectangleI other) {
		int x2 = MathUtils.clamp(x + width, other.x, other.x + other.width);
		int y2 = MathUtils.clamp(y + height, other.y, other.y + other.height);
		x = MathUtils.clamp(x, other.x, other.x + other.width);
		y = MathUtils.clamp(y, other.y, other.y + other.height);
		width = x2 - x;
		height = y2 - y;
		return this;
	}

	public Vector2I getDimension() {
		return new Vector2I(width, height);
	}

	public float getWidth() {
		return width;
	}
	public float getHeight() {
		return height;
	}

}
