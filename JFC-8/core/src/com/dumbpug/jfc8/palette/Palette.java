package com.dumbpug.jfc8.palette;

import com.badlogic.gdx.graphics.Color;
import java.util.HashMap;

/**
 * The application colour palette.
 */
public class Palette {
    /**
     * The mapping of application colours to gdx Colour types.
     */
    private static HashMap<Colour, Color> colourMap;

    static {
        colourMap = new HashMap<Colour, Color>() {{
            this.put(Colour.BLACK, new Color(0x38383DFF));
            this.put(Colour.WHITE, new Color(0xEFEFEFFF));
            this.put(Colour.PURPLE, new Color(0x813681FF));
            this.put(Colour.RED, new Color(0xBC414FFF));
            this.put(Colour.ORANGE, new Color(0xEF895EFF));
            this.put(Colour.YELLOW, new Color(0xFAD973FF));
            this.put(Colour.LIME, new Color(0xA7F070FF));
            this.put(Colour.GREEN, new Color(0x38B764FF));
            this.put(Colour.FOREST, new Color(0x257179FF));
            this.put(Colour.NAVY, new Color(0x29366FFF));
            this.put(Colour.BLUE, new Color(0x3B5DC9FF));
            this.put(Colour.CERULEAN, new Color(0x41A6F6FF));
            this.put(Colour.CYAN, new Color(0x73EFF7FF));
            this.put(Colour.GREY, new Color(0xB3B3B3FF));
            this.put(Colour.IRON, new Color(0x737373FF));
            this.put(Colour.CHARCOAL, new Color(0x4B4B4BFF));
        }};
    }

    /**
     * Get the gdx equivalent of the given colour.
     * @param colour The colour.
     * @return The gdx equivalent of the given colour.s
     */
    public static Color getColour(Colour colour) {
        return colourMap.get(colour);
    }
}
