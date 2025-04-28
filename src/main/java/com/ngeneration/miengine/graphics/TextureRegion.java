package com.ngeneration.miengine.graphics;

import com.ngeneration.miengine.math.RectangleI;
import com.ngeneration.miengine.math.Vector2;
import com.ngeneration.miengine.scene.annotations.Resource;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Resource({ "png" })
public class TextureRegion extends RectangleI {

	private int id;
	private Texture texture;
	public Vector2 origin;

	public TextureRegion(Texture texture) {
		setTexture(texture);
		set(0, 0, texture.getWidth(), texture.getHeight());
		origin = new Vector2(getWidth() * 0.5f, getHeight() * 0.5f);
	}

}
