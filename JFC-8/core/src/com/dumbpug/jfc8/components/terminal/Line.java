package com.dumbpug.jfc8.components.terminal;

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
     * The line prefix.
     */
    private String prefix;

    /**
     * Creates a new instance of the Line class.
     * @param prefix The line prefix.
     */
    public Line(String prefix) {
        // Set the line prefix.
        this.prefix = prefix;

        // Add the initial empty column.
        columns.add(new Column());
    }

    /**
     * Creates a new instance of the Line class.
     */
    public Line() {
        this(null);
    }

    /**
     * Add a character to the line at the specified column.
     * @param character The character to add.
     * @param column The column to add the character at.
     */
    public void addCharacter(char character, int column) {
        // Add a character to the specified column position.
        this.columns.add(column, new Column(character));
    }

    /**
     * Add a character to the end of the line
     * @param character The character to add.
     */
    public void addCharacter(char character) {
        this.columns.add(this.columns.size() - 1, new Column(character));
    }

    /**
     * Remove a character from the specified column.
     * @param column The column to remove the character from.
     */
    public void removeCharacter(int column) {
        // Check to make sure that this column event exists.
        if (column > this.getColumnCount() + 1) {
            throw new RuntimeException("cannot remove character at column " + column);
        }

        // Remove the column.
        this.columns.remove(column);
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
     * Clear the line.
     */
    public void clear() {
        this.columns = new ArrayList<Column>();

        // Add the initial empty column.
        columns.add(new Column());
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
