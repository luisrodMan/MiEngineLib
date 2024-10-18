package com.ngeneration.miengine.scene;

import com.ngeneration.miengine.math.Vector3;

public class GameObject {

	public final Vector3 position = new Vector3();
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
