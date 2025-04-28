package com.ngeneration.miengine.scene;

import com.ngeneration.miengine.math.Vector2;
import com.ngeneration.miengine.math.Vector3;

public class Transform extends Component {

	private Vector3 location = new Vector3();
	private Vector3 scale = new Vector3(1, 1, 1);
	private Vector3 rotation = new Vector3();

	public void setLocalLocation(Vector3 local) {
		if (gameObject != null && gameObject.getParentGameObject() != null)
			setLocation(local.x + gameObject.getParentGameObject().transform.location.x,
					local.y + gameObject.getParentGameObject().transform.location.y,
					local.z + gameObject.getParentGameObject().transform.location.z);
		else
			setLocation(local);
	}

	public void setLocalScale(Vector3 local) {
		if (gameObject != null && gameObject.getParentGameObject() != null)
			setScale(local.x * gameObject.getParentGameObject().transform.scale.x,
					local.y * gameObject.getParentGameObject().transform.scale.y,
					local.z * gameObject.getParentGameObject().transform.scale.z);
		else
			setScale(local);
	}

	public void setLocalRotation(Vector3 local) {
		if (gameObject != null && gameObject.getParentGameObject() != null)
			setRotation(local.x + gameObject.getParentGameObject().transform.rotation.x,
					local.y + gameObject.getParentGameObject().transform.rotation.y,
					local.z + gameObject.getParentGameObject().transform.rotation.z);
		else
			setRotation(local);
	}

	public void setLocation(Vector2 position) {
		setLocation(position.x, position.y);
	}

	public void setLocation(float x, float y) {
		setLocation(x, y, 0);
	}

	public void setLocation(Vector3 location) {
		setLocation(location.x, location.y, location.z);
	}

	public void setScale(Vector3 location) {
		setScale(location.x, location.y, location.z);
	}

	public void setRotation(Vector3 location) {
		setRotation(location.x, location.y, location.z);
	}

	public void setLocation(float x, float y, float z) {
		float diffx = x - location.x;
		float diffy = y - location.y;
		float diffz = z - location.z;
		location.set(x, y, z);
		if (gameObject != null && gameObject.hasChildren()) {
			gameObject.children.forEach(c -> c.transform.setLocation(c.transform.location.x + diffx,
					c.transform.location.y + diffy, c.transform.location.z + diffz));
		}
	}

	public void setRotation(float x, float y, float z) {
		float diffx = x - rotation.x;
		float diffy = y - rotation.y;
		float diffz = z - rotation.z;
		rotation.set(x, y, z);
		if (gameObject != null && gameObject.hasChildren()) {
			gameObject.children.forEach(c -> c.transform.setRotation(c.transform.rotation.x + diffx,
					c.transform.rotation.y + diffy, c.transform.rotation.z + diffz));
		}
	}

	public void setScale(float x, float y, float z) {
		float diffx = scale.x == 0 ? 1 : scale.x;
		float diffy = scale.y == 0 ? 1 : scale.y;
		float diffz = scale.z == 0 ? 1 : scale.z;

		scale.set(x, y, z);
		if (gameObject != null && gameObject.hasChildren()) {
			gameObject.children.forEach(c -> {
				float xx = c.transform.scale.x / diffx * x;
				float yy = c.transform.scale.y / diffy * y;
				float zz = c.transform.scale.z / diffz * z;
				c.transform.setScale(xx == 0 && x > 0 ? 1 : xx, yy == 0 && y > 0 ? 1 : yy, zz == 0 && z > 0 ? 1 : zz);
			});
		}
	}

	public void addToLocationScl(Vector2 velocity, float delta) {
		setLocation(location.x + velocity.x * delta, location.y + velocity.y * delta);
	}

	public void addToLocation(Vector3 mouseOffset) {
		setLocation(location.x + mouseOffset.x, location.y + mouseOffset.y, location.z + mouseOffset.z);
	}

	public void addToLocation(Vector2 mouseOffset) {
		setLocation(location.x + mouseOffset.x, location.y + mouseOffset.y);
	}

	public float getRotationZ() {
		return rotation.z;
	}

	public Vector3 getLocation() {
		return location.cpy();
	}

	public Vector2 getLocation2() {
		return new Vector2(location.x, location.y);
	}

	public float getLocationX() {
		return location.x;
	}

	public float getLocationY() {
		return location.y;
	}

	public float getScaleX() {
		return scale.x;
	}

	public float getScaleY() {
		return scale.y;
	}

	public Vector3 getScale() {
		return scale.cpy();
	}

	public Vector3 getRotation() {
		return rotation.cpy();
	}

	public void setScaleX(float f) {
		scale.x = f;
	}

	public Vector3 getLocalLocation() {
		if (gameObject != null && gameObject.getParentGameObject() != null)
			return location.cpy().sub(gameObject.getParentGameObject().transform.location);
		else
			return location.cpy();
	}

	public Vector3 getLocalRotation() {
		if (gameObject != null && gameObject.getParentGameObject() != null)
			return rotation.cpy().sub(gameObject.getParentGameObject().transform.rotation);
		else
			return rotation.cpy();
	}

	public Vector3 getLocalScale() {
		if (gameObject != null && gameObject.getParentGameObject() != null)
			return scale.cpy().scl(gameObject.getParentGameObject().transform.scale.cpy().inv());
		else
			return scale.cpy();
	}

	public void setRotation(float zrotation) {
		setRotation(0, 0, zrotation);
	}

	public void set(Transform transform) {
		location.set(transform.location);
		scale.set(transform.scale);
		rotation.set(transform.rotation);
	}

}
