package com.ngeneration.miengine.scene.annotations;

public @interface Dependent {
	public static final int INLINE = 1, INNER = 2;

	String value();

	int mode();

}
