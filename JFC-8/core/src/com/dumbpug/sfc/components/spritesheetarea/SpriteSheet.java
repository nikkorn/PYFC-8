package com.dumbpug.sfc.components.spritesheetarea;

import com.dumbpug.sfc.components.interactable.IInteractionHandler;
import com.dumbpug.sfc.components.interactable.InteractableElement;

public class SpriteSheet extends InteractableElement {
    /**
     * Creates a new instance of the SpriteSheet class.
     * @param x                  The x of the interactable.
     * @param y                  The y of the interactable.
     * @param width              The width/height of the interactable.
     * @param height             The width/height of the interactable.
     * @param interactionHandler The interaction handler for the interactable element.
     */
    public SpriteSheet(float x, float y, float width, float height, IInteractionHandler interactionHandler) {
        super(x, y, width, height, interactionHandler);
    }
}
