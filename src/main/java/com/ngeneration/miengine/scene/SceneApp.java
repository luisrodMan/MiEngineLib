package com.ngeneration.miengine.scene;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.ngeneration.miengine.Engine;
import com.ngeneration.miengine.EngineApplicationConfiguration;
import com.ngeneration.miengine.Game;
import com.ngeneration.miengine.desktop.DesktopApplication;
import com.ngeneration.miengine.graphics.FitViewport;
import com.ngeneration.miengine.graphics.OrthographicCamera;
import com.ngeneration.miengine.math.Point;
import com.ngeneration.miengine.scene.physics.CollisionResolver;
import com.ngeneration.miengine.scene.physics.RigidBody;
import com.ngeneration.miengine.util.EngineSerializer;
import com.ngeneration.miengine.util.Util;
import com.ngeneration.miengine.util.indexer.ResourceIndexer;

public class SceneApp extends Game {

	private GameObject scene;
	private FitViewport viewport;
	private Camera camera;
	private String scenePath;
	private ResourceIndexer indexer;
	private EngineApplicationConfiguration configuration;

	private CollisionResolver physicResolver;
	static SceneApp instance;

	private Point getPoint(JsonElement e) {
		String[] view = e.getAsString().split(",");
		return new Point(Integer.parseInt(view[0]), Integer.parseInt(view[1]));
	}

	public SceneApp(String[] args) {
		instance = this;
		EngineApplicationConfiguration configuration = new EngineApplicationConfiguration();
		configuration.setTitle("Drop");
		configuration.useVsync(true);
//	    configuration.setForegroundFPS(MiEngineApplicationConfiguration.getDisplayMode().refreshRate + 1);
//	    configuration.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png");

		Gson gson = new Gson();
		try {
			var config = gson.fromJson(Util.readText(new File("project.miengine")), JsonObject.class)
					.get("configuration").getAsJsonObject();
			Point window = getPoint(config.get("window"));
			configuration.setWindowedMode(window.getX(), window.getY()); // this line changes the size of the window
			var misc = config.get("misc").getAsJsonObject();
			var gravity = misc.get("gravity");
			RigidBody.gravity = gravity == null ? RigidBody.gravity : Float.parseFloat(gravity.getAsString());
		} catch (JsonSyntaxException | IOException e) {
			throw new RuntimeException(e);
		}

		// load scene get from configuration..
		// not found ? get from command line
//		var scene = "Assets/level1.scn";
		String scene = null;
		if (args.length > 0) {
			scene = args[0];
		}
		if (scene == null)
			throw new RuntimeException("no scene");

		this.scenePath = scene;
		this.configuration = configuration;

		physicResolver = new CollisionResolver(500);

		new DesktopApplication(this, configuration);
	}
	
	void reloadScene() throws FileNotFoundException, IOException {
		setScene(EngineSerializer.deserialize(new File(scenePath), indexer, null));
	}

	public void setScene(GameObject scene) {
		this.scene = scene;
		SceneManager.setActiveScene(scene);
		
		camera = scene.getComponent(Camera.class);
		if (camera == null) {
			camera = new Camera();
			var cam = new OrthographicCamera();
			cam.setToOrtho(false, configuration.getWidth(), configuration.getHeight());
			camera.setCamera(cam);
			camera.activate();
			camera.update(Engine.graphics.getDeltaTime());
			scene.addComponent(camera);
		}

		Gson gson = new Gson();
		JsonObject config;
		try {
			config = gson.fromJson(Util.readText(new File("project.miengine")), JsonObject.class).get("configuration")
					.getAsJsonObject();
			Point viewport = getPoint(config.get("viewport"));
			this.viewport = new FitViewport(viewport.getX(), viewport.getY());
			this.viewport.setCamera(camera);
			this.viewport.update(Engine.graphics.getWidth(), Engine.graphics.getHeight(), false);
			this.viewport.apply();
			System.out.println("scale: " + this.viewport.getScale());
			camera.getCamera().scale.set(this.viewport.getScale());
		} catch (JsonSyntaxException | IOException e) {
			throw new RuntimeException(e);
		}
		if (scene != null) {
			System.out.println("started 1");
			scene.start();
		}
	}
	
	

	@Override
	public void create() {
		indexer = new ResourceIndexer();
		try {
			indexer.loadIndex(new File("index"));
			Engine.indexer = indexer;
			reloadScene();

		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void render() {
		if (scene != null) {
			var color = camera.getColor();
			glClearColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
			glClear(GL_COLOR_BUFFER_BIT); // clear the framebuffer

			// update
			scene.update(Engine.graphics.getDeltaTime());

			// physics
			scene.updatePhysics(Engine.graphics.getDeltaTime());
			physicResolver.update(scene);

			// destroy objects
			GameObject.toDestroy.forEach(o -> o.getParentGameObject().remove(o));
			GameObject.toDestroy.clear();
			// dispatch objects
			for (var kv : GameObject.toDispatch.entrySet()) {
				var parent = kv.getKey();
				kv.getValue().forEach(c -> parent.addChild(c));
			}
			GameObject.toDispatch.clear();

			// render
			scene.render();
		}

	}

}
