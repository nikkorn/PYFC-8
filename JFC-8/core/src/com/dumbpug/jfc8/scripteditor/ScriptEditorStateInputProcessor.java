package com.dumbpug.jfc8.scripteditor;

import com.badlogic.gdx.InputProcessor;
import com.dumbpug.jfc8.components.textarea.TextArea;
import com.dumbpug.jfc8.device.Device;

/**
 * The input processor to handle all input for the script editor application state.
 */
public class ScriptEditorStateInputProcessor implements InputProcessor {
    /**
     * The console device.
     */
    private Device device;
    /**
     * The editor text area.
     */
    private TextArea editorTextArea;

    /**
     * Creates a new instance of the ScriptEditorStateInputProcessor class.
     * @param device The console device.
     * @param editorTextArea The editor text area.
     */
    public ScriptEditorStateInputProcessor(Device device, TextArea editorTextArea) {
        this.device         = device;
        this.editorTextArea = editorTextArea;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        // Update the device's script editor to reflect any change.
        this.device.getScriptEditor().setText(this.editorTextArea.getText());
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
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
