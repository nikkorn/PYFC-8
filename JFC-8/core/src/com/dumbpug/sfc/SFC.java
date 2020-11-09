package com.dumbpug.sfc;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dumbpug.sfc.device.Device;
import com.dumbpug.sfc.utility.ConcurrentQueue;
import com.dumbpug.sfc.utility.Screenshot;
import com.dumbpug.sfc.state.runtime.RuntimeState;
import com.dumbpug.sfc.state.StateManager;
import com.dumbpug.sfc.state.splash.SplashState;
import com.dumbpug.sfc.state.terminal.TerminalState;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * The SFC application.
 */
public class SFC extends ApplicationAdapter {
	/**
	 * The application message queue from which to read user commands.
	 */
	private ConcurrentQueue<String> commandQueue = new ConcurrentQueue<String>();
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
	/**
	 * The application background.
	 */
	private Sprite background;

	/**
	 * Add a command to the SFC application command queue to be processed as part of its update.
	 * @param command The command to add.
	 */
	public void addCommand(String command) {
		this.commandQueue.add(command);
	}
	
	@Override
	public void create () {
		// Create the actual representation of the fantasy console device.
		device = new Device();

		// Create the state manager and add the application states.
		stateManager = new StateManager();
		stateManager.addState(new SplashState());
		stateManager.addState(new TerminalState(device));
		stateManager.addState(new RuntimeState(device));

		// Set the initial application state.
		stateManager.setCurrentState("SPLASH");

		camera = new OrthographicCamera();
		viewport = new ExtendViewport(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT, camera);
		viewport.apply();

		camera.position.set(Constants.WINDOW_WIDTH / 2, Constants.WINDOW_HEIGHT / 2,0);

		// Create the application sprite batch.
		batch = new SpriteBatch();

		// Create and position the application background sprite.
		background = new Sprite(new Texture(Gdx.files.internal("images/general/full_background.png")));
		background.setSize(Gdx.graphics.getWidth() * 3, Gdx.graphics.getHeight() * 3);
		background.setPosition(-Gdx.graphics.getWidth(), -Gdx.graphics.getHeight());

		// Capture the system cursor.
		// Gdx.input.setCursorCatched(true);
	}

	@Override
	public void render () {
		// Fetch all command queue messages and process each one in order.
		if (this.commandQueue != null) {
			while (this.commandQueue.hasNext()) {
				// Get the next command.
				String command = this.commandQueue.next();

				// TODO Process the command!
				System.out.println("I am SFC! I will process: " + command);

				if (command.equals("exit")) {
					// Exit the application now!
					Gdx.app.exit();
				}
			}
		}

		// Toggle whether the system cursor is caught on presses of the F12 key.
		if (Gdx.input.isKeyJustPressed(Input.Keys.F12)) {
			Gdx.input.setCursorCatched(!Gdx.input.isCursorCatched());
		}

		// Toggle full screen on/off on presses of the F11 key.
		if (Gdx.input.isKeyJustPressed(Input.Keys.F11)){
			if (Gdx.graphics.isFullscreen()) {
				Gdx.graphics.setWindowedMode(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
			} else {
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
		this.background.draw(batch);
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

				// TODO Save somewhere sensible!
				// Screenshot.write(this.device.getFileSystem().getCurrentDirectory(), dateFormat.format(new Date()));
			}
		}
	}

	@Override
	public void resize(int width, int height){
		viewport.update(width, height);
		//camera.position.set(Constants.WINDOW_WIDTH / 2, Constants.WINDOW_HEIGHT / 2,0);
	}
	
	@Override
	public void dispose () {
		// Dispose of the application sprite batch.
		batch.dispose();
	}
}
