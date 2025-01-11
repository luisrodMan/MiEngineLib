package com.ngeneration.miengine.desktop;

import com.ngeneration.miengine.EngineApplicationConfiguration;
import com.ngeneration.miengine.SampleGame;

public class DesktopLauncher {

	public static void main(String[] arg) {
		EngineApplicationConfiguration configuration = new EngineApplicationConfiguration();
		configuration.setTitle("Drop");
		configuration.useVsync(true);
//	    configuration.setForegroundFPS(MiEngineApplicationConfiguration.getDisplayMode().refreshRate + 1);
		configuration.setWindowedMode(800, 500); // this line changes the size of the window
//	    configuration.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png");

		new DesktopApplication(new SampleGame(), configuration);
	}

}
