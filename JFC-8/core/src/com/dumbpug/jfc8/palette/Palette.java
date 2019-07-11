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
            this.put(Colour.BLACK, Color.BLACK);
            this.put(Colour.WHITE, Color.WHITE);
            this.put(Colour.PLUM, Color.PURPLE);
            this.put(Colour.RED, Color.RED);
            this.put(Colour.ORANGE, Color.ORANGE);
            this.put(Colour.YELLOW, Color.YELLOW);
            this.put(Colour.LIME, Color.LIME);
            this.put(Colour.GREEN, Color.GREEN);
            this.put(Colour.FOREST, Color.FOREST);
            this.put(Colour.NAVY, Color.NAVY);
            this.put(Colour.BLUE, Color.BLUE);
            this.put(Colour.CERULEAN, Color.SKY);
            this.put(Colour.CYAN, Color.CYAN);
            this.put(Colour.GREY, Color.LIGHT_GRAY);
            this.put(Colour.IRON, Color.GRAY);
            this.put(Colour.CHARCOAL, Color.DARK_GRAY);
        }};
    }

    /**
     * Get the gdx equivalent of the given colour.
     * @param colour The colour.
     * @return The gdx equivalent of the given colou
     */
    public static Color getColour(Colour colour) {
        return colourMap.get(colour);
    }
}
