package com.ngeneration.miengine;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

import com.ngeneration.miengine.graphics.Color;

public class ScreenUtils {

	public static void clear(Color black) {
		glClearColor(1.15f, 1f, 0.45f, 0.0f);
		glClear(GL_COLOR_BUFFER_BIT); // clear the framebuffer
	}

}
