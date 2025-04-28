package com.ngeneration.miengine.graphics.font;

import lombok.Getter;

public class Character {

	@Getter
	private int id;
	public int x;
	public int y;
	public int width;
	public int height;
	public int xOffset;
	public int yOffset;
	public int xAdvance;

	public Character(int id, int x, int y, int width, int height, int xOffset, int yOffset, int xAdvance) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.xAdvance = xAdvance;
	}

}
