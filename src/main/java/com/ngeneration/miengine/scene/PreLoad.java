package com.ngeneration.miengine.scene;

import com.ngeneration.miengine.scene.annotations.Resource;
import com.ngeneration.miengine.util.indexer.ResourceItem;
import com.ngeneration.miengine.util.indexer.ResourceTemplate;

@Resource({ "scn" })
public class PreLoad extends ResourceItem {

	public PreLoad(ResourceTemplate resource, int id, String name) {
		super(resource, id, name);
	}

	public GameObject instantiate() {
		return Component.instantiate(getId());
	}

}
