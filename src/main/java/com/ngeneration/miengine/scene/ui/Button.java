package com.ngeneration.miengine.scene.ui;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

import com.ngeneration.miengine.Engine;
import com.ngeneration.miengine.graphics.Color;
import com.ngeneration.miengine.math.Rectangle;
import com.ngeneration.miengine.scene.SpriteRenderer;
import com.ngeneration.miengine.scene.annotations.ListType;
import com.ngeneration.miengine.scene.invoke.ObjectCallback;

public class Button extends CanvasComponent {

	@ListType(ObjectCallback.class)
	public List<ObjectCallback> onClick = new LinkedList<>();

	public Button() {
		color = Color.BLACK.cpy();
		size.set(200, 60);
	}

	@Override
	public void start() {
		// deleted objects or components
		onClick.removeAll(onClick.stream().filter(c -> c.getObject() == null || c.getComponent() == null).toList());
	}

	@Override
	public void render() {
		var batch = SpriteRenderer.getBatch();
		batch.begin();
		batch.setColor(color);
		batch.fillRect(transform.getLocationX() - pivot.x * size.x, transform.getLocationY() - pivot.y * size.y, size.x,
				size.y);
		batch.end();
	}

	private Rectangle getRectangle() {
		return new Rectangle(-pivot.x * size.x, -pivot.y * size.y, size.x, size.y);
	}

	@Override
	public void update(float delta) {
		if (Engine.input.isKeyJustPressed(Engine.input.Keys.MOUSE_BTN1)) {
			if (getRectangle().contains(Engine.input.getMouse().sub(transform.getLocation()))) {
				doClick();
			}
		}
	}

	public void doClick() {
		onClick.forEach(listener -> {
			try {
				if (listener.method.name != null)
					listener.getComponent().getClass().getDeclaredMethod(listener.method.name, new Class<?>[0])
							.invoke(listener.component);
			} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
		});
	}

}