package com.dumbpug.sfc.spriteeditor;

import com.dumbpug.sfc.Constants;
import com.dumbpug.sfc.components.interactable.IInteractionHandler;
import com.dumbpug.sfc.components.interactable.InteractableArea;
import com.dumbpug.sfc.components.interactable.InteractableElement;
import com.dumbpug.sfc.components.paintarea.InputMode;
import com.dumbpug.sfc.palette.Colour;

/**
 * The toolbar wrapping the colour selection, input selection and selection transformation controls.
 */
public class SpriteEditorToolbar extends InteractableArea {
    /**
     * The current colour.
     */
    private Colour colour = Colour.WHITE;
    /**
     * The current input mode.
     */
    private InputMode inputMode = InputMode.PAINT_SMALL;

    /**
     * Creates a new instance of the SpriteEditorToolbar class.
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public SpriteEditorToolbar(float x, float y, float width, float height) {
        super(x, y, width, height);

        // Create the button interactable elements for the colour bar.
        this.addInteractable(this.createColourBarButton(14,54, Colour.CHARCOAL));
        this.addInteractable(this.createColourBarButton(22,54, Colour.IRON));
        this.addInteractable(this.createColourBarButton(30,54, Colour.GREY));
        this.addInteractable(this.createColourBarButton(38,54, Colour.CYAN));
        this.addInteractable(this.createColourBarButton(46,54, Colour.CERULEAN));
        this.addInteractable(this.createColourBarButton(54,54, Colour.BLUE));
        this.addInteractable(this.createColourBarButton(62,54, Colour.NAVY));
        this.addInteractable(this.createColourBarButton(70,54, Colour.FOREST));
        this.addInteractable(this.createColourBarButton(78,54, Colour.GREEN));
        this.addInteractable(this.createColourBarButton(86,54, Colour.LIME));
        this.addInteractable(this.createColourBarButton(94,54, Colour.YELLOW));
        this.addInteractable(this.createColourBarButton(102,54, Colour.ORANGE));
        this.addInteractable(this.createColourBarButton(110,54, Colour.RED));
        this.addInteractable(this.createColourBarButton(118,54, Colour.PURPLE));
        this.addInteractable(this.createColourBarButton(126,54, Colour.BLACK));
        this.addInteractable(this.createColourBarButton(134,54, Colour.WHITE));
    }

    /**
     * Gets the current colour.
     * @return The current colour.
     */
    public Colour getColour() {
        return colour;
    }

    /**
     * Gets the current input mode.
     * @return The current input mode.
     */
    public InputMode getInputMode() {
        return inputMode;
    }

    /**
     * Create a colour bar button.
     * @param x The x position.
     * @param y The y position.
     * @param colourSelection The colour to be made the current colour on clicking the button.
     * @return A colour bar button.
     */
    private InteractableElement createColourBarButton(float x , float y, final Colour colourSelection) {
        return new InteractableElement(
            x * Constants.DISPLAY_PIXEL_SIZE,
            y * Constants.DISPLAY_PIXEL_SIZE,
            8 * Constants.DISPLAY_PIXEL_SIZE,
            8 * Constants.DISPLAY_PIXEL_SIZE,
            new IInteractionHandler() {
                @Override
                public boolean onElementClick(float x, float y) {
                    colour = colourSelection;
                    return true;
                }
            });
    }
}
