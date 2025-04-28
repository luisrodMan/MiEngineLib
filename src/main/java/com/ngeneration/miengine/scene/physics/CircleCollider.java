package com.ngeneration.miengine.scene.physics;

import com.ngeneration.miengine.math.Vector2;
import com.ngeneration.miengine.scene.GameObject;
import com.ngeneration.miengine.scene.SpriteRenderer;

public class CircleCollider extends Collider {

	public float radius = 5;

	private static Vector2 helper1 = new Vector2();

	@Override
	public void render() {
		var batch = SpriteRenderer.getBatch();
		if (!batch.isRendering())
			batch.begin();
		batch.setPenSize(1 / GameObject.getRootCamera().scale.z);
		batch.setColor(Collider.colliderColor);// colliders color !!s

		float scx = transform.getScaleX();
		float scy = transform.getScaleY();
		helper1.set(offset).rotate(transform.getRotationZ()).scl(transform.getScaleX(), transform.getScaleY());

		batch.drawCircle(transform.getLocationX() + helper1.x, transform.getLocationY() + helper1.y, radius * scx, 2);
		batch.end();
	}

}
