package com.dumbpug.sfc.spriteeditor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.dumbpug.sfc.components.interactable.InteractableArea;
import com.dumbpug.sfc.device.Device;

/**
 * The input processor to handle all input for the sprite editor application state.
 */
public class SpriteEditorStateInputProcessor implements InputProcessor {
    /**
     * The console device.
     */
    private com.dumbpug.sfc.device.Device device;
    /**
     * The interactable area that covers the entire sprite editor.
     */
    private InteractableArea editorInteractableArea;

    /**
     * Creates a new instance of the SpriteEditorStateInputProcessor class.
     * @param device The console device.
     * @param editorInteractableArea The editor interactable area.
     */
    public SpriteEditorStateInputProcessor(Device device, InteractableArea editorInteractableArea) {
        this.device                 = device;
        this.editorInteractableArea = editorInteractableArea;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return this.editorInteractableArea.onClick(screenX, Gdx.graphics.getHeight() - screenY);
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