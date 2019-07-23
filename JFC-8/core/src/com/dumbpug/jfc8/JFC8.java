package com.dumbpug.jfc8;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dumbpug.jfc8.device.Device;
import com.dumbpug.jfc8.display.Screenshot;
import com.dumbpug.jfc8.runtime.RuntimeState;
import com.dumbpug.jfc8.scripteditor.ScriptEditorState;
import com.dumbpug.jfc8.state.StateManager;
import com.dumbpug.jfc8.state.states.Splash;
import com.dumbpug.jfc8.terminal.TerminalState;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The JFC8 application.
 */
public class JFC8 extends ApplicationAdapter {
	/**
	 * The console device.
	 */
	private Device device;
	/**
	 * The state manager.
	 */
	private StateManager stateManager;
	/**
	 * The sprite batch to use throughout the application.
	 */
	private SpriteBatch batch;
	
	@Override
	public void create () {
		// Create the actual representation of the fantasy console device.
		device = new Device();

		// Create the state manager and add the application states.
		stateManager = new StateManager();
		stateManager.addState(new Splash());
		stateManager.addState(new TerminalState(device));
		stateManager.addState(new ScriptEditorState(device));
		stateManager.addState(new RuntimeState(device));

		// Set the initial application state.
		stateManager.setCurrentState("TERMINAL");

		// Create the application sprite batch.
		batch = new SpriteBatch();

		// Capture the system cursor.
		Gdx.input.setCursorCatched(true);
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		Gdx.gl.glClearColor(0.219f, 0.219f, 0.239f, 1);

		// Toggle whether the system cursor is caught on presses of the F12 key.
		if (Gdx.input.isKeyJustPressed(Input.Keys.F12)) {
			Gdx.input.setCursorCatched(!Gdx.input.isCursorCatched());
		}

		// Write the FPS to the console.
		System.out.println(Gdx.graphics.getFramesPerSecond() + " FPS");

		// Update the current application state.
		this.stateManager.update();

		// Render the current application state.
		batch.begin();
		this.stateManager.render(batch);
		batch.end();

		// Process requests to take a screenshot of the application.
		if (Gdx.input.isKeyJustPressed(Input.Keys.F9)) {
			// If the control key was held when taking the screenshot the user is attempting to set the cartridge label.
			if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT) || Gdx.input.isKeyPressed(Input.Keys.CONTROL_RIGHT)) {
				// Set current cartridge label image if a cartridge is loaded.
				if (this.device.getCartridge() != null) {
					this.device.getCartridge().setLabel(Screenshot.capture());
				}
			} else {
				DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd-HH.mm.ss.SSS");
				Screenshot.write(this.device.getFileSystem().getCurrentDirectory(), dateFormat.format(new Date()));
			}
		}
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
