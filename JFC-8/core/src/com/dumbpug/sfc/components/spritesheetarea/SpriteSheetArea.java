package com.dumbpug.sfc.components.spritesheetarea;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dumbpug.sfc.Constants;
import com.dumbpug.sfc.components.interactable.InteractableElement;
import com.dumbpug.sfc.components.paintarea.IPaintableTarget;
import com.dumbpug.sfc.device.SpriteData;
import com.dumbpug.sfc.palette.Colour;
import com.dumbpug.sfc.palette.Palette;
import com.dumbpug.sfc.resources.SpriteEditorResources;
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
        super(x, y, width, height);

        this.spriteData = spriteData;

        // Create the paintable target which will always be represented by the current sheet and selection.
        this.paintableTarget = new IPaintableTarget() {
            @Override
            public int getSize() {
                switch (viewMode) {
                    case SMALL:
                        return Constants.SPRITE_EDITOR_SELECTION_SMALL_PIXELS;
                    case MEDIUM:
                        return Constants.SPRITE_EDITOR_SELECTION_MEDIUM_PIXELS;
                    case LARGE:
                        return Constants.SPRITE_EDITOR_SELECTION_LARGE_PIXELS;
                    default:
                        throw new RuntimeException("unexpected view mode type");
                }
            }

            @Override
            public void setPixel(int x, int y, Colour colour) {
                // Ignore any requests to set values for pixels outside the paintable area.
                if (x < 0 || y < 0 || x >= this.getSize() || y >= this.getSize()) {
                    return;
                }

                // Convert the relative x/y position to an absolute sprite sheet position.
                int targetX = (selectionPosition.getX() * Constants.SPRITE_EDITOR_SHEET_UNIT) + x;
                int targetY = (selectionPosition.getY() * Constants.SPRITE_EDITOR_SHEET_UNIT) + y;
                spriteData.setPixel(targetX, targetY, colour);

                // The sprite sheet pixmap will have to be updated to reflect this change.
                updateSheetPixmapPixel(targetX, targetY);
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

        // Refresh the sheet pixmap to reflect the device sprite data.
        refresh();
    }

    /**
     * Gets the current paintable target.
     * @return The current paintable target.
     */
    public IPaintableTarget getPaintableTarget() {
        return paintableTarget;
    }

    /**
     * Sets the view mode.
     * @param viewMode The view mode.
     */
    public void setViewMode(ViewMode viewMode) {
        this.viewMode = viewMode;

        // Clamp the selection position to ensure the selection box stays within the bounds of the sprite sheet panel.
        this.clampSelectionToArea();
    }

    /**
     * Attempt to process a click event on the element.
     * @param x The x position of the click relative to the element position.
     * @param y The y position of the click relative to the element position.
     * @return Whether the click was actually processed.
     */
    @Override
    public boolean onElementClick(float x, float y) {
        // Find the panel position based on the x/y click location.
        int positionX = (int)(x / Constants.DISPLAY_PIXEL_SIZE) / Constants.SPRITE_EDITOR_SHEET_UNIT;
        int positionY = (int)((this.height - y) / Constants.DISPLAY_PIXEL_SIZE) / Constants.SPRITE_EDITOR_SHEET_UNIT;

        // Update the selection position.
        this.selectionPosition.setX(positionX);
        this.selectionPosition.setY(positionY);

        // Clamp the selection position to ensure the selection box stays within the bounds of the sprite sheet panel.
        this.clampSelectionToArea();

        // We handled the click.
        return true;
    }

    /**
     * Refresh the sprite sheet pixmap to match the device sprite data.
     */
    public void refresh() {
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
     * Draw the sprite sheet area.
     * @param batch The sprite batch.
     */
    public void draw(SpriteBatch batch) {
        // Draw the sprite sheet pixmap texture.
        batch.draw(this.displayPixmapTexture, x, y, width, height);

        // Draw the selection box.
        Sprite selectionBox = null;
        int positionYOffset = 0;
        switch (this.viewMode) {
            case SMALL:
                selectionBox    = SpriteEditorResources.getSelectionBoxSmall();
                positionYOffset = 1;
                break;
            case MEDIUM:
                selectionBox    = SpriteEditorResources.getSelectionBoxMedium();
                positionYOffset = 2;
                break;
            case LARGE:
                selectionBox    = SpriteEditorResources.getSelectionBoxLarge();
                positionYOffset = 4;
                break;
        }

        float selectionX = this.x + ((this.selectionPosition.getX() * Constants.SPRITE_EDITOR_SHEET_UNIT) * Constants.DISPLAY_PIXEL_SIZE);
        float selectionY = (this.y + this.height) - (((this.selectionPosition.getY() + positionYOffset) * Constants.SPRITE_EDITOR_SHEET_UNIT) * Constants.DISPLAY_PIXEL_SIZE);
        selectionBox.setPosition(selectionX, selectionY);
        selectionBox.draw(batch);
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

    /**
     * Clamp the selection position to ensure the selection box stays within the bounds of the sprite sheet panel.
     */
    private void clampSelectionToArea() {
        int selectionUnitsSize = 0;
        switch (this.viewMode) {
            case SMALL:
                selectionUnitsSize = 1;
                break;
            case MEDIUM:
                selectionUnitsSize = 2;
                break;
            case LARGE:
                selectionUnitsSize = 4;
                break;
        }

        this.selectionPosition.setX(Math.min(this.selectionPosition.getX(), Constants.SPRITE_EDITOR_SHEET_UNITS_WIDE - selectionUnitsSize + 1));
        this.selectionPosition.setY(Math.min(this.selectionPosition.getY(), Constants.SPRITE_EDITOR_SHEET_UNITS_HIGH - selectionUnitsSize + 1));
    }
}
