package com.dumbpug.jfc8.components.textarea.history;

import com.dumbpug.jfc8.components.textarea.Position;

/**
 * Represents a snapshot of the text area state.
 */
public class Snapshot {
    /**
     * The area text.
     */
    private String text;
    /**
     * The cursor position.
     */
    private Position cursorPosition;
    /**
     * The selection anchor position, or null if there was no selection.
     */
    private Position selectionAnchorPosition;
    /**
     * The line offset.
     */
    private int lineOffset;
    /**
     * The column offset.
     */
    private int columnOffset;

    /**
     * Creates a new instance of the Snapshot class.
     * @param text The text area text.
     * @param cursor The cursor position.
     * @param selectionAnchor The selection anchor position.
     * @param lineOffset The line offset.
     * @param columnOffset The column offset.
     */
    public Snapshot(String text, Position cursor, Position selectionAnchor, int lineOffset, int columnOffset) {
        this.text                    = text;
        this.cursorPosition          = cursor;
        this.selectionAnchorPosition = selectionAnchor;
        this.lineOffset              = lineOffset;
        this.columnOffset            = columnOffset;
    }

    /**
     * Gets the area text.
     * @return The area text.
     */
    public String getText() {
        return text;
    }

    /**
     * Gets the cursor position.
     * @return The cursor position.
     */
    public Position getCursorPosition() {
        return cursorPosition;
    }

    /**
     * Gets the selection anchor position, or null if there was no selection.
     * @return The selection anchor position, or null if there was no selection.
     */
    public Position getSelectionAnchorPosition() {
        return selectionAnchorPosition;
    }

    /**
     * Gets the line offset.
     * @return The line offset.
     */
    public int getLineOffset() {
        return lineOffset;
    }

    /**
     * Gets the column offset.
     * @return The column offset.
     */
    public int getColumnOffset() {
        return columnOffset;
    }
}
