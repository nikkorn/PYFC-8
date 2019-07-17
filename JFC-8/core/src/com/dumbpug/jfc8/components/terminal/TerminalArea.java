package com.dumbpug.jfc8.components.terminal;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.dumbpug.jfc8.palette.Colour;
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
        // Modify line offset so that the cursor stays in area vertically.
        if ((this.lines.size() - 1) < lineOffset) {
            lineOffset = (this.lines.size() - 1);
        } else if (this.lines.size() > lineOffset + lineCount) {
            lineOffset = this.lines.size() - lineCount;
        }

        // Modify column offset so the cursor remains in the area horizontally, excluding the columns taken up by the line number column.
        if (this.cursor.getColumnNumber() < columnOffset) {
            columnOffset = this.cursor.getColumnNumber();
        } else if (this.cursor.getColumnNumber() + 1 > columnOffset + columnCount - this.inputLine.getPrefix().length()) {
            columnOffset = (this.cursor.getColumnNumber() + this.inputLine.getPrefix().length()) - columnCount;
        }
    }

    /**
     * Clear the terminal area.
     */
    public void clear() {
        // Get rid of all terminal lines.
        this.lines.clear();

        // Re-add the input line.
        this.lines.add(this.inputLine);

        // Reset the line offset.
        this.lineOffset = 0;
    }

    /**
     * Print the text to the terminal output with the following print starting from the same line.
     * @param text The text to print.
     * @param colour The text colour.
     */
    public void print(String text, Colour colour) {
        // Print the text to the terminal output.
        this.printOutputText(text, colour);

        // The next print should continue from the current line.
        printToNewOutputLine = false;
    }

    /**
     * Print the text to the terminal output with the following print starting from a new line.
     * @param text The text to print.
     * @param colour The text colour.
     */
    public void printLine(String text, Colour colour) {
        // Print the text to the terminal output.
        this.printOutputText(text, colour);

        // The next print should not continue from the current line and should start on a new one.
        printToNewOutputLine = true;
    }

    /**
     * Print the text to the terminal output with the following print starting from the same line.
     * @param text The text to print.
     */
    public void print(String text) {
        this.print(text, this.configuration.fontColour);
    }

    /**
     * Print the text to the terminal output with the following print starting from a new line.
     * @param text The text to print.
     */
    public void printLine(String text) {
        this.printLine(text, this.configuration.fontColour);
    }

    /**
     * Sets the text in the input line.
     * @param text The text to set as the text value for the input line.
     */
    public void setInput(String text) {
        // Clear the input line.
        this.inputLine.clear();

        // Reset the cursor position.
        this.cursor.setColumnNumber(0);

        // Insert the text.
        this.insertText(text);

        // Focus on the cursor
        focusCursor();
    }

    /**
     * Gets the text in the input line.
     * @return The text in the input line.
     */
    public String getInput() {
        return this.inputLine.getText();
    }

    /**
     * Inserts the text at the cursor position.
     * Any text selection will be cleared before the text is inserted.
     * @param text The text insert at the cursor position.
     * @param colour The colour of the text.
     */
    public void insertText(String text, Colour colour) {
        // Insert the text at the cursor position, one character at a time.
        for (int characterIndex = 0; characterIndex < text.length(); characterIndex++){
            // Get the next character.
            char character = text.charAt(characterIndex);

            // Add the character to the current line (the one targeted by the cursor)
            this.inputLine.addCharacter(character, colour, this.cursor.getColumnNumber());

            // The cursor column position will have to be moved to the right to account for the added column.
            this.moveCursor(CursorMovement.RIGHT);
        }
    }

    /**
     * Inserts the text at the cursor position using the default font colour.
     * Any text selection will be cleared before the text is inserted.
     * @param text The text insert at the cursor position.
     */
    public void insertText(String text) {
        this.insertText(text, this.configuration.fontColour);
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
                (this.cursor.getColumnNumber() - columnOffset) + this.inputLine.getPrefix().length(),
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
        // Always reset the font colour back to the editor default.
        font.setColor(Palette.getColour(this.configuration.fontColour));

        // Draw the actual text.
        for (int lineIndex = lineOffset + lineCount - 1; lineIndex >= lineOffset; lineIndex--) {
            // There is nothing to do if the current line index exceeds the actual number of lines in the area.
            if (lineIndex >= this.lines.size()) {
                continue;
            }

            // Get the line at the current line index.
            Line line = this.lines.get(lineIndex);

            // Get the columns that make up the current line.
            ArrayList<Column> columns = line.getColumns();

            // Draw each column character withing the terminal area.
            for (int columnIndex = columnOffset; columnIndex < (columnOffset + columnCount); columnIndex++) {
                // There is nothing to do if the current column index exceeds the actual number of columns in the current line.
                if (columnIndex >= columns.size()) {
                    break;
                }

                // Get the column at the current line/column location.
                Column column = columns.get(columnIndex);

                // There is nothing to do if there is no character.
                if (column.getCharacter() == null) {
                    continue;
                }

                // Create a glyph layout so we can get the actual size of the character we are about to draw.
                GlyphLayout glyphLayout = new GlyphLayout();
                glyphLayout.setText(font, String.valueOf(column.getCharacter()));

                float columnX = x + ((columnIndex - columnOffset) * columnWidth) + (columnWidth / 2 - glyphLayout.width / 2);
                float columnY = y + ((lineCount - (lineIndex - lineOffset)) * lineHeight) - (lineHeight / 2 - glyphLayout.height / 2);

                // Set the font colour to match the column colour if there is one.
                if (column.getColour() != null) {
                    font.setColor(Palette.getColour(column.getColour()));
                }

                // Draw the character!
                font.draw(batch, String.valueOf(column.getCharacter()), columnX, columnY);
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
     * @param colour The text colour.
     */
    private void printOutputText(String text, Colour colour) {
        if (this.printToNewOutputLine || this.lines.size() == 1) {
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
                outputLine.addCharacter(character, colour);
            }
        }

        // Focus on the cursor
        focusCursor();
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
