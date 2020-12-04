package com.dumbpug.sfc.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.dumbpug.sfc.SFC;
import com.dumbpug.sfc.SFCContext;
import com.dumbpug.sfc.SFCContextDetails;
import java.io.File;

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
		config.title         = "SFC";
		config.addIcon("images/icon.png", Files.FileType.Internal);

		// Create the SFC context details based ont he application arguments.
		SFCContextDetails contextDetails = createContextDetails(args);

		switch (contextDetails.getContext()) {
			case DIRECTORY:
				System.out.println("directory: '" + contextDetails.getTarget().getAbsolutePath() + "'");
				break;
			case CARTRIDGE:
				System.out.println("cartridge: '" + contextDetails.getTarget().getAbsolutePath() + "'");
				break;
			default:
				throw new RuntimeException("unknown SFCContext type");
		}

		// Create the SFC instance.
		SFC sfc = new SFC(contextDetails);

		// Launch the SFC application
		new LwjglApplication(sfc, config);
	}

	/**
	 * Create the application context details.
	 * @param args
	 * @return
	 */
	private static SFCContextDetails createContextDetails(String[] args) {
		String targetArgument = "";
		String flagsArgument  = "";

		if (args.length == 1) {
			// Is the first and only argument the flags argument?
			if (args[0].startsWith("-")) {
				flagsArgument = args[0];
			} else {
				targetArgument = args[0];
			}
		} else if (args.length > 1) {
			flagsArgument = args[0];
			targetArgument = args[1];
		}

		File target = new File(targetArgument);

		// Get the application context based on the target file type.
		SFCContext context = getContext(target);

		// Parse the application flags.
		boolean isFullScreen = flagsArgument.contains("f");

		return new SFCContextDetails(context, target, isFullScreen);
	}

	/**
	 * Gets the application context based on the target file type.
	 * @param target The target file.
	 * @return The application context based on the target file type.
	 */
	private static SFCContext getContext(File target) {
		// We must have a valid directory/file.
		if (!target.getAbsoluteFile().exists()) {
			System.out.println("'" + target.getAbsolutePath() + "' is not a valid directory or cart image file");
			System.exit(0);
		}

		// Is the file a directory or a cartridge file?
		if (target.getAbsoluteFile().isDirectory()) {
			return SFCContext.DIRECTORY;
		} else {
			if (!target.getAbsolutePath().endsWith("sfc.png")) {
				System.out.println("'" + target.getAbsolutePath() + "' is not a valid directory or cart image file");
				System.exit(0);
			}

			return SFCContext.CARTRIDGE;
		}
	}
}
