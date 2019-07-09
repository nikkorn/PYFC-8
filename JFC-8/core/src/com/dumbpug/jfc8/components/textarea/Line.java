package com.dumbpug.jfc8.components.textarea;

import java.util.ArrayList;

/**
 * Represents a single line in a text area.
 */
public class Line {
    /**
     * The columns in the line.
     */
    private ArrayList<Column> columns = new ArrayList<Column>();

    /**
     * Creates a new instance of the Line class.
     */
    public Line() {
        // Add the initial empty column.
        columns.add(new Column());
    }

    /**
     * Gets the number of populated columns in the line.
     * @return The number of populated columns in the line.
     */
    public int getColumnCount() {
        return this.columns.size();
    }
}
