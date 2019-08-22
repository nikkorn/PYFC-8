package com.dumbpug.sfc.spriteeditor;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dumbpug.sfc.Constants;
import com.dumbpug.sfc.components.interactable.IInteractionHandler;
import com.dumbpug.sfc.components.interactable.InteractableElement;
import com.dumbpug.sfc.palette.Colour;
import com.dumbpug.sfc.resources.SpriteEditorResources;

public class ToolbarColourButton extends InteractableElement {
    /**
     * The colour of the button.
     */
    private Colour colour;

    /**
     * Creates a new instance of the ToolbarColourButton class.
     * @param x
     * @param y
     * @param colour
     * @param interactionHandler
     */
    public ToolbarColourButton(float x, float y, Colour colour, IInteractionHandler interactionHandler) {
        super(x, y, 8 * Constants.DISPLAY_PIXEL_SIZE, 8 * Constants.DISPLAY_PIXEL_SIZE, interactionHandler);
        this.colour = colour;
    }

    /**
     * Gets the colour of this button.
     * @return The colour of this button.
     */
    public Colour getColour() {
        return colour;
    }

    /**
     * Draw the button.
     * @param batch The sprite batch.
     * @param isActive Whether this button is active.
     */
    public void draw(SpriteBatch batch, boolean isActive) {
        // Draw the button selection pointer underneath this button if it is the active one.
        if (isActive) {
            Sprite pointer = SpriteEditorResources.getButtonSelectionPointer();
            pointer.setPosition(
                    this.x - (int)((pointer.getWidth() / 2) - (this.width / 2)),
                    this.y - (int)pointer.getHeight() * 0.66f
            );
            pointer.draw(batch);
        }
    }
}
