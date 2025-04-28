package com.ngeneration.miengine.scene.ui;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

import com.ngeneration.miengine.graphics.Color;
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
	public void render() {
		var batch = SpriteRenderer.getBatch();
		batch.begin();
		batch.setColor(color);
		batch.fillRect(transform.getLocationX() - pivot.x * size.x, transform.getLocationY() - pivot.y * size.y, size.x,
				size.y);
		batch.end();
	}

	public void doClick() {
		onClick.forEach(listener -> {
			try {
				if (listener.method.name != null)
					listener.getComponent().getClass().getDeclaredMethod(listener.method.name, new Class<?>[0])
							.invoke(listener.object);
			} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
		});
	}

}