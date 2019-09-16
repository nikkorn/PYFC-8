package com.dumbpug.sfc;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dumbpug.sfc.device.Device;
import com.dumbpug.sfc.display.Screenshot;
import com.dumbpug.sfc.runtime.RuntimeState;
import com.dumbpug.sfc.scripteditor.ScriptEditorState;
import com.dumbpug.sfc.spriteeditor.SpriteEditorState;
import com.dumbpug.sfc.state.StateManager;
import com.dumbpug.sfc.state.states.Splash;
import com.dumbpug.sfc.terminal.TerminalState;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The SFC application.
 */
public class SFC extends ApplicationAdapter {
	/**
	 * The console device.
	 */
	private com.dumbpug.sfc.device.Device device;
	/**
	 * The state manager.
	 */
	private com.dumbpug.sfc.state.StateManager stateManager;
	/**
	 * The sprite batch to use throughout the application.
	 */
	private SpriteBatch batch;
	/**
	 * The application camera.
	 */
	private OrthographicCamera camera;
	/**
	 * The application viewport.
	 */
	private Viewport viewport;
	
	@Override
	public void create () {
		// Create the actual representation of the fantasy console device.
		device = new Device();

		// Create the state manager and add the application states.
		stateManager = new StateManager();
		stateManager.addState(new Splash());
		stateManager.addState(new TerminalState(device));
		stateManager.addState(new ScriptEditorState(device));
		stateManager.addState(new SpriteEditorState(device));
		stateManager.addState(new RuntimeState(device));

		// Set the initial application state.
		stateManager.setCurrentState("TERMINAL");

		camera = new OrthographicCamera();
		viewport = new FitViewport(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT, camera);
		viewport.apply();

		camera.position.set(camera.viewportWidth / 2,camera.viewportHeight / 2,0);

		// Create the application sprite batch.
		batch = new SpriteBatch();

		// Capture the system cursor.
		// Gdx.input.setCursorCatched(true);
	}

	@Override
	public void render () {
		// Toggle whether the system cursor is caught on presses of the F12 key.
		if (Gdx.input.isKeyJustPressed(Input.Keys.F12)) {
			Gdx.input.setCursorCatched(!Gdx.input.isCursorCatched());
		}

		// Toggle full screen on/off on presses of the F11 key.
		if (Gdx.input.isKeyPressed(Input.Keys.F11)){
			if (Gdx.graphics.isFullscreen()) {
				camera.zoom = 1;
				Gdx.graphics.setWindowedMode(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
			} else {
				camera.zoom = 1.2f;
				Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
			}
		}

		// Write the FPS to the console.
		// System.out.println(Gdx.graphics.getFramesPerSecond() + " FPS");

		// Update the current application state.
		this.stateManager.update();

		// Update the sprite batch position to match the camera.
		camera.update();
		batch.setProjectionMatrix(camera.combined);

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		Gdx.gl.glClearColor(0.219f, 0.219f, 0.239f, 1);

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
					this.device.getCartridge().setLabel(com.dumbpug.sfc.display.Screenshot.capture());
				}
			} else {
				DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd-HH.mm.ss.SSS");
				Screenshot.write(this.device.getFileSystem().getCurrentDirectory(), dateFormat.format(new Date()));
			}
		}
	}

	@Override
	public void resize(int width, int height){
		viewport.update(width, height);
		camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
