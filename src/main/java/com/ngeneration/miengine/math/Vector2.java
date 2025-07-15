package com.ngeneration.miengine.math;

import lombok.ToString;

@ToString
public class Vector2 {

	public static Vector2 UP() {
		return new Vector2(0, 1);
	}

	public static Vector2 Left() {
		return new Vector2(-1, 0);
	}

	public static Vector2 Right() {
		return new Vector2(1, 0);
	}

	public float x, y;

	public Vector2() {
	}

	public Vector2(float x, float y) {
		set(x, y);
	}

	public Vector2(float v) {
		set(v, v);
	}

	public Vector2(Vector3 location) {
		set(location.x, location.y);
	}

	public Vector2(Vector2 position) {
		set(position);
	}

	public Vector2 set(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public Vector2 set(Vector3 v) {
		x = v.x;
		y = v.y;
		return this;
	}

	public Vector2 sub(Vector2 other) {
		return sub(other.x, other.y);
	}

	private Vector2 sub(float x2, float y2) {
		x -= x2;
		y -= y2;
		return this;
	}

	public Vector2 cpy() {
		return new Vector2(x, y);
	}

	public Vector2 set(float[] floats) {
		return set(floats[0], floats[1]);
	}

	public float dist2(Vector2 location) {
		return dist2(location.x, location.y);
	}

	public float dist2(Vector3 location) {
		return dist2(location.x, location.y);
	}

	public float dist2(float x2, float y2) {
		return (x2 - x) * (x2 - x) + (y2 - y) * (y2 - y);
	}

	public Vector2 sub(Vector3 location) {
		return sub(location.x, location.y);
	}

	public float length2() {
		return x * x + y * y;
	}

	public float length() {
		return (float) Math.sqrt(length2());
	}

	public Vector2 nor() {
		float length = length();
		if (length == 0) {
			length = 1;
		}
		x /= length;
		y /= length;
		return this;
	}

	public Vector2 set(Vector2 nor) {
		return set(nor.x, nor.y);
	}

	public Vector2 scl(float sql) {
		x *= sql;
		y *= sql;
		return this;
	}

	public Vector2 add(Vector2 offset) {
		add(offset.x, offset.y);
		return this;
	}

	public Vector2 add(float x2, float y2) {
		x += x2;
		y += y2;
		return this;
	}

	public Vector2 addScl(Vector2 dimension, Vector2 sql) {
		add(dimension.x * sql.x, dimension.y * sql.y);
		return this;
	}

	public Vector2 addScl(Vector3 dimension, Vector2 sql) {
		add(dimension.x * sql.x, dimension.y * sql.y);
		return this;
	}

	public Vector2 addScl(Vector2 dimension, Vector3 sql) {
		add(dimension.x * sql.x, dimension.y * sql.y);
		return this;
	}

	public Vector2 addScl(Vector3 dimension, Vector3 sql) {
		add(dimension.x * sql.x, dimension.y * sql.y);
		return this;
	}

	public Vector2 addScl(Vector2 dimension, float sql) {
		add(dimension.x * sql, dimension.y * sql);
		return this;
	}

	public Vector2 mul(Vector2 value) {
		return set(x * value.x, y * value.y);
	}

	public float dot(Vector2 other) {
		return x * other.x + y * other.y;
	}

	public float toAngle() {
		var v = (float) (Math.atan2(y, x) * MathUtils.TO_DEGREES);
		return v < 0 ? -v : 360 - v;
	}

	public Vector2 rotate(float degrees) {
		if (degrees == 0)
			return this;
		float radians = degrees * MathUtils.TO_RADIANS;
		float sin = (float) Math.sin(radians);
		float cos = (float) Math.cos(radians);

		// sin cos x
		// cos -sin y
		float xx = x;
		x = cos * x + sin * y;
		y = -sin * xx + cos * y;
		return this;
	}

	public Vector2 rotate90() {
		var xx = x;
		x = y;
		y = -xx;
		return this;
	}

	public Vector2 scl(float scaleX, float scaleY) {
		x *= scaleX;
		y *= scaleY;
		return this;
	}

	public Vector2 scl(Vector3 scale) {
		return scl(scale.x, scale.y);
	}

	public Vector2 prj(Vector2 hp) {
		x = x * hp.x;
		y = y * hp.y;
		return this;
	}

	public Vector2 setLength(float random) {
		nor();
		return scl(random);
	}

}
