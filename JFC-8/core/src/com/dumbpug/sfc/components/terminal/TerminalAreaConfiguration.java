package com.dumbpug.sfc.components.terminal;

import com.dumbpug.sfc.palette.Colour;

/**
 * Configuration for a terminal area component.
 */
public class TerminalAreaConfiguration {
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
     * The input line prefix.
     */
    public String inputPrompt = "> ";
}
