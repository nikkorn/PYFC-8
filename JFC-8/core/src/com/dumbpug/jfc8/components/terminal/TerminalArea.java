package com.dumbpug.jfc8.components.terminal;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.dumbpug.jfc8.palette.Palette;
import java.util.ArrayList;

/**
 * A text area that supports terminal type functionality.
 */
public class TerminalArea {
    /**
     * The terminal area configuration.
     */
    private TerminalAreaConfiguration configuration = new TerminalAreaConfiguration();
    /**
     * The number of rows and columns.
     */
    private int lineCount, columnCount;
    /**
     * The height of each row.
     */
    private float lineHeight;
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
     * The input line.
     */
    private Line inputLine;
    /**
     * The area text cursor.
     */
    private Cursor cursor = new Cursor();
    /**
     * The line offset between the first line and the first visible line in the text area.
     */
    private int lineOffset = 0;
    /**
     * The column offset between the first column and the first visible column in the text area.
     */
    private int columnOffset = 0;
    /**
     * Whether the next print operation should happen on a new output line.
     */
    private boolean printToNewOutputLine = true;

    /**
     * Creates a new instance of the TerminalArea class.
     * @param x The x position of the text area.
     * @param y The y position of the text area.
     * @param lineHeight The height of each line.
     * @param columnWidth The width of each column.
     * @param lines The height of the area as a total number of lines.
     * @param columns The width of the area as a total number of columns.
     * @param config The additional terminal area configuration.
     */
    public TerminalArea(float x, float y, float lineHeight, float columnWidth, int lines, int columns, TerminalAreaConfiguration config) {
        this.x             = x;
        this.y             = y;
        this.lineHeight    = lineHeight;
        this.columnWidth   = columnWidth;
        this.lineCount     = lines;
        this.columnCount   = columns;
        this.configuration = config;

        // Calculate the actual width/height of the area.
        this.width  = columns * columnWidth;
        this.height = lines * lineHeight;

        // Create the input line.
        this.inputLine = new Line(config.inputPrefix);

        // Add the input line.
        this.lines.add(inputLine);
    }

    /**
     * Try to set the cursor at the specified column position.
     * @param column The column number.
     */
    public void setCursorPosition(int column) {
        // Try to get a valid column position, bounded by the actual number of columns in the target line.
        if (column < 0) {
            column = 0;
        } else if (column > this.inputLine.getColumnCount()) {
            column = this.inputLine.getColumnCount();
        }

        // Set the cursor column number.
        this.cursor.setColumnNumber(column);

        // The cursor may have moved out of the visible portion of the terminal area, get it back in view.
        this.focusCursor();
    }

    /**
     * Attempt to move the cursor.
     * @param movement The cursor movement to make.
     */
    public void moveCursor(CursorMovement movement) {
        switch (movement) {
            case LEFT:
                this.setCursorPosition(this.cursor.getColumnNumber() - 1);
                break;
            case RIGHT:
                this.setCursorPosition(this.cursor.getColumnNumber() + 1);
                break;
            default:
                throw new RuntimeException("unknown cursor movement: " + movement);
        }
    }

    /**
     * Attempt to focus on the cursor.
     */
    public void focusCursor() {
        // Modify column offset so the cursor remains in the area horizontally, excluding the columns taken up by the line number column.
        if (this.cursor.getColumnNumber() < columnOffset) {
            columnOffset = this.cursor.getColumnNumber();
        } else if (this.cursor.getColumnNumber() + 1 > columnOffset + columnCount) {
            columnOffset = (this.cursor.getColumnNumber() + 1) - columnCount;
        }
    }

    /**
     * Print the text to the terminal output with the following print starting from the same line.
     * @param text The text to print.
     */
    public void print(String text) {
        // Print the text to the terminal output.
        this.printOutputText(text);

        // The next print should continue from the current line.
        printToNewOutputLine = false;
    }

    /**
     * Print the text to the terminal output with the following print starting from a new line.
     * @param text The text to print.
     */
    public void printLine(String text) {
        // Print the text to the terminal output.
        this.printOutputText(text);

        // The next print should not continue from the current line and should start on a new one.
        printToNewOutputLine = true;
    }

    /**
     * Sets the text in the input line.
     * @param text The text to set as the text value for the input line.
     */
    public void setText(String text) {
        // Clear the input line.
        this.inputLine.clear();

        // Reset the cursor position.
        this.cursor.setColumnNumber(0);

        // Insert the text.
        this.insertText(text);
    }

    /**
     * Inserts the text at the cursor position.
     * Any text selection will be cleared before the text is inserted.
     * @param text The text insert at the cursor position.
     */
    public void insertText(String text) {
        // Insert the text at the cursor position, one character at a time.
        for (int characterIndex = 0; characterIndex < text.length(); characterIndex++){
            // Get the next character.
            char character = text.charAt(characterIndex);

            // Add the character to the current line (the one targeted by the cursor)
            this.inputLine.addCharacter(character, this.cursor.getColumnNumber());

            // The cursor column position will have to be moved to the right to account for the added column.
            this.moveCursor(CursorMovement.RIGHT);
        }
    }

    /**
     * Carry out a backspace operation.
     */
    public void backspace() {
        if (this.cursor.getColumnNumber() > 0) {
            // Get rid of the column before the cursor.
            this.inputLine.removeCharacter(this.cursor.getColumnNumber() - 1);

            // Update the cursor position to account for the removed column.
            this.moveCursor(CursorMovement.LEFT);
        }
    }

    public void delete() {
        // TODO If not on last document character then move forward 1 and do a backspace.
    }

    /**
     * Draw the text area.
     * @param batch The sprite batch.
     * @param shapeRenderer The shape renderer.
     * @param font The font to use in drawing the text within the text area.
     */
    public void draw(SpriteBatch batch, ShapeRenderer shapeRenderer, BitmapFont font) {
        // As we are about to use the shape renderer content we should end our batch render temporarily.
        batch.end();

        // Draw the cursor.
        this.fillColumn(
                this.lines.indexOf(this.inputLine) - lineOffset,
                (this.cursor.getColumnNumber() - columnOffset),
                Palette.getColour(this.configuration.cursorColour),
                shapeRenderer
        );

        // Resume our batch render.
        batch.begin();

        // Draw the text, including the line numbers if applicable.
        this.drawText(batch, font);
    }

    /**
     * Draw the text.
     * @param batch The sprite batch.
     * @param font The font to use in drawing the text within the text area.
     */
    private void drawText(SpriteBatch batch, BitmapFont font) {
        // Draw the actual text.
        for (int lineIndex = lineOffset + lineCount - 1; lineIndex >= lineOffset; lineIndex--) {
            // There is nothing to do if the current line index exceeds the actual number of lines in the area.
            if (lineIndex >= this.lines.size()) {
                continue;
            }

            // Get the line at the current line index.
            Line line = this.lines.get(lineIndex);

            for (int columnIndex = columnOffset; columnIndex < (columnOffset + columnCount); columnIndex++) {
                // There is nothing to do if the current column index exceeds the actual number of columns in the current line.
                if (columnIndex >= line.getColumnCount()) {
                    break;
                }

                // Get the character at the current line/column location.
                Character character = line.getCharacter(columnIndex);

                // There is nothing to do if there is no character.
                if (character == null) {
                    continue;
                }

                // Create a glyph layout so we can get the actual size of the character we are about to draw.
                GlyphLayout glyphLayout = new GlyphLayout();
                glyphLayout.setText(font, String.valueOf(character));

                float columnX = x + columnWidth + ((columnIndex - columnOffset) * columnWidth) + (columnWidth / 2 - glyphLayout.width / 2);
                float columnY = y + ((lineCount - (lineIndex - lineOffset)) * lineHeight) - (lineHeight / 2 - glyphLayout.height / 2);

                // Draw the character!
                font.draw(batch, String.valueOf(character), columnX, columnY);
            }
        }
    }

    /**
     * Fill a single column on a line with a colour.
     * @param shapeRenderer The shape renderer.
     */
    private void fillColumn(int line, int column, Color colour, ShapeRenderer shapeRenderer) {
        float columnX = x + (column * columnWidth);
        float columnY = (y + height) - ((line + 1) * lineHeight);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(colour);
        shapeRenderer.rect(columnX, columnY, columnWidth, lineHeight);
        shapeRenderer.end();
    }

    /**
     * Prints the text to the terminal output.
     * @param text The text to print to the terminal output.
     */
    private void printOutputText(String text) {
        if (this.printToNewOutputLine) {
            this.lines.add(this.lines.size() - 1, new Line());
        }

        // Get the last output line to print to.
        Line outputLine = this.getLastOutputLine();

        // Insert the text at the cursor position, one character at a time.
        for (int characterIndex = 0; characterIndex < text.length(); characterIndex++){
            // Get the next character.
            char character = text.charAt(characterIndex);

            // Are we moving on to a new output line?
            if (character == '\n' || character == '\r') {
                // Add a new output line.
                this.lines.add(this.lines.size() - 1, new Line());

                // Get the last output line to print to.
                outputLine = this.getLastOutputLine();
            } else {
                // Add the character to the end of the current output line.
                outputLine.addCharacter(character);
            }
        }
    }

    /**
     * Gets the last output line.
     * @return The last output line.
     */
    private Line getLastOutputLine() {
        if (this.lines.size() < 2) {
            throw new RuntimeException("no output lines found");
        }

        return this.lines.get(this.lines.size() - 2);
    }
}
