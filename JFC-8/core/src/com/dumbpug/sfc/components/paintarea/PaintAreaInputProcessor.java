package com.dumbpug.sfc.components.paintarea;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

public class PaintAreaInputProcessor implements InputProcessor {
    /**
     * The paint area.
     */
    private PaintArea paintArea;

    /**
     * Creates a new instance of the PaintAreaInputProcessor class.
     * @param paintArea The paint area.
     */
    public PaintAreaInputProcessor(PaintArea paintArea) {
        this.paintArea = paintArea;
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
        return this.paintArea.processPointerDown(screenX, Gdx.graphics.getHeight() - screenY);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return this.paintArea.processPointerUp(screenX, Gdx.graphics.getHeight() - screenY);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return this.paintArea.processPointerDrag(screenX, Gdx.graphics.getHeight() - screenY);
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
