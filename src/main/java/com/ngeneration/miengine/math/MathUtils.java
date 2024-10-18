package com.ngeneration.miengine.math;

public class MathUtils {

	public static int clamp(int value, int min, int max) {
		return value < min ? min : (value > max ? max : value);
	}

	public static float clamp(float value, float min, float max) {
		return value < min ? min : (value > max ? max : value);
	}

	public static float random(float min, float max) {
		return (float) (min + Math.random() * (max - min));
	}

}
