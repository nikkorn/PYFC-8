package com.dumbpug.sfc.components.interactable;

public interface IInteractionHandler {

    /**
     * Attempt to process a click event on the element.
     * @param x The x position of the click relative to the element position.
     * @param y The y position of the click relative to the element position.
     * @return Whether the click was actually processed.
     */
    boolean onElementClick(float x, float y);
}
