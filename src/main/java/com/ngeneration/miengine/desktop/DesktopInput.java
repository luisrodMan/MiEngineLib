package com.ngeneration.miengine.desktop;

import java.util.HashMap;
import java.util.Map;

import com.ngeneration.miengine.Engine;
import com.ngeneration.miengine.Input;

public class DesktopInput implements Input {

	private float y;
	private float x;

	private Map<Integer, KeyState> keys = new HashMap<>();

	private static class KeyState {
		private boolean pressed, justPressed;
	}

	@Override
	public float getX() {
		return x;
	}

	@Override
	public float getY() {
		return Engine.graphics.getHeight() - y;
	}

	@Override
	public boolean isKeyPressed(int key) {
		return keys.computeIfAbsent(key, key1 -> new KeyState()).pressed;
	}

	@Override
	public boolean isKeyJustPressed(int key) {
		return keys.computeIfAbsent(key, key1 -> new KeyState()).justPressed;
	}

	public void updateKey(int key, boolean b) {
		var val = keys.computeIfAbsent(key, key1 -> new KeyState());
		val.justPressed = !val.pressed && b;
		val.pressed = b;
	}

	public void update() {
		keys.values().forEach(k -> k.justPressed = false);
	}

	@Override
	public boolean isTouched() {
		return false;
	}

	public void setCursorPosition(float mx, float my) {
		this.x = mx;
		this.y = my;
	}

}
