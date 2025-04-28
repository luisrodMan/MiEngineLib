package com.ngeneration.miengine.scene;

import com.ngeneration.miengine.graphics.TextureRegion;
import com.ngeneration.miengine.scene.annotations.Resource;
import com.ngeneration.miengine.util.indexer.ResourceItem;
import com.ngeneration.miengine.util.indexer.templates.TextureResource;

@Resource({ "png" })
public class SpriteResource extends ResourceItem {

	private TextureRegion region;

	public SpriteResource(TextureResource resource, int id, String name, TextureRegion region) {
		super(resource, id, name);
		this.region = region;
	}

	public TextureRegion getRegion() {
		return region;
	}

}
