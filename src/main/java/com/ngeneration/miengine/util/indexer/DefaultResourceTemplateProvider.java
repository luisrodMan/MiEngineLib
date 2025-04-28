package com.ngeneration.miengine.util.indexer;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.ngeneration.miengine.graphics.font.BitmapFontLoader;
import com.ngeneration.miengine.graphics.font.BitmapFontResource;
import com.ngeneration.miengine.scene.PreLoad;
import com.ngeneration.miengine.util.indexer.templates.AbstractResource;
import com.ngeneration.miengine.util.indexer.templates.TextureResource;

public class DefaultResourceTemplateProvider implements ResourceTemplateProvider {

	private String[] validExtensions = new String[] { "png", "scn", "fnt" };

	@Override
	public String[] getExtensions() {
		return validExtensions;
	}

	@Override
	public ResourceTemplate getResourceTemplate(String ext, int id, String path, String json) {
		if (ext.equals("scn") || ext.equals("fnt")) {
			try {
				return new AbstractResource(id, path, ext, new String[] { ext }, json) {

					ResourceItem item = ext.equals("scn") ? new PreLoad(this, id, new File(path).getName())
							: new BitmapFontResource(this, id,
									BitmapFontLoader.create(path.replace(".fnt", ".png"), path));

					@Override
					public ResourceItem getResource(int id) {
						return id == 0 || id == super.getId() ? item : null;
					}

					@Override
					public List<ResourceItem> getItems() {
						return List.of(item);
					}

					@Override
					public void apply() {

					}
				};
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return new TextureResource(id, path, ext, validExtensions, json);
	}

}
