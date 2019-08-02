package com.dumbpug.sfc.components.textarea;

/**
 * Represents an origin as line and column at which text selection begins.
 */
public class SelectionOrigin {
    /**
     * The line at which the cursor is placed.
     */
    private int line = 0;
    /**
     * The column at which the cursors is placed.
     */
    private int column = 0;

    /**
     * Creates a new instance of the SelectionOrigin class.
     * @param line The line of the selection origin.
     * @param column The column of the selection origin.
     */
    public SelectionOrigin(int line, int column) {
        this.line   = line;
        this.column = column;
    }

    /**
     * Gets the line number of the selection origin.
     * @return The line number of the selection origin.
     */
    public int getLine() {
        return line;
    }

    /**
     * Gets the column number of the selection origin.
     * @return The column number of the selection origin.
     */
    public int getColumn() {
        return column;
    }
}
