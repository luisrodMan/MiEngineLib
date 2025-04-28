package com.ngeneration.miengine;

import com.ngeneration.miengine.graphics.Graphics;
import com.ngeneration.miengine.util.indexer.ResourceIndexer;

public class Engine {

	public static enum ENV {
		PROD, DEV, EDITOR
	};

	public static Graphics graphics;
	public static Input input;
	public static ResourceIndexer indexer;
	public static ENV env = ENV.DEV;

}
