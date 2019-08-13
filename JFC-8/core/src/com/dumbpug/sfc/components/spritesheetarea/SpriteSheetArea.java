package com.dumbpug.sfc.components.spritesheetarea;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dumbpug.sfc.Constants;
import com.dumbpug.sfc.components.interactable.IInteractionHandler;
import com.dumbpug.sfc.components.interactable.InteractableElement;
import com.dumbpug.sfc.components.paintarea.IPaintableTarget;
import com.dumbpug.sfc.components.paintarea.ViewMode;
import com.dumbpug.sfc.device.SpriteData;
import com.dumbpug.sfc.palette.Colour;
import com.dumbpug.sfc.palette.Palette;
import com.dumbpug.sfc.utility.Position;

/**
 * An area in which one of multiple sprite sheets can have selections made on it.
 */
public class SpriteSheetArea extends InteractableElement {
    /**
     * The sprite data.
     */
    private SpriteData spriteData;
    /**
     * The paintable target defined by the current sheet and selection.
     */
    private IPaintableTarget paintableTarget;
    /**
     * The current position of the panel selection where 0/0 is top-left.
     */
    private Position<Integer> selectionPosition = new Position<Integer>(0, 0);
    /**
     * The current selection view mode.
     */
    private ViewMode viewMode = ViewMode.SMALL;
    /**
     * The sprite sheet pixmap.
     */
    private Pixmap spriteSheetPixmap;
    /**
     * The sprite sheet pixmap texture
     */
    private Texture displayPixmapTexture;

    /**
     * Creates a new instance of the SpriteSheetArea class.
     * @param spriteData The sprite sheet data.
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public SpriteSheetArea(final SpriteData spriteData, float x, float y, float width, float height) {
        super(x, y, width, height, new IInteractionHandler() {
            @Override
            public boolean onClick(float x, float y) { return onClick(x, y); }
        });

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
                int targetX = (selectionPosition.getX() * Constants.SPRITE_EDITOR_SHEET_UNIT) + x;
                int targetY = (selectionPosition.getY() * Constants.SPRITE_EDITOR_SHEET_UNIT) + y;
                spriteData.setPixel(targetX, targetY, colour);

                // The sprite sheet pixmap will have to be updated to reflect this change.
                updateSheetPixmapPixel(x, y);
            }

            @Override
            public Colour getPixel(int x, int y) {
                int targetX = (selectionPosition.getX() * Constants.SPRITE_EDITOR_SHEET_UNIT) + x;
                int targetY = (selectionPosition.getY() * Constants.SPRITE_EDITOR_SHEET_UNIT) + y;
                return spriteData.getPixel(targetX, targetY);
            }
        };

        // Create the sprite sheet pixmap.
        this.spriteSheetPixmap = new Pixmap(Constants.SPRITE_SHEET_PIXELS_WIDTH, Constants.SPRITE_SHEET_PIXELS_HEIGHT, Pixmap.Format.RGBA8888);

        // Create a drawable texture based on the contents of the display pixmap.
        this.displayPixmapTexture = new Texture(this.spriteSheetPixmap, Pixmap.Format.RGB888, false);

        // Update the sheet pixmap to reflect the device sprite data.
        updateSheetPixmap();
    }

    /**
     * Gets the current paintable target.
     * @return The current paintable target.
     */
    public IPaintableTarget getPaintableTarget() {
        return paintableTarget;
    }

    /**
     * Attempt to handle a click in the interactable element.
     * @return Whether the click was handled.
     */
    public boolean onClick() {
        return false;
    }

    /**
     * Draw the sprite sheet area.
     * @param batch The sprite batch.
     */
    public void draw(SpriteBatch batch) {
        // Draw the sprite sheet pixmap texture.
        batch.draw(this.displayPixmapTexture, x, y, width, height);
    }

    /**
     * Update the sprite sheet pixmap to match the device sprite data.
     */
    private void updateSheetPixmap() {
        for (int x = 0; x < Constants.SPRITE_SHEET_PIXELS_WIDTH; x++) {
            for (int y = 0; y < Constants.SPRITE_SHEET_PIXELS_HEIGHT; y++) {
                this.spriteSheetPixmap.setColor(Palette.getColour(spriteData.getPixel(x, y)));
                this.spriteSheetPixmap.drawPixel(x, y);
            }
        }

        // Dispose of any previously created texture to avoid memory leaks.
        this.displayPixmapTexture.dispose();

        // Create a drawable texture based on the contents of the display pixmap.
        this.displayPixmapTexture = new Texture(this.spriteSheetPixmap, Pixmap.Format.RGB888, false);
    }

    /**
     * Update the sprite sheet pixmap to match the device sprite data for a pixel.
     * @param x The pixel x position.
     * @param y The pixel y position.
     */
    private void updateSheetPixmapPixel(int x, int y) {
        this.spriteSheetPixmap.setColor(Palette.getColour(spriteData.getPixel(x, y)));
        this.spriteSheetPixmap.drawPixel(x, y);

        // Dispose of any previously created texture to avoid memory leaks.
        this.displayPixmapTexture.dispose();

        // Create a drawable texture based on the contents of the display pixmap.
        this.displayPixmapTexture = new Texture(this.spriteSheetPixmap, Pixmap.Format.RGB888, false);
    }
}
