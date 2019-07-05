package com.dumbpug.jfc8;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dumbpug.jfc8.state.StateManager;
import com.dumbpug.jfc8.state.states.Splash;
import com.dumbpug.jfc8.state.states.Terminal;

/**
 * The JFC8 application.
 */
public class JFC8 extends ApplicationAdapter {
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
		// Create the state manager and add the application states.
		stateManager = new StateManager();
		stateManager.addState(new Splash());
		stateManager.addState(new Terminal());

		// Set the initial application state.
		stateManager.setCurrentState("SPLASH");

		// Create the application sprite batch.
		batch = new SpriteBatch();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Update the current application state.
		this.stateManager.update();

		// Render the current application state.
		batch.begin();
		this.stateManager.render(batch);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
