package com.ngeneration.miengine.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ngeneration.miengine.scene.Component;
import com.ngeneration.miengine.scene.GameObject;
import com.ngeneration.miengine.util.ResourceReference.Datas;

public class References {

	private Map<GameObject, ResourceReference> references1 = new HashMap<>();
	private GameObject root;

	public GameObject getRoot() {
		return root;
	}

	public References(GameObject root, List<ReferenceData> referenced, List<ReferenceData> references) {
		this.root = root;
		if (referenced != null && references != null) {
			Map<Integer, Reference> values = referenced.stream().collect(Collectors.toMap(r -> r.id, r -> r.reference));
			references.forEach(ref -> {
				var refer = values.get(ref.id);
				ref.reference.setValue(refer.getValue());
				createReference(refer.getObject(), refer.getComponent(), ref.reference);
			});
		}
	}

	public void createReference(GameObject owner, Component ownerComponent, Reference reference) {
		var object1 = references1.computeIfAbsent(owner, c -> new ResourceReference());
		object1.datas.computeIfAbsent(ownerComponent, r -> new Datas()).add(reference);

		var object2 = references1.computeIfAbsent(reference.getObject(), c -> new ResourceReference());
		var parent = new Reference(owner, ownerComponent, null);
		var d = object2.datas.computeIfAbsent(reference.getComponent(), r -> new Datas());
		d.instance = parent;
	}

	public void registerResource(GameObject object, Component instance, String name, Object resource) {

	}

}
