package com.dumbpug.sfc.spriteeditor;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dumbpug.sfc.Constants;
import com.dumbpug.sfc.components.interactable.IInteractionHandler;
import com.dumbpug.sfc.components.interactable.InteractableElement;
import com.dumbpug.sfc.components.paintarea.InputMode;
import com.dumbpug.sfc.resources.SpriteEditorResources;

public class ToolbarInputModeButton extends InteractableElement {
    /**
     * The input mode.
     */
    private InputMode inputMode;

    /**
     * Creates a new instance of the ToolbarInputModeButton class.
     * @param x
     * @param y
     * @param inputMode
     * @param interactionHandler
     */
    public ToolbarInputModeButton(float x, float y, InputMode inputMode, IInteractionHandler interactionHandler) {
        super(x, y, 14 * Constants.DISPLAY_PIXEL_SIZE, 13 * Constants.DISPLAY_PIXEL_SIZE, interactionHandler);
        this.inputMode = inputMode;
    }

    /**
     * Gets the input mode of this button.
     * @return The input mode of this button.
     */
    public InputMode getInputMode() {
        return inputMode;
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
                    this.y - (int)pointer.getHeight() * 0.5f
            );
            pointer.draw(batch);
        }
    }
}