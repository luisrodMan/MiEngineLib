package com.ngeneration.miengine.util.indexer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import com.ngeneration.miengine.util.Util;

public class ResourceIndexer {

	private Map<Integer, String> linesMap = new HashMap<>();
	private Map<Integer, ResourceTemplate> resources = new HashMap<>();
	// ext, Provider
	private Map<String, ResourceTemplateProvider> resourceProviders = new HashMap<>();
//	private Map<Component, Map<String, ResourceItem>> references = new HashMap<>();
	private String basePath;
	private int resourceIndex = 1;
	private File file;

	public ResourceIndexer() {
		var provider = new DefaultResourceTemplateProvider();
		addResourceParser(provider);
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	public void addResourceParser(ResourceTemplateProvider parser) {
		for (String ext : parser.getExtensions())
			resourceProviders.put(ext, parser);
	}

	public void loadIndex(File file) throws FileNotFoundException, IOException {
		loadIndex(Util.readText(file));
		this.file = file;
	}

	public void loadIndex(String data) {
		for (String line : Util.sliptToLines(data)) {
			if (line.startsWith("#") || line.isBlank())
				continue;
			int i = line.indexOf(' ');
			int idx = Integer.parseInt(line.substring(0, i));
			resourceIndex = Math.max(resourceIndex, idx + 1);
			linesMap.put(idx, line.substring(i + 1));
		}
	}

	public int register(String resourcePath) {
		var template = loadResource(resourceIndex++, resourcePath);
		if (template != null) {
			linesMap.put(template.getId(), resourcePath);
		}
		return template == null ? -1 : template.getId();
	}

	private ResourceTemplate loadResource(int index, String line) {
		int jsonStart = line.indexOf('{');
		jsonStart = jsonStart == -1 ? line.length() : jsonStart;
		String jsonData = line.substring(jsonStart);
		String path = line.substring(0, jsonStart);
		int dot = path.lastIndexOf('.');
		String ext = dot == -1 ? "" : path.substring(dot + 1);
		var provider = resourceProviders.get(ext);
		ResourceTemplate template = null;
		if (provider != null)
			template = provider.getResourceTemplate(ext, index, (basePath == null ? "" : basePath + "/") + path,
					jsonData);
		if (template != null)
			this.resources.put(index, template);
		return template;
	}

	public ResourceTemplate getTemplate(int id) {
		var resourceData = this.resources.get(id);
		if (resourceData == null) {
			String line = linesMap.get(id);
			if (line == null)
				return null;
			else
				return loadResource(id, line);
		}
		return resourceData;
	}

	public <T extends ResourceTemplate> T getTemplate(int id, Class<T> type) {
		var val = getTemplate(id);
		if (val == null)
			val = null;
		return type.cast(val);
	}

	public void persistTemplate(ResourceTemplate template) {
		System.out.println("persisting template: " + template);
	}

	public Integer getIndex(String path) {
		var id = resources.entrySet().stream().filter(c -> c.getValue().getPath().endsWith(path)).map(c -> c.getKey())
				.findAny().orElse(-1);
		if (id < 1) {
			id = linesMap.entrySet().stream()
					.filter(c -> c.getValue().endsWith(path) || c.getValue().contains(path + "{")).map(c -> c.getKey())
					.findAny().orElse(-1);
		}
		return id;
	}

//	public void registerReference(Component component, String fieldName, ResourceItem  ) {
//		references.computeIfAbsent(component, r -> new HashMap<>()).put(fieldName, resource);
//	}

//	public void removeReference(Component component, String fieldName) {
//		references.computeIfAbsent(component, r -> new HashMap<>()).remove(fieldName);
//		if (references.get(component).isEmpty())
//			references.remove(component);
//	}
//
//	public ResourceItem getReference(Component component, String fieldName) {
//		var ref = references.get(component);
//		return ref == null ? null : ref.get(fieldName);
//	}

	public boolean updateResourcePath(int id, String newPath) {
		var template = getTemplate(id, ResourceTemplate.class);
		if (template != null) {
			template.updatePath(newPath);
			linesMap.put(id, linesMap.get(id).replaceAll("^" + id + "[^{]+", id + " " + newPath));
		}
		return template != null;
	}

	public void renameDirectory(String oldPath, String newPath) {
		if (!oldPath.endsWith("/"))
			oldPath += "/";
		if (!newPath.endsWith("/"))
			newPath += "/";

		var base = (basePath == null ? "" : basePath + "/");

		var oldOriPath = oldPath;
		var newOriPath = newPath;
		var oldPathWithBase = base + oldPath;
		var newPathWithBase = base + newPath;

		resources.values().forEach(res -> {
			if (res.getPath().startsWith(oldPathWithBase))
				res.updatePath(newPathWithBase + res.getName());
		});
		linesMap.entrySet().forEach(v -> {
			if (v.getValue().startsWith(oldOriPath)) {
				linesMap.put(v.getKey(), v.getValue().replace(oldOriPath, newOriPath));
			}
		});
	}

	public void persists() throws IOException {
		StringBuilder sb = new StringBuilder();
		resources.values().forEach(res -> {
			var jsonString = res.getJsonString();
			sb.append(res.getId()).append(" ")
					.append(res.getPath().replaceFirst("^" + Pattern.quote(basePath) + "/", ""))
					.append(jsonString != null ? jsonString : "");
			sb.append(System.lineSeparator());
		});
		Util.write(file, sb.toString());
	}

}