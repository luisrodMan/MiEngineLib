package com.ngeneration.miengine.graphics;

public class Color {

	public static final Color WHITE = new Color(255, 255, 255);
	public static final Color RED = new Color(255, 0, 0);
	public static final Color BLUE = new Color(0, 0, 255);
	public static final Color BLACK = new Color(0, 0, 0);
	public static final Color GREEN = new Color(0, 255, 0);
	public static final Color YELLOW = new Color(255, 255, 0);
	public static final Color CYAN = new Color(0, 255, 255);
	public static final Color MAGENTA = new Color(255, 0, 255);
	public static final Color GRAY = new Color(50, 50, 50);
	public static final Color LIGTH_GRAY = new Color(150, 150, 150);
	public static final Color DARK_GRAY = new Color(45, 45, 45);

	private int value;

	public Color() {
	}

	public Color(int value) {
		this.value = value;
	}

	public Color(int r, int g, int b) {
		this(r, g, b, 255);
	}

	public Color(int r, int g, int b, int a) {
		value |= (a & 255) << 24;
		value |= (r & 255) << 16;
		value |= (g & 255) << 8;
		value |= b & 255;
	}

	public Color(Color color) {
		value = color.value;
	}

	public float getAlpha() {
		return ((value >> 24) & 255) / 255.0f;
	}

	public float getRed() {
		return ((value >> 16) & 255) / 255.0f;
	}

	public float getGreen() {
		return ((value >> 8) & 255) / 255.0f;
	}

	public float getBlue() {
		return (value & 255) / 255.0f;
	}

	public int toInt() {
		return value;
	}

	public Color darker() {
		return glow(-5);
	}

	public Color lighter() {
		return glow(5);
	}

	private Color glow(float glow) {
		float f = glow;
		return new Color((int) (((value >> 16) & 255) + f), (int) (((value >> 8) & 255) + f),
				(int) ((value & 255) + f));
	}

	public Color cpy() {
		return new Color(value);
	}

}
