package com.ngeneration.miengine.util;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.ngeneration.miengine.scene.Component;

public class ResourceReference {

	Map<Component, Datas> datas = new HashMap<>();

	static class Datas {
		Reference instance;
		// field -- value
		Set<Reference> references = new LinkedHashSet<>();
		Map<String, ResourceLink> links = new HashMap<>();

		public void add(Reference reference) {
			references.add(reference);
		}
	}

	private class ResourceLink {

	}

}
