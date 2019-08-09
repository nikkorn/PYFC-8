package com.dumbpug.sfc.device;

import com.dumbpug.sfc.Constants;
import com.dumbpug.sfc.palette.Colour;
import java.util.Arrays;

/**
 * Represents the sprite data in the context of the console device.
 */
public class SpriteData {
    /**
     * The array of pixels defined by a colour.
     */
    private Colour[][] pixels = new Colour[Constants.SPRITE_SHEET_PIXELS_WIDTH][Constants.SPRITE_SHEET_PIXELS_HEIGHT];

    /**
     * Creates a new instance of the SpriteData class.
     */
    public SpriteData() {
        // Initialise ever pixel to be the colour black.
        for (Colour[] row : this.pixels) {
            Arrays.fill(row, Colour.BLACK);
        }
    }

    /**
     * Sets the pixel colour at the x/y position.
     * @param x The x position.
     * @param y The y position.
     * @param colour The colour.
     */
    public void setPixel(int x, int y, Colour colour) {
        this.pixels[x][y] = colour;
    }

    /**
     * Gets the pixel colour at the x/y position.
     * @param x The x position.
     * @param y The y position.
     * @return The pixel colour at the x/y position.
     */
    public Colour getPixel(int x, int y) {
        return this.pixels[x][y];
    }
}
