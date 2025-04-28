package com.ngeneration.miengine.util.indexer.templates;

import com.ngeneration.miengine.util.indexer.ResourceTemplate;

public abstract class AbstractResource implements ResourceTemplate {

	private transient Integer id;
	private transient String path;
	private transient String[] ext;
	private String json;

	public AbstractResource(int id, String path, String ext, String[] extensions, String json) {
		this.id = id;
		this.ext = extensions;
		this.path = path;
		this.json = json;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getPath() {
		return path;
	}

	public String[] getExtensions() {
		return ext;
	}

	public String getJsonString() {
		return json;
	}

	protected void updateJsonString(String jsonString) {
		this.json = jsonString;
	}

	@Override
	public void updatePath(String newPath) {
		this.path = newPath;
	}

}
