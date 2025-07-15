package com.ngeneration.miengine.scene;

import com.ngeneration.miengine.Engine;
import com.ngeneration.miengine.scene.physics.Collider;
import com.ngeneration.miengine.scene.physics.Collision;
import com.ngeneration.miengine.util.EngineSerializer;

public class Component {

	private int id = GameObject.generateId();
	public transient Transform transform;
	public transient GameObject gameObject;
	private boolean enabled = true;

	public int getId() {
		return id;
	}

	public void start() {

	}

	public void update(float delta) {

	}

	public void updatePhysics(float delta) {

	}

	public void render() {

	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean value) {
		enabled = value;
	}

	public void onPropertyUpdated(String propertyName) {

	}

	protected static void dispatch(GameObject parent, GameObject child) {
		GameObject.dispatch(parent, child);
	}

	public static GameObject instantiate(String path) {
		path = "Assets/" + path;
		path = !path.endsWith(".scn") ? path + ".scn" : path;
		return instantiate(Engine.indexer.getIndex(path));
	}

	public static GameObject instantiate(int id) {
		return EngineSerializer.deserialize(id, Engine.indexer, null);
	}

	public void destroy() {
		GameObject.destroy(gameObject);
	}

	public void onAttached() {

	}

	public void onCollisionEnter2D(Collision collision) {

	}

	public void onTriggerEnter2D(Collider collider) {
		
	}

	public void onTriggerExit2D(Collider collider) {
		
	}

	public void onTriggerStay2D(Collider collider) {
		
	}

}
