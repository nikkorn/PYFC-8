package com.dumbpug.sfc.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.dumbpug.sfc.Constants;
import com.dumbpug.sfc.SFC;
import com.dumbpug.sfc.cli.ISFCLauncher;
import com.dumbpug.sfc.cli.REPL;
import com.dumbpug.sfc.utility.ConcurrentQueue;

public class DesktopLauncher {
	/**
	 * Application entry point.
	 * @param args Console arguments.
	 */
	public static void main (String[] args) {
		// Print the console splash.
		System.out.println("SFC v.0.0.1");

		// The user may have specified a cart image to load.
		// If so then don't start the console REPL and just launch SFC.
		if (args.length > 0) {
			// Launch the SFC application straight away with the specified cart loaded.
			// TODO Actually pass the cart, or script/sprite/audio resources.
			new LwjglApplication(new SFC(), createApplicationConfig());
		} else {
			new REPL(new ISFCLauncher() {
				@Override
				public SFC launch() {
					// Create the SFC instance.
					SFC sfc = new SFC();

					// Launch the SFC application without loading any cart.
					new LwjglApplication(sfc, createApplicationConfig());

					// Return the command queue to use for REPL -> SFC communication.
					return sfc;
				}
			}).start();
		}
	}

	/**
	 * Creates the lwjgl application config.
	 * @return The lwjgl application config.
	 */
	public static LwjglApplicationConfiguration createApplicationConfig() {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.forceExit     = false;
		config.width         = Constants.WINDOW_WIDTH;
		config.height        = Constants.WINDOW_HEIGHT;
		config.backgroundFPS = 60;
		config.foregroundFPS = 60;
		config.vSyncEnabled  = false;
		config.resizable     = false;
		config.title         = "SFC";
		config.addIcon("images/icon.png", Files.FileType.Internal);
		return config;
	}
}
