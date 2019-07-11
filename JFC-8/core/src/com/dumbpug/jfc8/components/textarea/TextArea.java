package com.dumbpug.jfc8.components.textarea;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;
import com.dumbpug.jfc8.palette.Colour;
import com.dumbpug.jfc8.palette.Palette;

import java.util.ArrayList;

/**
 * A text area that supports terminal and code editor type functionality.
 */
public class TextArea {
    /**
     * The text area configuration.
     */
    private TextAreaConfiguration configuration = new TextAreaConfiguration();
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
     * Creates a new instance of the TextArea class.
     * @param x The x position of the text area.
     * @param y The y position of the text area.
     * @param lineHeight The height of each line.
     * @param columnWidth The width of each column.
     * @param lines The height of the area as a total number of lines.
     * @param columns The width of the area as a total number of columns.
     */
    public TextArea(float x, float y, float lineHeight, float columnWidth, int lines, int columns) {
        this.x           = x;
        this.y           = y;
        this.lineHeight  = lineHeight;
        this.columnWidth = columnWidth;
        this.lineCount   = lines;
        this.columnCount = columns;

        // Calculate the actual width/height of the area.
        this.width  = columns * columnWidth;
        this.height = lines * lineHeight;

        // Add the initial empty line.
        this.lines.add(new Line());
    }

    /**
     * Creates a new instance of the TextArea class.
     * @param x The x position of the text area.
     * @param y The y position of the text area.
     * @param lineHeight The height of each line.
     * @param columnWidth The width of each column.
     * @param lines The height of the area as a total number of lines.
     * @param columns The width of the area as a total number of columns.
     * @param config The additional text area configuration.
     */
    public TextArea(float x, float y, float lineHeight, float columnWidth, int lines, int columns, TextAreaConfiguration config) {
        this(x, y, lineHeight,columnWidth, lines, columns);
        this.configuration = config;
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
        } else if (targetColumnNumber > targetLine.getColumnCount()) {
            targetColumnNumber = targetLine.getColumnCount();
        }

        // Set the cursor column number.
        this.cursor.setColumnNumber(targetColumnNumber);

        // The cursor may have moved out of the visible portion of the text area, get it back in view.
        this.focusCursor();
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
     * Attempt to focus on the cursor.
     */
    public void focusCursor() {
        // Modify line offset so that the cursor stays in area vertically.
        if (this.cursor.getLineNumber() < lineOffset) {
            lineOffset = this.cursor.getLineNumber();
        } else if (this.cursor.getLineNumber() + 1 > lineOffset + lineCount) {
            lineOffset = (this.cursor.getLineNumber() + 1) - lineCount;
        }

        // Modify column offset so cursor is in area.
        if (this.cursor.getColumnNumber() < columnOffset) {
            columnOffset = this.cursor.getColumnNumber();
        } else if (this.cursor.getColumnNumber() + 1 > columnOffset + columnCount) {
            columnOffset = (this.cursor.getColumnNumber() + 1) - columnCount;
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

        // Reset the cursor position.
        this.cursor.reset();

        // Insert the text.
        this.insertText(text);
    }

    /**
     * Gets the text area text.
     * @return The text area text.
     */
    public String getText() {
        String value = "";

        for (Line line : this.lines) {
            value += (line.getText() + "\n");
        }

        return value;
    }

    /**
     * Inserts the text at the cursor position.
     * Any text selection will be cleared before the text is inserted.
     * @param text The text insert at the cursor position.
     */
    public void insertText(String text) {
        // Remove the currently selected text from the text area if there is any.
        if(this.cursor.getSelectionLength() > 0) {
            // TODO Remove the currently selected text from the text area.
        }

        // Insert the text at the cursor position, one character at a time.
        for (int characterIndex = 0; characterIndex < text.length(); characterIndex++){
            // Get the next character.
            char character = text.charAt(characterIndex);

            // Get the line targeted by the cursor.
            Line targetLine = this.lines.get(this.cursor.getLineNumber());

            // Check whether the current character is a new-line character.
            if (character == '\n' || character == '\r') {
                // Gather any columns that follow the cursor on the current line.
                ArrayList<Character> choppedCharacters = targetLine.chop(this.cursor.getColumnNumber());

                // Add a new empty line.
                this.lines.add(this.cursor.getLineNumber() + 1, new Line());

                // Move the cursor to the start of the new line.
                this.moveCursor(CursorMovement.DOWN);

                // Update the current target line.
                targetLine = this.lines.get(this.cursor.getLineNumber());

                // Move the chopped characters to the new line.
                for (Character chopped : choppedCharacters) {
                    // Add the character to the current line (the one targeted by the cursor)
                    targetLine.addCharacter(chopped, this.cursor.getColumnNumber());

                    // The cursor column position will have to be moved to the right to account for the added column.
                    this.moveCursor(CursorMovement.RIGHT);
                }

                // We need to reset the cursor column position to the start of the new line.
                this.setCursorPosition(this.cursor.getLineNumber(),0);
            } else {
                // Add the character to the current line (the one targeted by the cursor)
                targetLine.addCharacter(character, this.cursor.getColumnNumber());

                // The cursor column position will have to be moved to the right to account for the added column.
                this.moveCursor(CursorMovement.RIGHT);
            }
        }
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

        // Draw the text area background if one is defined.
        if (this.configuration.backgroundColour == null /* TODO Replace with Colour.NOT_SET */) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Palette.getColour(this.configuration.backgroundColour)); // TODO This should eventually map to the colour defined in the config.
            shapeRenderer.rect(x, y, width, height);
            shapeRenderer.end();
        }

        // TODO Draw the line number bar background.

        // TODO Draw the selection.

        // Draw the cursor.
        this.fillColumn(this.cursor.getLineNumber() - lineOffset, this.cursor.getColumnNumber() - columnOffset, Palette.getColour(this.configuration.cursorColour), shapeRenderer);

        // TODO Draw the line numbers.

        // Resume our batch render.
        batch.begin();

        // Draw the text.
        this.drawText(batch, font);
    }

    /**
     * Draw the text.
     * @param batch The sprite batch.
     * @param font The font to use in drawing the text within the text area.
     */
    private void drawText(SpriteBatch batch, BitmapFont font) {
        for (int lineIndex = lineOffset + lineCount - 1; lineIndex >= lineOffset; lineIndex--) {
            // There is nothing to do if the current line index exceeds the actual number of lines in the area.
            if (lineIndex >= this.lines.size()) {
                continue;
            }

            // Get the line at the current line index.
            Line line = this.lines.get(lineIndex);

            for (int columnIndex = columnOffset; columnIndex < columnOffset + columnCount; columnIndex++) {
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

                float columnX = x + ((columnIndex - columnOffset) * columnWidth) + (columnWidth / 2 - glyphLayout.width / 2);
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
        float columnX =  x + (column * columnWidth); // TODO Account for columnOffset.
        float columnY = (y + height) - ((line + 1) * lineHeight); // TODO Account for columnOffset.

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(colour);
        shapeRenderer.rect(columnX, columnY, columnWidth, lineHeight);
        shapeRenderer.end();
    }
}
