package com.dumbpug.sfc.components.interactable;

/**
 * Represents an interactable element that can be placed within an interactive area.
 */
public class InteractableElement extends Interactable implements IInteractionHandler {
    /**
     * The interaction handler for the interactable element.
     */
    private IInteractionHandler interactionHandler;

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

    /**
     * Creates a new instance of the InteractableElement class.
     * @param x The x of the interactable.
     * @param y The y of the interactable.
     * @param width The width/height of the interactable.
     * @param height The width/height of the interactable.
     */
    public InteractableElement(float x, float y, float width, float height) {
        super(x, y, width, height);
        this.interactionHandler = this;
    }

    @Override
    public boolean onClick(float x, float y) {
        // We will not be processing this click if the area is disabled or the click is outside the area.
        if (!this.isEnabled() || !this.isPositionInBounds(x, y)) {
            return false;
        }

        // Invoke the interaction click handler, passing an x/y position relative to the element.
        return this.interactionHandler.onElementClick(x - this.x, y - this.y);
    }

    /**
     * Attempt to process a click event on the element.
     * @param x The x position of the click relative to the element position.
     * @param y The y position of the click relative to the element position.
     * @return Whether the click was actually processed.
     */
    @Override
    public boolean onElementClick(float x, float y) {
        return false;
    }
}
