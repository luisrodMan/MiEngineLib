package com.ngeneration.miengine.scene.invoke;

import com.ngeneration.miengine.scene.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class Callback {

	public Component object;
	public String method;

}
