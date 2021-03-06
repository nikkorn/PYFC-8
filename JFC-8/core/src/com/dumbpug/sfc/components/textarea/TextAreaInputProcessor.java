package com.dumbpug.sfc.components.textarea;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.dumbpug.sfc.components.textarea.CursorMovement;
import com.dumbpug.sfc.components.textarea.TextArea;
import com.dumbpug.sfc.Constants;

/**
 * The input processor for a text area component.
 */
public class TextAreaInputProcessor implements InputProcessor {
    /**
     * The text area.
     */
    private com.dumbpug.sfc.components.textarea.TextArea textArea;

    /**
     * Create a new instance of the TextAreaInputProcessor class.
     * @param textArea The text area.
     */
    public TextAreaInputProcessor(TextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public boolean keyDown(int keycode) {
        // Process any arrow keys, as this will impact the text area cursor position.
        if (keycode == Input.Keys.UP) {
            this.textArea.moveCursor(com.dumbpug.sfc.components.textarea.CursorMovement.UP);
            return true;
        } else if (keycode == Input.Keys.DOWN) {
            this.textArea.moveCursor(com.dumbpug.sfc.components.textarea.CursorMovement.DOWN);
            return true;
        } else if (keycode == Input.Keys.LEFT) {
            this.textArea.moveCursor(com.dumbpug.sfc.components.textarea.CursorMovement.LEFT);
            return true;
        } else if (keycode == Input.Keys.RIGHT) {
            this.textArea.moveCursor(com.dumbpug.sfc.components.textarea.CursorMovement.RIGHT);
            return true;
        }

        // Process presses of the forward DELETE key.
        if (keycode == Input.Keys.FORWARD_DEL) {
            this.textArea.delete();
            return true;
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        // We want to ignore any key presses of invalid characters.
        if (Constants.INPUT_VALID_CHARACTERS.indexOf(character) == -1) {
            return false;
        }

        // Process any backspace characters.
        if (character == '\b') {
            // Do a backspace!
            this.textArea.backspace();
        } else {
            // This character should be inserted at the current cursor position.
            this.textArea.insertText(String.valueOf(character));
        }

        // The event was handled here.
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return this.textArea.processPointerDown(screenX, Gdx.graphics.getHeight() - screenY);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return this.textArea.processPointerUp(screenX, Gdx.graphics.getHeight() - screenY);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return this.textArea.processPointerDrag(screenX, Gdx.graphics.getHeight() - screenY);
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        // Scrolling up/down should move the cursor.
        this.textArea.moveCursor(amount > 0 ? com.dumbpug.sfc.components.textarea.CursorMovement.DOWN : CursorMovement.UP);
        return true;
    }
}
