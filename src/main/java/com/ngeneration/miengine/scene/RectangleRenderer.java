package com.ngeneration.miengine.scene;

import com.ngeneration.miengine.graphics.Color;
import com.ngeneration.miengine.math.Rectangle;
import com.ngeneration.miengine.math.Vector2;

public class RectangleRenderer extends Renderer {

	public Color color = Color.WHITE.cpy();
	public Vector2 size = new Vector2(50);

	@Override
	public void render() {
		var batch = SpriteRenderer.getBatch();
		batch.setProjectionMatrix(GameObject.getRootCamera().combined);
		batch.begin();
		batch.setColor(color);
		batch.fillRect(transform.getLocationX(), transform.getLocationY(), size.x * transform.getScaleX(),
				size.y * transform.getScaleY());
		batch.end();
	}

	@Override
	public void getLocalBounds(Rectangle rect) {
		rect.set(0, 0, size.x, size.y);
	}

}
