package com.ngeneration.miengine.util.indexer;

import java.util.List;

public interface ResourceTemplate {

	int getId();

	String getPath();

	default String getName() {
		var path = getPath();
		int idx = -1;
		if ((idx = path.lastIndexOf('/')) != -1)
			return path.substring(idx + 1);
		return path;
	}

	default String getSimpleName() {
		var name = getName();
		var idx = name.indexOf('.');
		return idx == -1 ? name : name.substring(0, idx);
	}

	String[] getExtensions();

	void apply();

	ResourceItem getResource(int id);

	List<ResourceItem> getItems();

	void updatePath(String newPath);

	String getJsonString();

}
