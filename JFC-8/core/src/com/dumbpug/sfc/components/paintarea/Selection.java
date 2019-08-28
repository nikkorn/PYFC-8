package com.dumbpug.sfc.components.paintarea;

import com.dumbpug.sfc.utility.Position;

/**
 * Represents an area of selection within a paint-able area.
 */
public class Selection {
    /**
     * The selection origin.
     */
    private Position<Integer> origin;
    /**
     * The selection target.
     */
    private Position<Integer> target;

    /**
     * Creates a new instance of the Selection class.
     * @param originX The origin x position.
     * @param originY The origin y position.
     */
    public Selection(int originX, int originY) {
        this.origin = new Position<Integer>(originX, originY);
        this.target = new Position<Integer>(originX, originY);
    }

    public int getOriginX() {
        return this.origin.getX();
    }

    public int getOriginY() {
        return this.origin.getY();
    }

    public int getX() {
        return Math.min(this.origin.getX(), this.target.getX());
    }

    public int getY() {
        return Math.min(this.origin.getY(), this.target.getY());
    }

    public int getWidth() {
        return Math.abs(this.origin.getX() - this.target.getX());
    }

    public int getHeight() {
        return Math.abs(this.origin.getY() - this.target.getY());
    }

    public Position<Integer> getTarget() {
        return this.target;
    }
}
