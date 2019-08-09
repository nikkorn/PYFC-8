package com.dumbpug.sfc.scripteditor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.dumbpug.sfc.components.interactable.InteractableArea;
import com.dumbpug.sfc.components.textarea.TextArea;
import com.dumbpug.sfc.device.Device;

/**
 * The input processor to handle all input for the script editor application state.
 */
public class ScriptEditorStateInputProcessor implements InputProcessor {
    /**
     * The console device.
     */
    private com.dumbpug.sfc.device.Device device;
    /**
     * The editor text area.
     */
    private com.dumbpug.sfc.components.textarea.TextArea editorTextArea;
    /**
     * The toolbar area.
     */
    private InteractableArea toolbarArea;

    /**
     * Creates a new instance of the ScriptEditorStateInputProcessor class.
     * @param device The console device.
     * @param editorTextArea The editor text area.
     * @param toolbarArea The toolbar area.
     */
    public ScriptEditorStateInputProcessor(Device device, TextArea editorTextArea, InteractableArea toolbarArea) {
        this.device         = device;
        this.editorTextArea = editorTextArea;
        this.toolbarArea    = toolbarArea;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        // Update the device's script editor to reflect any change.
        this.device.getScriptData().setText(this.editorTextArea.getText());
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (this.toolbarArea.onClick(screenX, Gdx.graphics.getHeight() - screenY)) {
            // Update the device's script editor to reflect any change made by using the toolbar.
            this.device.getScriptData().setText(this.editorTextArea.getText());
            return true;
        }

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
