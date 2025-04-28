package com.ngeneration.miengine.scene.physics;

import com.ngeneration.miengine.math.Vector2;
import com.ngeneration.miengine.scene.GameObject;
import com.ngeneration.miengine.scene.SpriteRenderer;

public class BoxCollider extends Collider {

	public Vector2 dimension = new Vector2(50, 50);

	private static Vector2 helper1 = new Vector2();

	@Override
	public void render() {
		var batch = SpriteRenderer.getBatch();
		batch.begin();

		batch.setColor(Collider.colliderColor);// colliders color !!s
		batch.setPenSize(1 / GameObject.getRootCamera().scale.z);

		helper1.set(offset).rotate(transform.getRotationZ()).scl(transform.getScaleX(), transform.getScaleY());

		float scx = transform.getScaleX();
		float scy = transform.getScaleY();
		batch.drawRect(transform.getLocationX() + helper1.x, transform.getLocationY() + helper1.y, dimension.x * scx,
				dimension.y * scy, transform.getRotationZ(), dimension.x * 0.5f * scx, dimension.y * 0.5f * scy);

		batch.end();
	}

}
