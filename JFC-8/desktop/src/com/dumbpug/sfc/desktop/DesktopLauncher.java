package com.dumbpug.sfc.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.dumbpug.sfc.Constants;
import com.dumbpug.sfc.SFC;
import com.dumbpug.sfc.cli.REPL;

public class DesktopLauncher {
	/**
	 * Application entry point.
	 * @param args Console arguments.
	 */
	public static void main (String[] args) {
		// Print the console splash.
		System.out.println("SFC v.0.0.1");

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width         = 768;
		config.height        = 512;
		config.backgroundFPS = 60;
		config.foregroundFPS = 60;
		config.vSyncEnabled  = false;
		config.resizable     = false;
		config.title         = "SFC";
		config.addIcon("images/icon.png", Files.FileType.Internal);

		// Create the SFC instance.
		SFC sfc = new SFC();

		// Launch the SFC application
		new LwjglApplication(sfc, config);

		// The user may have specified a cart image to load.
		// If so then don't start the console REPL and just launch SFC.
		if (args.length == 0) {
			new REPL(sfc).start();
		}
	}
}
