package com.ngeneration.miengine.math;

public class Matrix {

	public float[] m = new float[16];

	// 0 1 2 3
	// 4 5 6 7
	// 8 9 10 11
	// 12 13 14 15
	public Matrix mul(Matrix other) {
		float m0 = m[0];
		float m1 = m[1];
		float m2 = m[2];
		m[0] = m[0] * other.m[0] + m[1] * other.m[4] + m[2] * other.m[8] + m[3] * other.m[12];
		m[1] = m0 * other.m[1] + m[1] * other.m[5] + m[2] * other.m[9] + m[3] * other.m[13];
		m[2] = m0 * other.m[2] + m1 * other.m[6] + m[2] * other.m[10] + m[3] * other.m[14];
		m[3] = m0 * other.m[3] + m1 * other.m[7] + m2 * other.m[11] + m[3] * other.m[15];

		m0 = m[4];
		m1 = m[5];
		m2 = m[6];
		m[4] = m[4] * other.m[0] + m[5] * other.m[4] + m[6] * other.m[8] + m[7] * other.m[12];
		m[5] = m0 * other.m[1] + m[5] * other.m[5] + m[6] * other.m[9] + m[7] * other.m[13];
		m[6] = m0 * other.m[2] + m1 * other.m[6] + m[6] * other.m[10] + m[7] * other.m[14];
		m[7] = m0 * other.m[3] + m1 * other.m[7] + m2 * other.m[11] + m[7] * other.m[15];

		m0 = m[8];
		m1 = m[9];
		m2 = m[10];
		m[8] = m[8] * other.m[0] + m[9] * other.m[4] + m[10] * other.m[8] + m[11] * other.m[12];
		m[9] = m0 * other.m[1] + m[9] * other.m[5] + m[10] * other.m[9] + m[11] * other.m[13];
		m[10] = m0 * other.m[2] + m1 * other.m[6] + m[10] * other.m[10] + m[11] * other.m[14];
		m[11] = m0 * other.m[3] + m1 * other.m[7] + m2 * other.m[11] + m[11] * other.m[15];

		m0 = m[12];
		m1 = m[13];
		m2 = m[14];
		m[12] = m[12] * other.m[0] + m[13] * other.m[4] + m[14] * other.m[8] + m[15] * other.m[12];
		m[13] = m0 * other.m[1] + m[13] * other.m[5] + m[14] * other.m[9] + m[15] * other.m[13];
		m[14] = m0 * other.m[2] + m1 * other.m[6] + m[14] * other.m[10] + m[15] * other.m[14];
		m[15] = m0 * other.m[3] + m1 * other.m[7] + m2 * other.m[11] + m[15] * other.m[15];
		return this;
	}

	static Vector3 ss = new Vector3();

	public void transform(Vector3 vec) {
		ss.set(vec);
		vec.x = m[0] * ss.x + ss.y * m[4] + ss.z * m[8] + 1 * m[12];
		vec.y = m[1] * ss.x + ss.y * m[5] + ss.z * m[9] + 1 * m[13];
		vec.z = m[2] * ss.x + ss.y * m[6] + ss.z * m[10] + 1 * m[14];
//		1 = m0 * 1 + m1 * m[7] + m2 * m[11] + 1 * m[15];
	}

	public Matrix() {
		setToIdentity();
	}

	public void setToIdentity() {
		m[0] = m[5] = m[10] = m[15] = 1;
		m[1] = m[2] = m[3] = m[4] = m[6] = m[7] = m[8] = m[9] = m[11] = m[12] = m[13] = m[14] = 0;
	}

	public void setToOrtho(float left, float right, float bottom, float top, int near, int far) {
		setToIdentity();
		m[0] = 2 / (right - left);
		m[5] = 2 / (top - bottom);
		m[10] = -2 / (far - near);

		m[12] = -((right + left) / (right - left));
		m[13] = -((top + bottom) / (top - bottom));
		m[14] = -((far + near) / (far - near));
	}

	public void setTranslation(float x, float y, float z) {
		m[12] = x;
		m[13] = y;
		m[14] = z;
	}

	public void setToTranslate(float x, float y, float z) {
		setToIdentity();
		setTranslation(x, y, z);
	}

	public void setScale(Vector3 scale) {
		setScale(scale.x, scale.y, scale.z);
	}

	public void setScale(float x, float y, float z) {
		m[0] = x;
		m[5] = y;
		m[10] = z;
	}

	public void setToScale(float x, float y, float z) {
		setToIdentity();
		setScale(x, y, z);
	}

	public Matrix set(Matrix projection) {
		for (int i = 0; i < 16; i++)
			m[i] = projection.m[i];
		return this;
	}

}
