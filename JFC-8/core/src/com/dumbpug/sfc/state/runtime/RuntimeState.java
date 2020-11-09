package com.dumbpug.sfc.state.runtime;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dumbpug.sfc.Constants;
import com.dumbpug.sfc.palette.Colour;
import com.dumbpug.sfc.palette.Palette;
import com.dumbpug.sfc.device.Device;
import com.dumbpug.sfc.state.State;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * The runtime state of the application.
 */
public class RuntimeState extends com.dumbpug.sfc.state.State {
    /**
     * The console device.
     */
    private com.dumbpug.sfc.device.Device device;
    /**
     * The runtime script executor.
     */
    private com.dumbpug.sfc.state.runtime.RuntimeScriptExecutor runtimeScriptExecutor;
    /**
     * The display pixmap.
     */
    private Pixmap displayPixmap;
    /**
     * The display pixmap texture.
     */
    private Texture displayPixmapTexture;

    /**
     * Create a new instance of the RuntimeState class.
     * @param device The console device.
     */
    public RuntimeState(Device device) {
        // Get a reference to the console device.
        this.device = device;

        // TODO Remove this line, it seems to speed up initial transition to this state.
        new ScriptEngineManager().getEngineByName("js");
    }

    @Override
    public void onEntry(State state) {
        // Create the display pixmap.
        this.displayPixmap = new Pixmap( Constants.DISPLAY_WIDTH, Constants.DISPLAY_HEIGHT, Pixmap.Format.RGBA8888);
        this.displayPixmap.setColor(Palette.getColour(Colour.BLACK));
        this.displayPixmap.fill();

        // Attempt to create the runtime script executor.
        try {
            this.runtimeScriptExecutor = new RuntimeScriptExecutor(this.device.getScriptData().getText(), new ScriptAdapter(this.displayPixmap));
        } catch (ScriptException e) {
            // TODO Write script exception details to terminal output to be processed by terminal state.
            System.out.println("ENTRY: " + e);

            // Go to the terminal state so that the user can see the exception.
            this.changeState("TERMINAL");
        }
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

        // Attempt to invoke the script 'update' function.
        if (this.runtimeScriptExecutor != null) {
            try {
                this.runtimeScriptExecutor.invokeScriptUpdate();
            } catch (ScriptException e) {
                // TODO Write script exception details to terminal output to be processed by terminal state.
                System.out.println("UPDATE: " + e);

                // Go to the terminal state so that the user can see the exception.
                this.changeState("TERMINAL");

                return;
            }
        }

        // Dispose of any previously created texture to avoid memory leaks.
        if (this.displayPixmapTexture != null) {
            this.displayPixmapTexture.dispose();
        }

        // Create a drawable texture based on the contents of the display pixmap.
        this.displayPixmapTexture = new Texture(this.displayPixmap, Pixmap.Format.RGB888, false);
    }

    @Override
    public void render(SpriteBatch batch) {
        // Draw the pixmap texture to the screen if we have one.
        if (this.displayPixmapTexture != null) {
            batch.draw(this.displayPixmapTexture, 0, 0, Constants.DISPLAY_WIDTH, Constants.DISPLAY_HEIGHT);
        }
    }

    @Override
    public String getStateName() {
        return "RUNTIME";
    }
}
