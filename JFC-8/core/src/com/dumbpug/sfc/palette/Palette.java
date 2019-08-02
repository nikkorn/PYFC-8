package com.dumbpug.sfc.palette;

import com.badlogic.gdx.graphics.Color;
import com.dumbpug.sfc.palette.Colour;

import java.util.HashMap;

/**
 * The application colour palette.
 */
public class Palette {
    /**
     * The mapping of application colours to gdx Colour types.
     */
    private static HashMap<com.dumbpug.sfc.palette.Colour, Color> colourMap;

    static {
        colourMap = new HashMap<com.dumbpug.sfc.palette.Colour, Color>() {{
            this.put(com.dumbpug.sfc.palette.Colour.BLACK, new Color(0x38383DFF));
            this.put(com.dumbpug.sfc.palette.Colour.WHITE, new Color(0xEFEFEFFF));
            this.put(com.dumbpug.sfc.palette.Colour.PURPLE, new Color(0x813681FF));
            this.put(com.dumbpug.sfc.palette.Colour.RED, new Color(0xBC414FFF));
            this.put(com.dumbpug.sfc.palette.Colour.ORANGE, new Color(0xEF895EFF));
            this.put(com.dumbpug.sfc.palette.Colour.YELLOW, new Color(0xFAD973FF));
            this.put(com.dumbpug.sfc.palette.Colour.LIME, new Color(0xA7F070FF));
            this.put(com.dumbpug.sfc.palette.Colour.GREEN, new Color(0x38B764FF));
            this.put(com.dumbpug.sfc.palette.Colour.FOREST, new Color(0x257179FF));
            this.put(com.dumbpug.sfc.palette.Colour.NAVY, new Color(0x29366FFF));
            this.put(com.dumbpug.sfc.palette.Colour.BLUE, new Color(0x3B5DC9FF));
            this.put(com.dumbpug.sfc.palette.Colour.CERULEAN, new Color(0x41A6F6FF));
            this.put(com.dumbpug.sfc.palette.Colour.CYAN, new Color(0x73EFF7FF));
            this.put(com.dumbpug.sfc.palette.Colour.GREY, new Color(0xB3B3B3FF));
            this.put(com.dumbpug.sfc.palette.Colour.IRON, new Color(0x737373FF));
            this.put(com.dumbpug.sfc.palette.Colour.CHARCOAL, new Color(0x4B4B4BFF));
        }};
    }

    /**
     * Get the gdx equivalent of the given colour.
     * @param colour The colour.
     * @return The gdx equivalent of the given colour.s
     */
    public static Color getColour(com.dumbpug.sfc.palette.Colour colour) {
        return colourMap.get(colour);
    }

    /**
     * Get the gdx equivalent of the given colour.
     * @param value The colour value as an integer.
     * @return The ordinal of the given colour.
     */
    public static com.dumbpug.sfc.palette.Colour getColour(int value) {
        for (Colour colour : colourMap.keySet()) {
            if (colourMap.get(colour).toIntBits() == value) {
                return colour;
            }
        }

        return null;
    }
}
