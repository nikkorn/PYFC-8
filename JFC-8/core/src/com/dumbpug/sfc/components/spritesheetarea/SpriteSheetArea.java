package com.dumbpug.sfc.components.spritesheetarea;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.dumbpug.sfc.components.interactable.InteractableArea;
import com.dumbpug.sfc.components.paintarea.IPaintableTarget;
import com.dumbpug.sfc.components.paintarea.ViewMode;
import com.dumbpug.sfc.components.textarea.Line;
import com.dumbpug.sfc.device.SpriteData;
import com.dumbpug.sfc.palette.Colour;
import com.dumbpug.sfc.palette.Palette;
import com.dumbpug.sfc.utility.Position;

/**
 * An area in which one of multiple sprite sheets can have selections made on it.
 */
public class SpriteSheetArea extends InteractableArea {
    /**
     * The sprite data.
     */
    private SpriteData spriteData;
    /**
     * The paintable target defined by the current sheet and selection.
     */
    private IPaintableTarget paintableTarget;
    /**
     * The x/y position of the text area.
     */
    private float x, y;
    /**
     * The width/height of the text area.
     */
    private float width, height;
    /**
     * The current position of the panel selection where 0/0 is top-left.
     */
    private Position selectionPosition = new Position(0, 0);
    /**
     * The current selection view mode.
     */
    private ViewMode viewMode = ViewMode.SMALL;

    /**
     * Creates a new instance of the SpriteSheetArea class.
     * @param spriteData The sprite sheet data.
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public SpriteSheetArea(SpriteData spriteData, float x, float y, float width, float height) {
        super(x, y, width, height);
        this.spriteData = spriteData;

        // Create the paintable target which will always be represented by the current sheet and selection.
        this.paintableTarget = new IPaintableTarget() {
            @Override
            public int getSize() {
                switch (viewMode) {
                    case SMALL:
                        return 16;
                    case MEDIUM:
                        return 32;
                    case LARGE:
                        return 64;
                    default:
                        throw new RuntimeException("unexpected view mode type");
                }
            }

            @Override
            public void setPixel(int x, int y, Colour colour) {
                // TODO
            }

            @Override
            public Colour getPixel(int x, int y) {
                // TODO
                return Colour.BLACK;
            }
        };

        // TODO Add the sprite sheets as interactable elements within this area. Make them here???
    }

    /**
     * Gets the current paintable target.
     * @return The current paintable target.
     */
    public IPaintableTarget getPaintableTarget() {
        return paintableTarget;
    }

    /**
     * Draw the sprite sheet area.
     * @param batch The sprite batch.
     */
    public void draw(SpriteBatch batch) {
        // TODO
    }
}
