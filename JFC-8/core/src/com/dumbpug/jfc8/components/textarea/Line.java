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
     * Add a character to the line at the specified column.
     * @param character The character to add.
     * @param column The column to add the character at.
     */
    public void addCharacter(char character, int column) {
        // Add a character to the column position at which the cursor currently resides at.
        this.columns.add(column, new Column(character));
    }

    /**
     * Get the character at the given column, or null if there isn't one.
     * @param column The column from which to get the character.
     * @return The character at the given column, or null if there isn't on
     */
    public Character getCharacter(int column) {
        // Is there even a character at the column?
        if (column >= this.getColumnCount()) {
            return null;
        }

        // Return the character at the column.
        return this.columns.get(column).getCharacter();
    }

    /**
     * Get rid of any columns following on from the specified column and return their characters.
     * @param column Teh column number to remove columns from.
     * @return The chopped characters.
     */
    public ArrayList<Character> chop(int column) {
        ArrayList<Character> chopped = new ArrayList<Character>();

        // Gather any characters in columns following 'column', this does not include our empty end column.
        for (int columnIndex = column; columnIndex < this.getColumnCount(); columnIndex++) {
            chopped.add(this.columns.get(columnIndex).getCharacter());
        }

        // Get rid of the chopped columns.
        for (int columnIndex = this.getColumnCount() - 1; columnIndex >= column; columnIndex--) {
            this.columns.remove(columnIndex);
        }

        return chopped;
    }

    /**
     * Gets the number of populated columns in the line, this excludes the empty terminating column.
     * @return The number of populated columns in the line, this excludes the empty terminating column.
     */
    public int getColumnCount() {
        return this.columns.size() - 1;
    }

    /**
     * Gets the text area text.
     * @return The text area text.
     */
    public String getText() {
        String value = "";

        for (Column column : this.columns) {
            if (column.getCharacter() != null) {
                value += column.getCharacter();
            }
        }

        return value;
    }
}
