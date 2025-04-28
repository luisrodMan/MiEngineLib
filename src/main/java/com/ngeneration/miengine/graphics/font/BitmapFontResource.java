package com.ngeneration.miengine.graphics.font;

import com.ngeneration.miengine.scene.annotations.Resource;
import com.ngeneration.miengine.util.indexer.ResourceItem;
import com.ngeneration.miengine.util.indexer.ResourceTemplate;

import lombok.Getter;

@Getter
@Resource("fnt")
public class BitmapFontResource extends ResourceItem {

	private BitmapFont font;

	public BitmapFontResource(ResourceTemplate resource, int resourceId, BitmapFont font) {
		super(resource, resourceId, font.getMetadata().getFace());
		this.font = font;
	}

}
