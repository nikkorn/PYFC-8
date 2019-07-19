package com.dumbpug.jfc8.runtime;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dumbpug.jfc8.device.Device;
import com.dumbpug.jfc8.state.State;

/**
 * The runtime state of the application.
 */
public class RuntimeState extends State {
    /**
     * The console device.
     */
    private Device device;

    /**
     * Create a new instance of the RuntimeState class.
     * @param device The console device.
     */
    public RuntimeState(Device device) {
        // Get a reference to the console device.
        this.device = device;
    }

    @Override
    public void onEntry(State state) {

    }

    @Override
    public void onExit() {

    }

    @Override
    public void update() {
        // Check whether the user is attempting to move state.
        if (Gdx.input.isKeyJustPressed(Input.Keys.F1)) {
            this.changeState("TERMINAL");
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.F2)) {
            this.changeState("SCRIPT_EDITOR");
        }
    }

    @Override
    public void render(SpriteBatch batch) {

    }

    @Override
    public String getStateName() {
        return "RUNTIME";
    }
}
