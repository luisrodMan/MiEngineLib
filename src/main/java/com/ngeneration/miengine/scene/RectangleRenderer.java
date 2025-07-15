package com.ngeneration.miengine.scene;

import com.ngeneration.miengine.graphics.Color;
import com.ngeneration.miengine.math.Rectangle;
import com.ngeneration.miengine.math.Vector2;

public class RectangleRenderer extends Renderer {

	public Color color = Color.WHITE.cpy();
	public Vector2 size = new Vector2(50);
	public Vector2 pivot = new Vector2(0.5f, 0.5f);

	@Override
	public void render() {
		var batch = SpriteRenderer.getBatch();
		batch.setProjectionMatrix(GameObject.getRootCamera().combined);
		batch.begin();
		batch.setColor(color);
		var sx = transform.getScaleX();
		var sy = transform.getScaleY();
		batch.fillRect(transform.getLocationX() - pivot.x * size.x * sx,
				transform.getLocationY() - pivot.y * size.y * sy, size.x * transform.getScaleX(),
				size.y * transform.getScaleY());
		batch.end();
	}

	@Override
	public void getLocalBounds(Rectangle rect) {
		rect.set(-pivot.x * size.x * transform.getScaleX(), -pivot.y * size.y * transform.getScaleY(), size.x, size.y);
	}

}
