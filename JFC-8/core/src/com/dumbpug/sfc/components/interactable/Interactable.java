package com.dumbpug.sfc.components.interactable;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Represents an interatable thing.
 */
public abstract class Interactable {
    /**
     * The x/y of the interactable.
     */
    protected float x,y;
    /**
     * The width/height of the interactable.
     */
    private float width,height;
    /**
     * The layer height of the element.
     */
    private int layer = 0;
    /**
     * Whether the interactive element is enabled.
     */
    private boolean isEnabled = true;

    /**
     * Creates a new instance of the Interactable class.
     * @param x The x of the interactable.
     * @param y The y of the interactable.
     * @param width The width/height of the interactable.
     * @param height The width/height of the interactable.
     */
    public Interactable(float x, float y, float width, float height) {
        this.x      = x;
        this.y      = y;
        this.width  = width;
        this.height = height;
    }

    /**
     * Gets the element layer.
     * @return The element layer.
     */
    public int getLayer() {
        return layer;
    }

    /**
     * Sets the element layer.
     * @param layer The element layer.
     */
    public void setLayer(int layer) {
        this.layer = layer;
    }

    /**
     * Gets whether the element is enabled.
     * @return Whether the element is enabled.
     */
    public boolean isEnabled() {
        return isEnabled;
    }

    /**
     * Sets whether the element is enabled.
     * @param enabled Whether the element is enabled.
     */
    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    /**
     * Gets whether the x/y position is within the bounds of the interactable.
     * @param x The x position.
     * @param y The y position.
     * @return Whether the x/y position is within the bounds of the interactable.
     */
    public boolean isPositionInBounds(float x, float y) {
        if (x < this.x) return false;
        if (x > this.x + this.width) return false;
        if (y < this.y) return false;
        if (y > this.y + this.height) return false;
        return true;
    }

    /**
     * Attempt to draw the interactable element.
     * @param batch The sprite batch.
     */
    public void draw(SpriteBatch batch) {}

    /**
     * Attempt to process a click event on the element.
     * @return Whether the click was actually processed.
     */
    public abstract boolean onClick(float x, float y);
}
