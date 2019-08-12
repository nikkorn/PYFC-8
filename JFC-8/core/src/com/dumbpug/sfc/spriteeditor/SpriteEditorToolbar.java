package com.dumbpug.sfc.spriteeditor;

import com.dumbpug.sfc.components.interactable.InteractableArea;
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

        // TODO Create toolbar button interactable elements.
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
}
