package com.dumbpug.jfc8.components.textarea;

import com.badlogic.gdx.graphics.Color;
import com.dumbpug.jfc8.palette.Colour;

/**
 * Configuration for a text area component.
 */
public class TextAreaConfiguration {
    /**
     * The colour to use for the text.
     * TODO Use Colour
     */
    public Color fontColour = Color.WHITE;
    /**
     * The colour to use for the text area background.
     * TODO Use Colour
     */
    public Color backgroundColour = Color.NAVY;
    /**
     * The colour to use for the cursor.
     * TODO Use Colour
     */
    public Color cursorColour = Color.LIME;
    /**
     * The colour to use for the text selection.
     * TODO Use Colour
     */
    public Color selectionColour = Color.FOREST;
    /**
     * Whether to include line numbers to the left of the text area.
     */
    public boolean includeLineNumbers = false;
}
