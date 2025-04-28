package com.ngeneration.miengine.scene.ui;

import com.ngeneration.miengine.graphics.Color;
import com.ngeneration.miengine.scene.SpriteRenderer;

public class Panel extends CanvasComponent {

	public Panel() {
		color = Color.BLACK.cpy();
		size.set(200, 200);
	}

	@Override
	public void render() {
		var batch = SpriteRenderer.getBatch();
		batch.begin();
		batch.setColor(color);
		batch.fillRect(transform.getLocationX(), transform.getLocationY(), size.x, size.y);
		batch.end();
	}

}