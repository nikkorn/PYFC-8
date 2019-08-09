package com.dumbpug.sfc.utility;

/**
 * An x/y position.
 */
public class Position<TType extends Number> {
    /**
     * The x position.
     */
    private TType x;
    /**
     * The y position.
     */
    private TType y;

    /**
     * Creates a new instance of the Position class.
     * @param x The x position.
     * @param y The y position.
     */
    public Position(TType x, TType y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Get the x position.
     * @return The x position.
     */
    public TType getX() {
        return x;
    }

    /**
     * Set the x position.
     * @param x The x position.
     */
    public void setX(TType x) {
        this.x = x;
    }

    /**
     * Get the y position.
     * @return The y position.
     */
    public TType getY() {
        return y;
    }

    /**
     * Set the y position.
     * @param y The y position.
     */
    public void setY(TType y) {
        this.y = y;
    }

    /**
     * Get a copy of this position.
     * The state of the copy will not be changed if the original is modified.
     * @return A copy of this position.
     */
    public Position copy() {
        return new Position(this.getX(), this.getY());
    }
}