package com.dumbpug.jfc8.components.textarea;

/**
 * Represents a static line/column position in the text area.
 */
public class Position {
    /**
     * The line.
     */
    private int line;
    /**
     * The column.
     */
    private int column;

    /**
     * Creates a new instance of the Position class.
     * @param line The line.
     * @param column The column.
     */
    public Position(int line, int column) {
        this.line   = line;
        this.column = column;
    }

    /**
     * Gets the line number.
     * @return The line number.
     */
    public int getLine() {
        return line;
    }

    /**
     * Gets the column number.
     * @return The column number.
     */
    public int getColumn() {
        return column;
    }

    /**
     * Gets whether the specified position follows this one.
     * @param position The position.
     * @return Whether the specified position follows this one.
     */
    public boolean follows(Position position) {
        return !(this.getLine() < position.getLine()
                || (this.getLine() == position.getLine() && this.getColumn() <= position.getColumn()));
    }
}
