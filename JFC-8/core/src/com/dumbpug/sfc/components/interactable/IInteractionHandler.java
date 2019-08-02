package com.dumbpug.sfc.components.interactable;

public interface IInteractionHandler {

    /**
     * Attempt to process a click event on the element.
     * @return Whether the click was actually processed.
     */
    boolean onClick();
}
