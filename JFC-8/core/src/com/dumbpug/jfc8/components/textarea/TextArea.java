package com.dumbpug.jfc8.components.textarea;

import java.util.ArrayList;

/**
 * A text area that supports terminal and code editor type functionality.
 */
public class TextArea {
    /**
     * The number of rows and columns.
     */
    private int rowCount, columnCount;
    /**
     * The height of each row.
     */
    private float rowHeight;
    /**
     * The width of each column.
     */
    private float columnWidth;
    /**
     * The x/y position of the text area.
     */
    private float x, y;
    /**
     * The width/height of the text area.
     */
    private float width, height;
    /**
     * The lines that the text area is populated with.
     */
    private ArrayList<Line> lines = new ArrayList<Line>();
    /**
     * The area text cursor.
     */
    private Cursor cursor = new Cursor();

    /**
     * Creates a new instance of the TextArea class.
     * @param x The x position of the text area.
     * @param y The y position of the text area.
     * @param rowHeight The height of each row.
     * @param columnWidth The width of each column.
     * @param rows The height of the area as a total number of rows.
     * @param columns The width of the area as a total number of columns.
     */
    public TextArea(float x, float y, float rowHeight, float columnWidth, int rows, int columns) {
        this.x           = x;
        this.y           = y;
        this.rowHeight   = rowHeight;
        this.columnWidth = columnWidth;
        this.rowCount    = rows;
        this.columnCount = columns;

        // Calculate the actual width/height of the area.
        this.width       = columns * columnWidth;
        this.height      = rows * rowHeight;

        // Add the initial empty line.
        lines.add(new Line());
    }

    /**
     * Try to set the cursor at the specified line and column position.
     * @param line The line number.
     * @param column The column number.
     */
    public void setCursorPosition(int line, int column) {
        // The target line.
        Line targetLine = null;

        // Try to get the line at the line position 'line', bounded by the number of actual rows.
        if (line < 0) {
            targetLine = this.lines.get(0);
        } else if (line >= this.lines.size()) {
            targetLine = this.lines.get(this.lines.size() - 1);
        } else {
            targetLine = this.lines.get(line);
        }

        // Set the cursor line number.
        this.cursor.setLineNumber(this.lines.indexOf(targetLine));

        int targetColumnNumber = column;

        // Try to get a valid column position, bounded by the actual number of columns in the target line.
        if (targetColumnNumber < 0) {
            targetColumnNumber = 0;
        } else if (targetColumnNumber >= targetLine.getColumnCount()) {
            targetColumnNumber = targetLine.getColumnCount() - 1;
        }

        // Set the cursor column number.
        this.cursor.setColumnNumber(targetColumnNumber);
    }

    /**
     * Attempt to move the cursor.
     * @param movement The cursor movement to make.
     */
    public void moveCursor(CursorMovement movement) {
        // Attempting to move the cursor in any direction will cause the cursor selection to be thrown away.
        this.cursor.setSelectionLength(0);

        switch (movement) {
            case UP:
                this.setCursorPosition(this.cursor.getLineNumber() - 1, this.cursor.getColumnNumber());
                break;
            case DOWN:
                this.setCursorPosition(this.cursor.getLineNumber() + 1, this.cursor.getColumnNumber());
                break;
            case LEFT:
                this.setCursorPosition(this.cursor.getLineNumber(), this.cursor.getColumnNumber() - 1);
                break;
            case RIGHT:
                this.setCursorPosition(this.cursor.getLineNumber(), this.cursor.getColumnNumber() + 1);
                break;
            default:
                throw new RuntimeException("unknown cursor movement: " + movement);
        }
    }

    /**
     * Sets the text area text and resets the cursor position.
     * @param text The text to set as the text area value.
     */
    public void setText(String text) {
        // Clear the lines in the text area.
        this.lines.clear();

        // Add the initial empty line.
        lines.add(new Line());

        // TODO Apply the text, creating a new line whenever a new-line character is found.

        // Reset the cursor position.
        this.cursor.reset();
    }

    /**
     * Inserts the text at the cursor position.
     * Any text selection will be cleared before the text is inserted.
     * @param text The text insert at the cursor position.
     */
    public void insertText(String text) {
        // TODO Remove the currently selected test from the text area.
        // TODO Insert the text a the cursor position.
    }
}
