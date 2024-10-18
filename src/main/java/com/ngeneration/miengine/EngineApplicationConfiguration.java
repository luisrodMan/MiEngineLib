package com.ngeneration.miengine;

import lombok.Data;

@Data
public class EngineApplicationConfiguration {

	private String title;
	private int height;
	private int width;

	public void setWindowedMode(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public void useVsync(boolean b) {
		// TODO Auto-generated method stub
		
	}

	public static Object getDisplayMode() {
		// TODO Auto-generated method stub
		return null;
	}

}
