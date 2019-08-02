package com.dumbpug.sfc.components.textarea;

/**
 * Represents a position range.
 */
public class Range {
    /**
     * The first position.
     */
    private Position positionA;
    /**
     * The second position.
     */
    private Position positionB;
    /**
     * Whether positionB follows positionA.
     */
    private boolean arePositionsSequential;

    /**
     * Creates a new instance of the Range class.
     * @param positionA The first position.
     * @param positionB The second position.
     */
    public Range(Position positionA, Position positionB) {
        this.positionA = positionA;
        this.positionB = positionB;

        // Determine whether position B follows or equals position A.
        this.arePositionsSequential = (positionA.getLine() < positionB.getLine() ||
                (positionA.getLine() == positionB.getLine() && positionA.getColumn() <= positionB.getColumn()));
    }

    /**
     * Gets the minimum position.
     * @return The minimum position.
     */
    public Position getMin() {
        return arePositionsSequential ? positionA : positionB;
    }

    /**
     * Gets the maximum position.
     * @return The maximum position.
     */
    public Position getMax() {
        return arePositionsSequential ? positionB : positionA;
    }
}
