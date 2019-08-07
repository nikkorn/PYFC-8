package com.dumbpug.sfc.components.paintarea;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dumbpug.sfc.palette.Colour;
import com.dumbpug.sfc.palette.Palette;

/**
 * A control that represents a paintable area.
 */
public class PaintArea {
    /**
     * The paintable target.
     */
    private IPaintableTarget paintableTarget;
    /**
     * The x/y position of the paint area.
     */
    private float x, y;
    /**
     * The width/height of the paint area.
     */
    private float width, height;
    /**
     * The current input mode.
     */
    private InputMode inputMode = InputMode.PAINT_SMALL;
    /**
     * The current colour.
     */
    private Colour colour = Colour.NOT_SET;
    /**
     * The display pixmap.
     */
    private Pixmap displayPixmap;

    /**
     * Creates a new instance of the PaintArea class.
     * @param target The paintable target.
     * @param x The x position of the area.
     * @param y The y position of the area.
     * @param width The width of the area.
     * @param height The height of the area.
     */
    public PaintArea(IPaintableTarget target, float x, float y, float width, float height) {
        this.paintableTarget = target;
        this.x               = x;
        this.y               = y;
        this.width           = width;
        this.height          = height;

        // Create the display pixmap.
        this.displayPixmap = new Pixmap( 128, 128, Pixmap.Format.RGBA8888);
        this.displayPixmap.setColor(Palette.getColour(Colour.BLACK));
        this.displayPixmap.fill();
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
     * Gets the width of the control.
     * @return The width of the control.
     */
    public float getWidth() {
        return width;
    }

    /**
     * Gets the height of the control.
     * @return The height of the control.
     */
    public float getHeight() {
        return height;
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
        if (screenX > (this.getX() + this.getWidth())) {
            return false;
        }
        if (screenY < this.getY()) {
            return false;
        }
        if (screenY > (this.getY() + this.getHeight())) {
            return false;
        }

        return true;
    }
}
