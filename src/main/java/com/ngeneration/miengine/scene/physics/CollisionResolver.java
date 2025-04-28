package com.ngeneration.miengine.scene.physics;

import java.util.LinkedList;
import java.util.List;

import com.ngeneration.miengine.Engine;
import com.ngeneration.miengine.math.MathUtils;
import com.ngeneration.miengine.math.Rectangle;
import com.ngeneration.miengine.math.Vector2;
import com.ngeneration.miengine.scene.GameObject;

public class CollisionResolver {

	private Collision[] collisions;

	public CollisionResolver() {
		this(500);
	}

	public CollisionResolver(int maxCollisions) {
		collisions = new Collision[maxCollisions];
		for (int i = 0; i < collisions.length; i++) {
			collisions[i] = new Collision();
		}
	}

	public void update(GameObject object) {
		List<Info> infos = new LinkedList<>();
		collectInfo(object, infos);
		int collisionsCount = collectCollision(infos);
		resolveCollisions(collisions, collisionsCount);
	}

	private void resolveCollisions(Collision[] collisions, int collisionsCount) {
		for (int i = 0; i < collisionsCount; i++) {
			var collision = collisions[i];
			// solve velocity for next frame bounds mass????
			// collision.body1.velocity
			float deep = collision.deep;
			Vector2 tvelocity = collision.body1 != null ? collision.body1.velocity.cpy() : new Vector2();
			float totalInverseMass = collision.body1 != null ? collision.body1.getInverseMass() : 0;
			if (collision.body2 != null) {
				tvelocity.sub(collision.body2.velocity);
				totalInverseMass += collision.body2.getInverseMass();
			}
			float c = 0;
			float tv = -(1 + c) * tvelocity.dot(collision.normal) / totalInverseMass;

			if (collision.body1 != null) {
				deep *= collision.body2 != null ? 0.5f : 1;
				float m = collision.body1.getInverseMass() / totalInverseMass;
				collision.body1.velocity.add(collision.normal.cpy().scl(tv));
				collision.body1.gameObject.transform.addToLocationScl(collision.normal.cpy(), deep);
				collision.body1.addCollision(collision.normal.cpy());
			}
			if (collision.body2 != null) {
				float m = collision.body2.getInverseMass() / totalInverseMass;
				collision.body2.velocity.add(collision.normal.cpy().scl(-tv));
				collision.body2.gameObject.transform.addToLocationScl(collision.normal.cpy(), -deep);
				collision.body2.addCollision(collision.normal.cpy().scl(-1));
			}
		}
	}

	private int collectCollision(List<Info> infos) {
		int collisionsFound = 0;
		for (int i = 0; i < infos.size() - 1 && collisionsFound < collisions.length; i++) {
			Info info1 = infos.get(i);
			for (int j = i + 1; j < infos.size() && collisionsFound < collisions.length; j++) {
				Info info2 = infos.get(j);
				collisionsFound += checkCollision(info1, info2, collisions, collisionsFound);
			}
		}
		return collisionsFound;
	}

	private int checkCollision(Info collider1, Info collider2, Collision[] collisions, int idx) {
		int count = 0;
		if (collider1.collider instanceof CircleCollider circle1) {
			if (collider2.collider instanceof CircleCollider circle2) {
				count = checkCollision(circle1, circle2, collisions, idx);
			} else if (collider2.collider instanceof BoxCollider box) {
				count = checkCollision(circle1, box, collisions, idx);
			} else {
				// plane???
				throw new RuntimeException("xdxdx no body collide1r xd");
			}
		} else if (collider1.collider instanceof BoxCollider box1) {
			if (collider2.collider instanceof CircleCollider circle2) {
				count = checkCollision(circle2, box1, collisions, idx);
				if (count > 0)
					collisions[idx].normal.scl(-1);
			} else if (collider2.collider instanceof BoxCollider box2) {
				count = checkCollision(box1, box2, collisions, idx);
			} else {
				// plane???
				throw new RuntimeException("xdxdx no body col2lider xd");
			}
		} else {
			// plane???
			throw new RuntimeException("xdxdx no body colli3der xd");
		}
		for (int i = idx; i < idx + count; i++) {
			collisions[i].body1 = collider1.body;
			collisions[i].body2 = collider2.body;
		}
		return count;
	}

	private Vector2 helpVector1 = new Vector2();

	private int checkCollision(BoxCollider collider1, BoxCollider collider2, Collision[] collisions, int collisionIdx) {
		if (collider1.gameObject.transform.getRotationZ() != 0)
			throw new RuntimeException();
		if (collider2.gameObject.transform.getRotationZ() != 0)
			throw new RuntimeException();

		Vector2 globalBox1 = collider1.gameObject.transform.getLocation2().add(collider1.offset);
		Vector2 globalBox2 = collider2.gameObject.transform.getLocation2().add(collider2.offset);

		Vector2 localBox1 = globalBox1.cpy().sub(globalBox2);

		Vector2 hb1 = collider1.dimension.cpy().scl(0.5f);
		Vector2 hb2 = collider2.dimension.cpy().scl(0.5f);

		var rectangle2 = new Rectangle().set(-hb2.x, -hb2.y, hb2.x * 2, hb2.y * 2);

		int count = 0;

		Vector2[] points = new Vector2[4];
		points[0] = localBox1.cpy().add(-hb1.x, -hb1.y);
		points[1] = localBox1.cpy().add(-hb1.x, hb1.y);
		points[2] = localBox1.cpy().add(hb1.x, -hb1.y);
		points[3] = localBox1.cpy().add(hb1.x, hb1.y);
		for (int i = 0; i < points.length; i++) {
			var point = points[i];
			if (rectangle2.contains(point)) {
				Vector2 cp = new Vector2();
				cp.x = Math.clamp(point.x + -hb1.x, -hb2.x, hb2.x);
				cp.y = Math.clamp(point.y + -hb1.y, -hb2.y, hb2.y);
				var collision = collisions[collisionIdx++];
				collision.deep = cp.sub(point).length();
				collision.normal.set(helpVector1.set(cp).sub(point).nor());
				count++;
				if (collisionIdx == collisions.length)
					break;
			}
		}
		return count;
	}

	private int checkCollision(CircleCollider circle, BoxCollider box, Collision[] collisions, int collisionIdx) {
		if (box.gameObject.transform.getRotationZ() != 0)
			throw new RuntimeException(""+box.gameObject.transform.getRotationZ() +" on " + box.gameObject.getName());
		Vector2 p = circle.gameObject.transform.getLocation2().add(circle.offset);
		float bx = box.gameObject.transform.getLocationX() + box.offset.x;
		float by = box.gameObject.transform.getLocationY() + box.offset.y;
		Vector2 cp = p.cpy();
		cp.x = MathUtils.clamp(cp.x, bx - box.dimension.x * 0.5f, bx + box.dimension.x * 0.5f);
		cp.y = MathUtils.clamp(cp.y, by - box.dimension.y * 0.5f, by + box.dimension.y * 0.5f);
		if (p.dist2(cp) < circle.radius * circle.radius) {
			var collision = collisions[collisionIdx];
			collision.normal.set(helpVector1.set(p).sub(cp).nor().cpy());
			collision.deep = circle.radius - cp.sub(p).length();
			return 1;
		}
		return 0;
	}

	private int checkCollision(CircleCollider collider1, CircleCollider collider2, Collision[] collisions,
			int collisionIdx) {
		float radius2 = (collider1.radius + collider2.radius) * (collider1.radius + collider2.radius);
		float dist2 = helpVector1
				.set(collider1.gameObject.transform.getLocationX() + collider1.offset.x,
						collider1.gameObject.transform.getLocationY() + +collider1.offset.y)
				.dist2(collider2.gameObject.transform.getLocationX() + collider2.offset.x,
						collider2.gameObject.transform.getLocationY() + collider2.offset.y);
		if (dist2 < radius2) {
			var collision = collisions[collisionIdx];
			collision.deep = (float) (Math.sqrt(radius2) - Math.sqrt(dist2));
			collision.normal.set(helpVector1.sub(collider2.gameObject.transform.getLocation2()).nor());
			return 1;
		}
		return 0;
	}

	private void collectInfo(GameObject object, List<Info> infos) {
		var colliders = object.getComponents(Collider.class);
		for (var collider : colliders) {
			Info info = new Info();
			info.collider = collider;
			info.collider.gameObject = object;
			info.collider.transform = object.transform;
			info.body = object.getComponent(RigidBody.class);
			if (info.body != null) {
				info.body.gameObject = object;
				info.body.transform = object.transform;
				info.body.clearNormals();
				info.body.updatePhysic(Engine.graphics.getDeltaTime());
			}
			infos.add(info);
		}
		if (object.hasChildren()) {
			object.getChildren().forEach(c -> collectInfo(c, infos));
		}
	}

}
