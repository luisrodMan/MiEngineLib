package com.ngeneration.miengine.util.indexer;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResourceItem {

	private ResourceTemplate resource;
	private int id;
	private String name;

	public boolean isValidReference() {
		return true;
	}

}
