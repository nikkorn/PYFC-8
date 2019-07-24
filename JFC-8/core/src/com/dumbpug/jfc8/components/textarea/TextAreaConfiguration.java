package com.dumbpug.jfc8.components.textarea;

import com.dumbpug.jfc8.palette.Colour;

/**
 * Configuration for a text area component.
 */
public class TextAreaConfiguration {
    /**
     * The colour to use for the text.
     */
    public Colour fontColour = Colour.WHITE;
    /**
     * The colour to use for the cursor.
     */
    public Colour cursorColour = Colour.GREY;
    /**
     * The colour to use for the text selection.
     */
    public Colour selectionColour = Colour.GREY;
    /**
     * Whether to include line numbers to the left of the text area.
     */
    public boolean includeLineNumbers = false;
    /**
     * The colour to use for the line number font.
     */
    public Colour lineNumberFontColour = Colour.WHITE;
    /**
     * Whether to match previous line indentation on new lines.
     */
    public boolean matchIndentationOnNewLine = true;
}
