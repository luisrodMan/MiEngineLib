package com.ngeneration.miengine.scene;

import com.ngeneration.miengine.graphics.Color;
import com.ngeneration.miengine.graphics.SpriteBatch;
import com.ngeneration.miengine.math.Rectangle;

public class SpriteRenderer extends Renderer {

	private static SpriteBatch batch;

	public static SpriteBatch getBatch() {
		return batch != null ? batch : (batch = new SpriteBatch(100));
	}

	public SpriteResource sprite;
	public Color color = Color.WHITE.cpy();

	@Override
	public void render() {
		batch = getBatch();
		batch.setProjectionMatrix(GameObject.getRootCamera().combined);
		if (sprite != null && sprite.isValidReference()) {
			batch.begin();
			batch.setColor(color);
			var sprite = this.sprite.getRegion();
			batch.drawTexture(sprite.getTexture(), transform.getLocationX(), transform.getLocationY(), sprite.x,
					sprite.y, sprite.width, sprite.height, sprite.origin.x, sprite.origin.y, transform.getScaleX(),
					transform.getScaleY(), transform.getRotationZ());
			batch.end();
		}
	}

	@Override
	public void getLocalBounds(Rectangle rect) {
		rect.setSize(-1, -1);
		if (sprite != null) {
			rect.set(-sprite.getRegion().origin.x, -sprite.getRegion().origin.y, sprite.getRegion().width,
					sprite.getRegion().height);
		}
	}

}
