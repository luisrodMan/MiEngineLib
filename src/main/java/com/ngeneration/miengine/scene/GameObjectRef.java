package com.ngeneration.miengine.scene;

public class GameObjectRef extends GameObject {

	private int resourceId;

	public GameObjectRef(int resourceId) {
		this.resourceId = resourceId;
	}

	public int getResourceId() {
		return resourceId;
	}

	public boolean isValid() {
		return this.resourceId > -1;
	}

	public void invalidate() {
		resourceId = -1;
		removeAllComponents();
		removeAllChildren();
	}

}
