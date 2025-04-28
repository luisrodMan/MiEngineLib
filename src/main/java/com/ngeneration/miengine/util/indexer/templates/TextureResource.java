package com.ngeneration.miengine.util.indexer.templates;

import java.util.LinkedList;
import java.util.List;

import com.ngeneration.miengine.graphics.Texture;
import com.ngeneration.miengine.graphics.TextureRegion;
import com.ngeneration.miengine.math.Point;
import com.ngeneration.miengine.scene.SpriteResource;
import com.ngeneration.miengine.scene.annotations.Dependent;
import com.ngeneration.miengine.scene.annotations.Select;
import com.ngeneration.miengine.util.indexer.ResourceItem;

public class TextureResource extends AbstractResource {

	private transient Texture texture;
	private transient List<TextureRegion> sprites = new LinkedList<>();

	@Select(value = { "Single", "Multiple" })
	private String spriteMode;

	@Dependent(value = "spriteMode#single", mode = Dependent.INNER)
	@Select(value = { "Center", "TopLeft", "Custom" })
	private String pivot;

	@Dependent(value = "pivot#custom", mode = Dependent.INNER)
	private Point location;

	@Select(value = { "Clamp" })
	private String wrapMode;

	@Select(value = { "Billinear" })
	private String filterMode;

	public TextureResource(int id, String path, String ext, String[] extensions, String json) {
		super(id, path, ext, extensions, json);
	}

	@Override
	public void apply() {
		System.out.println("updating texture config xd");
	}

	@Override
	public ResourceItem getResource(int id) {
		return getItems().get(0);
	}

	public Texture getTexture() {
		return texture;
	}

	@Override
	public List<ResourceItem> getItems() {
		if (texture == null) {
			texture = new Texture(getPath());
			// mode?
			sprites.add(new TextureRegion(texture));
		}
		return List.of(new SpriteResource(this, 0, getSimpleName() + "_0", sprites.get(0)));
	}

}
