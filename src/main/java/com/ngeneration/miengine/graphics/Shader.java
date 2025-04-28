package com.ngeneration.miengine.graphics;

import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgramiv;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderiv;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUseProgram;

public class Shader {

	private int shaderProgram;

	public Shader(String vertexSource, String fragmentSource) {
		int vertexShader = createShader(GL_VERTEX_SHADER, vertexSource);
		int[] status = new int[1];
		glGetShaderiv(vertexShader, GL_COMPILE_STATUS, status);

		if (status[0] < 1) {
			String msg = glGetShaderInfoLog(vertexShader);
			System.out.println("vertex compilation problem: " + msg);
		}

		int fragmentShader = createShader(GL_FRAGMENT_SHADER, fragmentSource);
		status[0] = 1;
		glGetShaderiv(fragmentShader, GL_COMPILE_STATUS, status);
		if (status[0] < 1) {
			String msg = glGetShaderInfoLog(fragmentShader);
			System.out.println("fragment compilation problem: " + msg);
		}

		shaderProgram = glCreateProgram();

		glAttachShader(shaderProgram, vertexShader);
		glAttachShader(shaderProgram, fragmentShader);
		glLinkProgram(shaderProgram);

		glGetProgramiv(shaderProgram, GL_LINK_STATUS, status);
		System.out.println("program: " + status[0]);
		if (status[0] < 1) {
			String msg = glGetProgramInfoLog(shaderProgram);
			System.out.println("program: " + msg);
		}

	}

	public int getProgram() {
		return shaderProgram;
	}

	private int createShader(int type, String source) {
		int vertexShader = glCreateShader(type);
		glShaderSource(vertexShader, source);
		glCompileShader(vertexShader);
		return vertexShader;
	}

	public void begin() {
		glUseProgram(shaderProgram);
	}

	public void end() {
		glUseProgram(0);
	}

}
