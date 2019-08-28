package com.dumbpug.sfc.components.paintarea;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dumbpug.sfc.Constants;
import com.dumbpug.sfc.palette.Colour;
import com.dumbpug.sfc.resources.SpriteEditorResources;
import com.dumbpug.sfc.utility.Position;

import java.util.ArrayList;

/**
 * A control that represents a paintable area.
 */
public class PaintArea {
    /**
     * The input processor for this paint area.
     */
    private PaintAreaInputProcessor inputProcessor = new PaintAreaInputProcessor(this);
    /**
     * The paintable target.
     */
    private IPaintableTarget paintableTarget;
    /**
     * The pixels collection.
     */
    private Pixels pixels;
    /**
     * The x/y position of the paint area.
     */
    private float x, y;
    /**
     * The size (width/height) of the paint area.
     */
    private float size;
    /**
     * The current input mode.
     */
    private InputMode inputMode = InputMode.PAINT_SMALL;
    /**
     * The paint selection.
     * This is only applicable when the current input mode is 'selection'.
     */
    private Selection selection = null;
    /**
     * The current colour.
     */
    private Colour colour = Colour.WHITE;

    /**
     * Creates a new instance of the PaintArea class.
     * @param target The paintable target.
     * @param x The x position of the area.
     * @param y The y position of the area.
     * @param size The size of the area.
     */
    public PaintArea(IPaintableTarget target, float x, float y, float size) {
        this.paintableTarget = target;
        this.x               = x;
        this.y               = y;
        this.size            = size;
        this.pixels          = new Pixels(target);
    }

    /**
     * Gets the x position of the paint area.
     * @return The x position of the paint area.
     */
    public float getX() {
        return x;
    }

    /**
     * Gets the y position of the paint area.
     * @return The the y position of the paint area.
     */
    public float getY() {
        return y;
    }

    /**
     * Gets the size of the control.
     * @return The size of the control.
     */
    public float getSize() {
        return size;
    }

    /**
     * Sets the input mode.
     * @param inputMode The input mode.
     */
    public void setInputMode(InputMode inputMode) {
        this.inputMode = inputMode;
    }

    /**
     * Sets the current colour.
     * @param colour The current colour.
     */
    public void setColour(Colour colour) {
        this.colour = colour;
    }

    /**
     * Gets the paint editor input processor.
     * @return The paint editor input processor.
     */
    public PaintAreaInputProcessor getInputProcessor() {
        return inputProcessor;
    }

    /**
     * Update the paint area to reflect changes made to the paintable target.
     */
    public void update() {
        // If the input mode is not 'selection' then any existing selection will have to be thrown away.
        if (this.inputMode != InputMode.SELECTION) {
            this.selection = null;
        }

        // Update the pixels collection. TODO Eventually only do this when sprite data changes.
        this.pixels.update(this.paintableTarget);
    };

    /**
     * Try to process a pointer hover event and return whether it was processed by the paint area.
     * @param pointerX The pointer x position.
     * @param pointerY The pointer y position.
     * @return Whether the pointer hover event was processed by the paint area.
     */
    public boolean processPointerHover(int pointerX, int pointerY) {
        return true;
    }

    /**
     * Try to process a pointer down event and return whether it was processed by the paint area.
     * @param pointerX The pointer x position.
     * @param pointerY The pointer y position.
     * @return Whether the pointer down event was processed by the paint area.
     */
    public boolean processPointerDown(int pointerX, int pointerY) {
        if (!this.isPointerInTextAreaBounds(pointerX, pointerY)) {
            return false;
        }

        // Clear any previously made selection.
        this.selection = null;

        // Get the relative x/y pointer position in the paint area.
        int relativeX = pointerX - (int)this.getX();
        int relativeY = (int) this.size - (int) (pointerY - this.getY());

        // Get the position of the pixel within the context of the paintable area.
        int pixelX = (relativeX / Constants.DISPLAY_PIXEL_SIZE) / (Constants.SPRITE_EDITOR_PAINT_AREA_SIZE / this.paintableTarget.getSize()) / Constants.DISPLAY_PIXEL_SIZE;
        int pixelY = (relativeY / Constants.DISPLAY_PIXEL_SIZE) / (Constants.SPRITE_EDITOR_PAINT_AREA_SIZE / this.paintableTarget.getSize()) / Constants.DISPLAY_PIXEL_SIZE;

        // How we process the click will depend on the current input mode.
        switch (this.inputMode) {
            case PAINT_SMALL:
            case PAINT_MEDIUM:
            case PAINT_LARGE:
                // Paint the pixel at the specified x/y location with a thickness defined by the input mode.
                this.paint(pixelX, pixelY);
                break;
            case FILL:
                // Carry out a fill operation using the current colour from the given x/y pixel origin.
                this.fill(pixelX, pixelY);
                break;
            case SELECTION:
                // Create selection.
                this.selection = new Selection(pixelX, pixelY);
                break;
        }

        return true;
    }

    /**
     * Try to process a pointer drag event and return whether it was processed by the paint area.
     * @param pointerX The pointer x position.
     * @param pointerY The pointer y position.
     * @return Whether the pointer drag event was processed by the paint area.
     */
    public boolean processPointerDrag(int pointerX, int pointerY) {
        if (!this.isPointerInTextAreaBounds(pointerX, pointerY)) {
            return false;
        }

        // TODO Maybe use mouse delta to determine which pixels we have dragged across between updates.

        // Get the relative x/y pointer position in the paint area.
        int relativeX = pointerX - (int)this.getX();
        int relativeY = (int) this.size - (int) (pointerY - this.getY());

        // Get the position of the pixel within the context of the paintable area.
        int pixelX = (relativeX / Constants.DISPLAY_PIXEL_SIZE) / (Constants.SPRITE_EDITOR_PAINT_AREA_SIZE / this.paintableTarget.getSize()) / Constants.DISPLAY_PIXEL_SIZE;
        int pixelY = (relativeY / Constants.DISPLAY_PIXEL_SIZE) / (Constants.SPRITE_EDITOR_PAINT_AREA_SIZE / this.paintableTarget.getSize()) / Constants.DISPLAY_PIXEL_SIZE;

        // How we process the drag will depend on the current input mode.
        switch (this.inputMode) {
            case PAINT_SMALL:
            case PAINT_MEDIUM:
            case PAINT_LARGE:
                // Paint the pixel at the specified x/y location with a thickness defined by the input mode.
                this.paint(pixelX, pixelY);
                break;
            case SELECTION:
                // Extend the selection target if the selection exists.
                if (this.selection != null) {
                    this.selection.getTarget().setX(pixelX);
                    this.selection.getTarget().setY(pixelY);
                }
                break;
        }

        return true;
    }

    /**
     * Process a pointer up event and return whether it was processed by the paint area.
     * @param pointerX The pointer x position.
     * @param pointerY The pointer y position.
     * @return Whether the pointer up event was processed by the paint area.
     */
    public boolean processPointerUp(int pointerX, int pointerY) {
        return true;
    }

    public void clearSelection() {};
    public void rotateSelection() {};
    public void flipSelectionVertically() {}
    public void flipSelectionHorizontally() {}

    /**
     * Draw the paint area.
     * @param batch The sprite batch.
     */
    public void draw(SpriteBatch batch) {
        // Draw the pixels display pixmap.
        batch.draw(this.pixels.getTexture(), x, y, size, size);

        // Draw the selection outline if there is a selection.
        // TODO Do this properly!
        if (this.selection != null) {
            Sprite paintSelectionSprite = SpriteEditorResources.getPaintSelection();
            paintSelectionSprite.setPosition(x + this.selection.getX(), y + this.selection.getY());
            paintSelectionSprite.setSize(this.selection.getWidth(), this.selection.getHeight());
            paintSelectionSprite.draw(batch);
        }
    }

    /**
     * Paint the pixel at the specified x/y location with a thickness defined by the input mode.
     * @param pixelX The pixel x position.
     * @param pixelY The pixel Y position.
     */
    private void paint(int pixelX, int pixelY) {
        switch (this.inputMode) {
            case PAINT_SMALL:
                this.paintableTarget.setPixel(pixelX, pixelY, this.colour);
                break;
            case PAINT_MEDIUM:
                this.paintableTarget.setPixel(pixelX, pixelY, this.colour);
                this.paintableTarget.setPixel(pixelX - 1, pixelY, this.colour);
                this.paintableTarget.setPixel(pixelX, pixelY - 1, this.colour);
                this.paintableTarget.setPixel(pixelX - 1, pixelY -1, this.colour);
                break;
            case PAINT_LARGE:
                this.paintableTarget.setPixel(pixelX, pixelY, this.colour);
                this.paintableTarget.setPixel(pixelX, pixelY - 1, this.colour);
                this.paintableTarget.setPixel(pixelX, pixelY - 2, this.colour);
                this.paintableTarget.setPixel(pixelX - 1, pixelY, this.colour);
                this.paintableTarget.setPixel(pixelX - 1, pixelY -1, this.colour);
                this.paintableTarget.setPixel(pixelX - 1, pixelY - 2, this.colour);
                this.paintableTarget.setPixel(pixelX - 2, pixelY, this.colour);
                this.paintableTarget.setPixel(pixelX - 2, pixelY - 1, this.colour);
                this.paintableTarget.setPixel(pixelX - 2, pixelY - 2, this.colour);
                break;
        }
    }

    /**
     * Carry out a fill operation using the current colour from the given x/y pixel origin.
     * @param pixelOriginX The x pixel origin.
     * @param pixelOriginY The y pixel origin.
     */
    private void fill(int pixelOriginX, int pixelOriginY) {
        // Get the colour of the origin pixel.
        Colour originColour = this.paintableTarget.getPixel(pixelOriginX, pixelOriginY);

        // If the origin pixel colour already matches the currently selected colour then there is nothing to do.
        if (originColour == this.colour) {
            return;
        }

        ArrayList<Position<Integer>> fillablePixelPositions = new ArrayList<Position<Integer>>();

        // Call a recursive method that populates a list of positions to update in batch for speed improvements.
        this.collectFillablePixelPositions(pixelOriginX, pixelOriginY, originColour, new ArrayList<String>(), fillablePixelPositions);

        // Set the colour for all fill-able pixel positions.
        this.paintableTarget.setPixels(fillablePixelPositions, this.colour);
    }

    /**
     * Find all reachable and fill-able pixel positions from an origin pixel position.
     * @param pixelX The origin x pixel position.
     * @param pixelY The origin y pixel position.
     * @param originalColour The original colour at the original pixel position.
     * @param visited The list of visited positions.
     * @param positions The list fill-able positions found.
     */
    private void collectFillablePixelPositions(int pixelX, int pixelY, Colour originalColour, ArrayList<String> visited, ArrayList<Position<Integer>> positions) {
        // Ignore any requests to set values for pixels outside the paintable area.
        if (pixelX < 0 || pixelY < 0 || pixelX >= this.getSize() || pixelY >= this.getSize()) {
            return;
        }

        // Have we already checked this position?
        if (visited.contains(pixelX + "_" + pixelY)) {
            return;
        }

        // Does the pixel at the given position need updating?
        if (this.paintableTarget.getPixel(pixelX, pixelY) != originalColour) {
            return;
        }

        // We have found a valid fill-able pixel position.
        positions.add(new Position<Integer>(pixelX, pixelY));

        // We need to keep track of which position we have visited to avoid re-doing a position.
        visited.add(pixelX + "_" + pixelY);

        // Move outwards to other pixel positions.
        collectFillablePixelPositions(pixelX - 1, pixelY, originalColour, visited, positions);
        collectFillablePixelPositions(pixelX + 1, pixelY, originalColour, visited, positions);
        collectFillablePixelPositions(pixelX, pixelY - 1, originalColour, visited, positions);
        collectFillablePixelPositions(pixelX, pixelY + 1, originalColour, visited, positions);
    }

    /**
     * Gets whether the given x/y pointer position is within the text area bounds.
     * @param screenX The x position of the pointer.
     * @param screenY The y position of the pointer.
     * @return Whether the given x/y pointer position is within the text area bounds.
     */
    private boolean isPointerInTextAreaBounds(int screenX, int screenY) {
        if (screenX < this.getX()) {
            return false;
        }
        if (screenX > (this.getX() + this.getSize())) {
            return false;
        }
        if (screenY < this.getY()) {
            return false;
        }
        if (screenY > (this.getY() + this.getSize())) {
            return false;
        }

        return true;
    }
}
