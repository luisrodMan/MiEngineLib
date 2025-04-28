package com.ngeneration.miengine.util;

import com.ngeneration.miengine.scene.Component;
import com.ngeneration.miengine.scene.GameObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

@Data
@AllArgsConstructor
@Value
public class Reference {
	private GameObject object;
	private Component component;
	private String field;

	public Object getValue() {
		return component != null ? component : object;
	}

	public void setValue(Object object) {
		try {
			component.getClass().getDeclaredField(field).set(component, object);
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
	}

//	public boolean equals(Object v) {
//
//		return v == this || (v != null && v instanceof Reference r && r.component == component && r.object == r.object
//				&& (r.field == field || (field != null && r.field == null && field.equals(r.field))));
//	}

}