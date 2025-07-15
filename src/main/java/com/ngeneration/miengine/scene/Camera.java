package com.ngeneration.miengine.scene;

import com.ngeneration.miengine.graphics.Color;
import com.ngeneration.miengine.graphics.OrthographicCamera;
import com.ngeneration.miengine.math.Vector3;

public class Camera extends Component {

	private OrthographicCamera camera;
	public Color color = new Color(20, 50, 100);
	public boolean cameraCentered = true;

	public void setCamera(OrthographicCamera cam) {
		this.camera = cam;
	}

	public com.ngeneration.miengine.graphics.Camera getCamera() {
		return camera;
	}

	public void activate() {
		GameObject.setRootCamera(camera);
	}

	public Color getColor() {
		return color;
	}

	@Override
	public void update(float delta) {
		// update camera pos
		if (cameraCentered)
			camera.position
					.set(transform.getLocalLocation().add(-camera.viewportWidth / 2, -camera.viewportHeight / 2, 0));
		else
			camera.position.set(transform.getLocalLocation());
		camera.scale.set(transform.getScale());
		camera.update();
	}

	public void setLocation(Vector3 localLocation) {
		transform.setLocation(localLocation);
	}

}
