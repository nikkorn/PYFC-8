package com.dumbpug.jfc8.runtime;

import com.badlogic.gdx.graphics.Pixmap;
import com.dumbpug.jfc8.device.input.Button;
import com.dumbpug.jfc8.palette.Colour;
import com.dumbpug.jfc8.palette.Palette;

public class ScriptAdapter {
    /**
     * The display pixmap.
     */
    private Pixmap pixmap;

    /**
     * Create a new instance of the ScriptAdapter class.
     * @param pixmap The display pixmap.
     */
    public ScriptAdapter(Pixmap pixmap) {
        this.pixmap = pixmap;
    }

    public int getPixel(int x, int y) {
        return Palette.getColour(this.pixmap.getPixel(x, y)).ordinal();
    }

    public void setPixel(int x, int y, int colour) {
        this.pixmap.setColor(Palette.getColour(Colour.values()[colour]));
        this.pixmap.drawPixel(x, y);
    }

    public void line(int x0, int y0, int x1, int y1, int colour) {
        this.pixmap.setColor(Palette.getColour(Colour.values()[colour]));
        this.pixmap.drawLine(x0, y0, x1, y1);
    }

    public void circle(int x, int y, int radius, int colour, boolean fill) {
        // TODO
    }

    /**
     * Draw a rect.
     * @param x0
     * @param y0
     * @param x1
     * @param y1
     * @param colour
     * @param fill
     */
    public void rect(int x0, int y0, int x1, int y1, int colour, boolean fill) {
        // TODO
    }

    /**
     * Clear the screen
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
        this.pixmap.setColor(Palette.getColour(Colour.values()[colour]));
        this.pixmap.fill();
    }

    /**
     * Gets whether the given button is currently being pressed.
     * @param button The button to check.
     * @return Whether the given button is currently being pressed.
     */
    public boolean pressed(Button button) {
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
}
