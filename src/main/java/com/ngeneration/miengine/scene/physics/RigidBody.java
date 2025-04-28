package com.ngeneration.miengine.scene.physics;

import java.util.ArrayList;
import java.util.List;

import com.ngeneration.miengine.math.Vector2;
import com.ngeneration.miengine.scene.Component;

public class RigidBody extends Component {

	public static float gravity = 250;//

	private transient List<Vector2> normals = new ArrayList<>();

	public float mass = 1;
	public Vector2 velocity = new Vector2();
	public float gravityScale = 1;

	public float getInverseMass() {
		return mass == 0 ? 0 : 1 / mass;
	}

	public void updatePhysic(float delta) {
		velocity.y -= gravity * gravityScale * delta;// move gravity to configuration
		transform.addToLocationScl(velocity, delta);
	}

	public boolean isOnGround(Vector2 up) {
		return normals.stream().anyMatch(normal -> normal.dot(up) > 0.85f);
	}

	public void addCollision(Vector2 normal) {
		normals.add(normal);
	}

	public void clearNormals() {
		normals.clear();
	}

}
