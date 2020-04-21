package com.dumbpug.sfc.state.runtime;

import com.badlogic.gdx.graphics.Pixmap;
import com.dumbpug.sfc.palette.Colour;
import com.dumbpug.sfc.palette.Palette;
import com.dumbpug.sfc.device.input.Button;

public class ScriptAdapter {
    /**
     * The display pixmap.
     */
    private Pixmap pixmap;

    /**
     * Create a new instance of the ScriptAdapter class.
     * @param pixmap The display pixmap.
     */
    ScriptAdapter(Pixmap pixmap) {
        this.pixmap = pixmap;
    }

    /**
     * Get the colour of a pixel
     * @param x The x position of the pixel.
     * @param y The y position of the pixel.
     * @return The colour of the pixel.
     */
    public int getPixel(int x, int y) {
        return Palette.getColour(this.pixmap.getPixel(x, y)).ordinal();
    }

    /**
     * Set the colour of a pixel.
     * @param x The x position of the pixel.
     * @param y The y position of the pixel.
     * @param colour The colour of the pixel.
     */
    public void setPixel(int x, int y, int colour) {
        if (!isValidColourInt(colour)) {
            return;
        }
        this.pixmap.setColor(Palette.getColour(Colour.values()[colour]));
        this.pixmap.drawPixel(x, y);
    }

    /**
     * Draw a line.
     * @param x0 The x position of the first point.
     * @param y0 The y position of the first point.
     * @param x1 The x position of the second point.
     * @param y1 The y position of the second point.
     * @param colour The colour of the line.
     */
    public void line(int x0, int y0, int x1, int y1, int colour) {
        if (!isValidColourInt(colour)) {
            return;
        }
        this.pixmap.setColor(Palette.getColour(Colour.values()[colour]));
        this.pixmap.drawLine(x0, y0, x1, y1);
    }

    /**
     * Draw a circle.
     * @param x The x position of the circle origin.
     * @param y The y position of the circle origin.
     * @param radius The radius of the circle.
     * @param colour The colour of the circle.
     * @param fill Whether to fill the circle.
     */
    public void circle(int x, int y, int radius, int colour, boolean fill) {
        if (!isValidColourInt(colour)) {
            return;
        }
        this.pixmap.setColor(Palette.getColour(Colour.values()[colour]));
        if (fill) {
            this.pixmap.fillCircle(x, y, radius);
        } else {
            this.pixmap.drawCircle(x, y, radius);
        }
    }

    /**
     * Draw a rectangle.
     * @param x The x position of the rectangle.
     * @param y The y position of the rectangle.
     * @param width The width of the rectangle.
     * @param height The height of the rectangle.
     * @param colour The colour of the rectangle.
     * @param fill Whether to fill the rectangle.
     */
    public void rect(int x, int y, int width, int height, int colour, boolean fill) {
        if (!isValidColourInt(colour)) {
            return;
        }
        this.pixmap.setColor(Palette.getColour(Colour.values()[colour]));
        if (fill) {
            this.pixmap.fillRectangle(x, y, width, height);
        } else {
            this.pixmap.drawRectangle(x, y, width, height);
        }
    }

    /**
     * Clear the screen.
     */
    public void cls() {
        this.pixmap.setColor(Palette.getColour(Colour.BLACK));
        this.pixmap.fill();
    }

    /**
     * Fill the screen with the given colour.
     * @param colour The colour with which to fill the screen.
     */
    public void fills(int colour) {
        if (!isValidColourInt(colour)) {
            return;
        }
        this.pixmap.setColor(Palette.getColour(Colour.values()[colour]));
        this.pixmap.fill();
    }

    /**
     * Gets whether the given button is currently being pressed.
     * @param button The button to check.
     * @return Whether the given button is currently being pressed.
     */
    public boolean pressed(com.dumbpug.sfc.device.input.Button button) {
        // TODO
        return false;
    }

    /**
     * Gets whether the given button was just pressed.
     * @param button The button to check.
     * @return Whether the given button was just pressed.
     */
    public boolean justPressed(Button button) {
        // TODO
        return false;
    }

    /**
     * Get whether an integer value maps to a valid 'Colour' enum.
     * @param colour The integer representing a colour.
     * @return Whether an integer value maps to a valid 'Colour' enum.
     */
    private boolean isValidColourInt(int colour) {
        return colour >= 0 && colour < Colour.values().length;
    }
}
