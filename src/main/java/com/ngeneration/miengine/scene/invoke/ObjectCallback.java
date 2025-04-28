package com.ngeneration.miengine.scene.invoke;

import com.ngeneration.miengine.scene.Component;
import com.ngeneration.miengine.scene.GameObject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ObjectCallback {

	public GameObject object;
	public Component component;
	public Function method;
	
}
