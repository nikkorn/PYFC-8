package com.dumbpug.sfc.state.splash;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dumbpug.sfc.Constants;
import com.dumbpug.sfc.state.State;

/**
 * The splash state of the application.
 */
public class SplashState extends com.dumbpug.sfc.state.State {
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
    public SplashState() {
        // Create and position the logo sprite.
        logo = new Sprite(new Texture(Gdx.files.internal("images/splash/splash_logo.png")));
        logo.setSize(Constants.DISPLAY_WIDTH, Constants.DISPLAY_HEIGHT);
        logo.setPosition(0, 0);
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
        // Have we shown the splash long enough? If so then move to the runtime.
        if ((System.currentTimeMillis() - this.startTime) >= Constants.SPLASH_DURATION) {
            this.changeState("RUNTIME");
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
