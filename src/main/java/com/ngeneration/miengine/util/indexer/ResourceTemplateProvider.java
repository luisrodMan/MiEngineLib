package com.ngeneration.miengine.util.indexer;

public interface ResourceTemplateProvider {

	String[] getExtensions();

	ResourceTemplate getResourceTemplate(String ext, int id, String path, String json);

}
