package com.dumbpug.jfc8.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.dumbpug.jfc8.JFC8;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width         = 768;
		config.height        = 512;
		config.foregroundFPS = 60; // <- limit when focused
		config.backgroundFPS = 60; // <- limit when minimized
		config.resizable     = false;
		config.title         = "SFC";
		config.addIcon("images/icon.png", Files.FileType.Internal);
		new LwjglApplication(new JFC8(), config);
	}
}
