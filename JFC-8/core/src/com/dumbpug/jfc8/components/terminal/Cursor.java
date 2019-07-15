package com.dumbpug.jfc8.components.terminal;

/**
 * Represents a cursor positioned at a column within a terminal input line.
 */
public class Cursor {
    /**
     * The column at which the cursors is placed.
     */
    private int column = 0;

    /**
     * Gets the column number at which the cursor is placed.
     * @return The column number at which the cursor is placed.
     */
    public int getColumnNumber() {
        return column;
    }

    /**
     * Sets the column number at which the cursor is placed.
     * @param column The column number at which the cursor is placed.
     */
    public void setColumnNumber(int column) {
        this.column = column;
    }
}
