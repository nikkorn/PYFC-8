package com.dumbpug.jfc8.state.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dumbpug.jfc8.Constants;
import com.dumbpug.jfc8.state.State;

/**
 * The splash state of the application.
 */
public class Splash extends State {
    /**
     * The time since the state was last entered.
     */
    private long startTime;
    /**
     * The splash logo sprite.
     */
    private Sprite logo;

    /**
     * Create a new instance of the Splash class.
     */
    public Splash() {
        // Create and position the logo sprite.
        logo = new Sprite(new Texture(Gdx.files.internal("images/splash/splash_logo.png")));
        logo.setOrigin(logo.getWidth() / 2,logo.getHeight() / 2);
        logo.setSize(Constants.SPLASH_LOGO_SIZE, Constants.SPLASH_LOGO_SIZE);
        logo.setPosition((Gdx.graphics.getWidth() / 2) - (logo.getWidth() / 2), (Gdx.graphics.getHeight() / 2) - (logo.getHeight() / 2));
    }

    @Override
    public void onEntry(State state) {
        // Reset the splash start time.
        this.startTime = System.currentTimeMillis();
    }

    @Override
    public void onExit() {
    }

    @Override
    public void update() {
        // Have we shown the splash long enough? If so then move to the terminal.
        if ((System.currentTimeMillis() - this.startTime) >= Constants.SPLASH_DURATION) {
            this.changeState("TERMINAL");
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        this.logo.draw(batch);
    }

    @Override
    public String getStateName() {
        return "SPLASH";
    }
}
