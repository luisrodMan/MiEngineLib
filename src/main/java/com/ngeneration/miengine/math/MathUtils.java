package com.ngeneration.miengine.math;

import com.ngeneration.miengine.math.Spline.SplinePoint;

public class MathUtils {

	public static final float PI = (float) Math.PI;
	public static final float TO_RADIANS = PI / 180.0f;
	public static final double TO_DEGREES = 180.0f / PI;

	public static int clamp(int value, int min, int max) {
		return value < min ? min : (value > max ? max : value);
	}

	public static float clamp(float value, float min, float max) {
		return value < min ? min : (value > max ? max : value);
	}

	public static float random(float min, float max) {
		return (float) (min + Math.random() * (max - min));
	}

	public static void spline(float t, SplinePoint point1, SplinePoint point2, Vector2 result) {
		Vector2 helper1 = new Vector2();
		Vector2 helper2 = new Vector2();
		float p1x, p1y, p2x, p2y;
		lerp(t, point1.position, helper1.set(point1.position).add(point1.handler2), result);
		p1x = result.x;
		p1y = result.y;
		lerp(t, helper1.set(point1.position).add(point1.handler2), helper2.set(point2.position).add(point2.handler1),
				result);
		p2x = result.x;
		p2y = result.y;
		lerp(t, helper1.set(p1x, p1y), helper2.set(p2x, p2y).add(point2.handler1), result);
		p1x = result.x;// p1
		p1y = result.y;

		lerp(t, helper1.set(point2.position).add(point2.handler1), helper2.set(point2.position), result);
		lerp(t, helper1.set(p2x, p2y), helper2.set(result), result);// p2

		lerp(t, helper1.set(p1x, p1y), helper2.set(result), result);// p
	}

	public static void lerp(float t, Vector2 p1, Vector2 p2, Vector2 result) {
		result.set(lerp(t, p1.x, p2.x), lerp(t, p1.y, p2.y));
	}

	public static float lerp(float t, float v0, float v1) {
		return (1 - t) * v0 + t * v1;
	}

}
