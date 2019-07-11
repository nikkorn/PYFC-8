package com.dumbpug.jfc8.components.textarea;

import com.dumbpug.jfc8.palette.Colour;

/**
 * Configuration for a text area component.
 */
public class TextAreaConfiguration {
    /**
     * The colour to use for the text.
     * TODO Use Colour
     */
    public Colour fontColour = Colour.WHITE;
    /**
     * The colour to use for the text area background.
     * TODO Use Colour
     */
    public Colour backgroundColour = Colour.NAVY;
    /**
     * The colour to use for the cursor.
     * TODO Use Colour
     */
    public Colour cursorColour = Colour.LIME;
    /**
     * The colour to use for the text selection.
     * TODO Use Colours
     */
    public Colour selectionColour = Colour.FOREST;
    /**
     * Whether to include line numbers to the left of the text area.
     */
    public boolean includeLineNumbers = false;
}
