package com.ngeneration.miengine.math;

import lombok.ToString;

@ToString
public class Vector3 {

	public static final Vector3 ZERO = new Vector3();
	public float x, y, z;

	public Vector3() {
	}

	public Vector3(float x, float y, float z) {
		set(x, y, z);
	}

	public Vector3(Vector3 mouseLocation) {
		set(mouseLocation);
	}

	public Vector3(float v) {
		this(v, v, v);
	}

	public void set(float[] values) {
		set(values[0], values[1], values[2]);
	}

	public Vector3 set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

	public Vector3 add(float x2, float y2) {
		x += x2;
		y += y2;
		return this;
	}

	public Vector3 sub(float x2, float y2) {
		x -= x2;
		y -= y2;
		return this;
	}

	public Vector3 set(Vector3 vec) {
		set(vec.x, vec.y, vec.z);
		return this;
	}

	public void set(float v) {
		set(v, v, v);
	}

	public Vector3 sub(Vector3 local) {
		return sub(local.x, local.y, local.z);
	}

	public Vector3 sub(Vector2 local) {
		return sub(local.x, local.y, 0);
	}

	public Vector3 sub(float x2, float y2, float z2) {
		x -= x2;
		y -= y2;
		z -= z2;
		return this;
	}

	public Vector3 mul(float v) {
		return mul(v, v, v);
	}

	public Vector3 mul(float v1, float v2, float v3) {
		x *= v1;
		y *= v2;
		z *= v3;
		return this;
	}

	public Vector3 add(Vector3 mul) {
		return add(mul.x, mul.y, mul.z);
	}

	public Vector3 add(float x2, float y2, float z2) {
		x += x2;
		y += y2;
		z += z2;
		return this;
	}

	public Vector3 scl(Vector3 scale) {
		mul(scale.x, scale.y, scale.z);
		return this;
	}

	public Vector3 scl(float scale) {
		mul(scale);
		return this;
	}

	public Vector3 cpy() {
		return new Vector3(x, y, z);
	}

	public Vector3 set(Vector2 v) {
		return set(v.x, v.y, z);
	}

	public Vector3 set(Vector2 v, float z) {
		return set(v.x, v.y, z);
	}

	public Vector2 toVec2() {
		return new Vector2(x, y);
	}

	public Vector3 add(Vector2 other) {
		return add(other, 0);
	}

	public Vector3 add(Vector2 other, float z) {
		return add(other.x, other.y, z);
	}

	public Vector3 addScl(Vector2 velocity, float scl) {
		return add(velocity.x * scl, velocity.y * scl);
	}

	public Vector3 inv() {
		x = x == 0 ? 0 : (1.0f / x);
		y = y == 0 ? 0 : (1.0f / y);
		z = z == 0 ? 0 : (1.0f / z);
		return this;
	}

}
