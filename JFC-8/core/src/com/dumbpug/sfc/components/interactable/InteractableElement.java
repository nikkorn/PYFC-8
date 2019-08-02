package com.dumbpug.sfc.components.interactable;

import com.dumbpug.sfc.components.interactable.IInteractionHandler;

/**
 * Represents an interactable element that can be placed within an interactive area.
 */
public class InteractableElement extends Interactable {
    /**
     * The interaction handler for the interactable element.
     */
    private com.dumbpug.sfc.components.interactable.IInteractionHandler interactionHandler;

    /**
     * Creates a new instance of the InteractableElement class.
     * @param x The x of the interactable.
     * @param y The y of the interactable.
     * @param width The width/height of the interactable.
     * @param height The width/height of the interactable.
     * @param interactionHandler The interaction handler for the interactable element.
     */
    public InteractableElement(float x, float y, float width, float height, IInteractionHandler interactionHandler) {
        super(x, y, width, height);
        this.interactionHandler = interactionHandler;
    }

    @Override
    public boolean onClick(float x, float y) {
        // We will not be processing this click if the area is disabled or the click is outside the area.
        if (!this.isEnabled() || !this.isPositionInBounds(x, y)) {
            return false;
        }

        return this.interactionHandler.onClick();
    }
}
