package com.ngeneration.miengine.scene.ui;

import com.ngeneration.miengine.graphics.Color;
import com.ngeneration.miengine.math.Vector2;
import com.ngeneration.miengine.scene.Component;

public class CanvasComponent extends Component {

	public Color color;
	public Vector2 size = new Vector2();
	public Vector2 pivot = new Vector2(0.5f, 0.5f);

}
