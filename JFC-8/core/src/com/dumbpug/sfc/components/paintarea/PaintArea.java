package com.dumbpug.sfc.components.paintarea;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dumbpug.sfc.palette.Colour;

/**
 * A control that represents a paintable area.
 */
public class PaintArea {
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
     * The current colour.
     */
    private Colour colour = Colour.NOT_SET;

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
     * Update the paint area to reflect changes made to the paintable target.
     */
    public void update() {
        // Update the pixels collection.
        this.pixels.update(this.paintableTarget);

        // TODO Clear any selection.
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
        return true;
    }

    /**
     * Try to process a pointer drag event and return whether it was processed by the paint area.
     * @param pointerX The pointer x position.
     * @param pointerY The pointer y position.
     * @return Whether the pointer drag event was processed by the paint area.
     */
    public boolean processPointerDrag(int pointerX, int pointerY) {
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
        // TODO Draw the display pixmap.
        // TODO Draw the selection outline if there is a selection.
        // TODO Draw the pixel outline if the cursor is hovering over the area.
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
