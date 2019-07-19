package com.dumbpug.jfc8.scripteditor;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.dumbpug.jfc8.Constants;
import com.dumbpug.jfc8.components.textarea.CursorMovement;
import com.dumbpug.jfc8.components.textarea.TextArea;
import com.dumbpug.jfc8.device.Device;

/**
 * The input processor to handle all input for the script editor application state.
 */
public class ScriptEditorInputProcessor implements InputProcessor {
    /**
     * The console device.
     */
    private Device device;
    /**
     * The editor text area.
     */
    private TextArea editorTextArea;

    /**
     * Creates a new instance of the ScriptEditorInputProcessor class.
     * @param device The console device.
     * @param editorTextArea The editor text area.
     */
    public ScriptEditorInputProcessor(Device device, TextArea editorTextArea) {
        this.device         = device;
        this.editorTextArea = editorTextArea;
    }

    @Override
    public boolean keyDown(int keycode) {
        // Process any arrow keys, as this will impact the text area cursor position.
        if (keycode == Input.Keys.UP) {
            this.editorTextArea.moveCursor(CursorMovement.UP);
            return true;
        } else if (keycode == Input.Keys.DOWN) {
            this.editorTextArea.moveCursor(CursorMovement.DOWN);
            return true;
        } else if (keycode == Input.Keys.LEFT) {
            this.editorTextArea.moveCursor(CursorMovement.LEFT);
            return true;
        } else if (keycode == Input.Keys.RIGHT) {
            this.editorTextArea.moveCursor(CursorMovement.RIGHT);
            return true;
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        // Process any backspace characters.
        if (character == '\b') {
            // Do a backspace!
            this.editorTextArea.backspace();
        } else {
            // We want to ignore any key presses of invalid characters.
            if (Constants.INPUT_VALID_CHARACTERS.indexOf(character) == -1) {
                return false;
            }

            // This character should be inserted at the current editor cursor position.
            this.editorTextArea.insertText(String.valueOf(character));
        }

        // Update the device's script editor to reflect this change.
        this.device.getScriptEditor().setText(this.editorTextArea.getText());

        // The event was handled here.
        return true;
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
        // Scrolling up/down should move the cursor.
        this.editorTextArea.moveCursor(amount > 0 ? CursorMovement.DOWN : CursorMovement.UP);
        return true;
    }
}
