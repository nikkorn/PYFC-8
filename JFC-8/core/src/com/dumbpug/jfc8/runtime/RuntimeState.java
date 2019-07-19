package com.dumbpug.jfc8.runtime;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
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

    private Texture _pixmapTexture;
    /**
     * Create a new instance of the RuntimeState class.
     * @param device The console device.
     */
    public RuntimeState(Device device) {
        // Get a reference to the console device.
        this.device = device;

        Pixmap _pixmap = new Pixmap( 384, 256, Pixmap.Format.RGBA8888 );
        _pixmap.setColor(Color.RED);
        _pixmap.fillRectangle(12, 12, 20, 20);
        _pixmapTexture = new Texture(_pixmap, Pixmap.Format.RGB888, false);
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
        batch.draw(_pixmapTexture,0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public String getStateName() {
        return "RUNTIME";
    }
}
