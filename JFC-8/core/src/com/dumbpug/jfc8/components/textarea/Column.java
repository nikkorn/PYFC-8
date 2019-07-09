package com.dumbpug.jfc8.components.textarea;

/**
 * Represents a single column that can be populated with a character in a line.
 */
public class Column {
    /**
     * The character in the column, or null if there is no character.
     */
    private Character character = null;

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
