package com.dumbpug.sfc.components.paintarea;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.dumbpug.sfc.Constants;
import com.dumbpug.sfc.palette.Colour;
import com.dumbpug.sfc.palette.Palette;
import java.util.HashMap;

public class Pixels {
    /**
     * The map of pixel positions to colours.
     */
    private HashMap<String, Colour> pixelMap = new HashMap<String, Colour>();
    /**
     * The pixmap.
     */
    private Pixmap pixelsPixmap = new Pixmap(Constants.SPRITE_EDITOR_PAINT_AREA_SIZE, Constants.SPRITE_EDITOR_PAINT_AREA_SIZE, Pixmap.Format.RGBA8888);
    /**
     * The size of the pixels area in pixels wide/high.
     */
    private int pixelSize;

    /**
     * Creates a new instance of the Pixels class.
     * @param target The paintable target.
     */
    public Pixels(IPaintableTarget target) {
        this.update(target);
    }

    /**
     * Update the pixel map and pixmap based on the state of the paintable target.
     * @param target The paintable target.
     */
    public void update(IPaintableTarget target) {
        // Clear the pixel map.
        this.pixelMap.clear();

        // Update the pixels size.
        this.pixelSize = target.getSize();

        // Update the pixels map.
        for (int y = 0; y < this.pixelSize; y++) {
            for (int x = 0; x < this.pixelSize; x++) {
                // Set the pixel in the pixel map.
                this.setPixel(x, y, target.getPixel(x, y));
            }
        }
    }

    /**
     * Set the pixel at the x/y position.
     * @param x The x position.
     * @param y The y position.
     * @param colour The colour.
     */
    public void setPixel(int x, int y, Colour colour) {
        // Set the pixel in the pixel map.
        this.pixelMap.put(x + "_" + y, colour);

        // Get the size of the pixel when drawn to the pixmap.
        int pixelDrawSize = Constants.SPRITE_EDITOR_PAINT_AREA_SIZE / this.pixelSize;

        // Draw the pixel onto the pixmap.
        this.pixelsPixmap.setColor(Palette.getColour(colour));
        this.pixelsPixmap.fillRectangle(x * pixelDrawSize, y * pixelDrawSize, pixelDrawSize, pixelDrawSize);
    }

    /**
     * Gets the pixels area texture.
     * @return The pixels area texture.
     */
    public Texture getTexture() {
        return new Texture(this.pixelsPixmap, Pixmap.Format.RGB888, false);
    }
}
