package com.dumbpug.jfc8.components.textarea;

/**
 * Represents a cursor positioned at a line and column, as well as the current text selection.
 */
public class Cursor {
    /**
     * The line at which the cursor is placed.
     */
    private int line = 0;
    /**
     * The column at which the cursors is placed.
     */
    private int column = 0;
    /**
     * The number of characters in the selection preceding the cursor.
     */
    private int selectionLength = 0;

    /**
     * Gets the line number at which the cursor is placed.
     * @return The line number at which the cursor is placed.
     */
    public int getLineNumber() {
        return line;
    }

    /**
     * Sets the line number at which the cursor is placed.
     * @param line The line number at which the cursor is placed.
     */
    public void setLineNumber(int line) {
        this.line = line;
    }

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

    /**
     * Gets the number of characters in the selection preceding the cursor.
     * @return The number of characters in the selection preceding the cursor.
     */
    public int getSelectionLength() {
        return selectionLength;
    }

    /**
     * Sets the number of characters in the selection preceding the cursor.
     * @param selectionLength The number of characters in the selection preceding the cursor.
     */
    public void setSelectionLength(int selectionLength) {
        this.selectionLength = selectionLength;
    }

    /**
     * Reset the cursor position and selection.
     */
    public void reset() {
        this.line            = 0;
        this.column          = 0;
        this.selectionLength = 0;
    }
}
