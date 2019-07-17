package com.dumbpug.jfc8.components.terminal;

import com.dumbpug.jfc8.palette.Colour;

/**
 * Represents a single column that can be populated with a character in a line.
 */
public class Column {
    /**
     * The character in the column, or null if there is no character.
     */
    private Character character = null;
    /**
     * The colour of the character in the column.
     */
    private Colour colour;

    /**
     * Create a new instance of the Column class.
     * @param colour The column colour.
     */
    public Column(Colour colour) {
        this.colour = colour;
    }

    /**
     * Create a new instance of the Column class.
     * @param character The character in the column.
     * @param colour The column colour.
     */
    public Column(char character, Colour colour) {
        this(colour);
        this.character = character;
    }

    /**
     * Get the colour of the column.
     * @return The colour of the column.
     */
    public Colour getColour() {
        return colour;
    }

    /**
     * Gets the character in the column, or null if there is no character.
     * @return The character in the column, or null if there is no character.
     */
    public Character getCharacter() {
        return character;
    }

    /**
     * Sets the character in the column, or null if there is no character.
     * @param character The character in the column, or null if there is no character.
     */
    public void setCharacter(Character character) {
        this.character = character;
    }
}
