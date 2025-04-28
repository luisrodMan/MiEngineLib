package com.ngeneration.miengine.scene;

import java.io.IOException;

public class SceneManager {

	private static GameObject activeScene;

	public static GameObject getActiveScene() {
		return activeScene;
	}

	static void setActiveScene(GameObject scene) {
		activeScene = scene;
	}

	public static void loadScene(String name) {
		if (activeScene != null && activeScene.getName() != null && activeScene.getName().equals(name))
			try {
				SceneApp.instance.reloadScene();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		// search on configured scenes
		// if found
		// update xd
	}

}
