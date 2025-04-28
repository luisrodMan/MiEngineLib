package com.ngeneration.miengine.scene;

import com.ngeneration.miengine.graphics.Color;
import com.ngeneration.miengine.graphics.OrthographicCamera;

public class Camera extends Component {

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private OrthographicCamera camera;
	public Color color = new Color(20, 50, 100);

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
		camera.update();
	}

}
