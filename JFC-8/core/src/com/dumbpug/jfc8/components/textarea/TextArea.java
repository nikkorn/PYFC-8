package com.dumbpug.jfc8.components.textarea;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.dumbpug.jfc8.palette.Colour;
import com.dumbpug.jfc8.palette.Palette;
import java.util.ArrayList;

/**
 * A text area that supports code editor type functionality.
 */
public class TextArea {
    /**
     * The text area configuration.
     */
    private TextAreaConfiguration configuration = new TextAreaConfiguration();
    /**
     * The input processor for this text area.
     */
    private TextAreaInputProcessor inputProcessor = new TextAreaInputProcessor(this);
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
     * The text area text.
     */
    private String text = "";

    /**
     * Creates a new instance of the TerminalArea class.
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
     * Creates a new instance of the TerminalArea class.
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
     * Gets the x position of the text area.
     * @return The x position of the text area.
     */
    public float getX() {
        return x;
    }

    /**
     * Gets the y position of the text area.
     * @return The the y position of the text area.
     */
    public float getY() {
        return y;
    }

    /**
     * Gets the width of the control.
     * @return The width of the control.
     */
    public float getWidth() {
        return width;
    }

    /**
     * Gets the height of the control.
     * @return The height of the control.
     */
    public float getHeight() {
        return height;
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
        return this.text;
    }

    /**
     * Gets the text editor input processor.
     * @return The text editor input processor.
     */
    public TextAreaInputProcessor getInputProcessor() {
        return inputProcessor;
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
        this.cursor.setSelectionOrigin(null);

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

        // Modify column offset so the cursor remains in the area horizontally, excluding the columns taken up by the line number column.
        if (this.cursor.getColumnNumber() < columnOffset) {
            columnOffset = this.cursor.getColumnNumber();
        } else if (this.cursor.getColumnNumber() + 1 > columnOffset + (columnCount - this.getLineNumberColumnWidth())) {
            columnOffset = (this.cursor.getColumnNumber() + 1) - (columnCount - this.getLineNumberColumnWidth());
        }
    }

    /**
     * Inserts the text at the cursor position.
     * Any text selection will be cleared before the text is inserted.
     * @param text The text insert at the cursor position.
     */
    public void insertText(String text) {
        // Remove the currently selected text from the text area if there is any.
        if(this.cursor.getSelectionOrigin() != null) {
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

        // Update the text area text.
        this.updateTextAreaText();
    }

    /**
     * Carry out a backspace operation.
     */
    public void backspace() {
        // TODO Do this per selected column, but at least once.

        // There are two scenarios.
        // 1- cursor is at start of the line. So:
        //         - Chop the remaining.
        //         - delete the current line
        //         - move cursor up a line and to the end of it
        //         - reinsert chopped text.
        // 2- cursor is not at start of the line. So:
        //         - remove previous column from current line
        //         - update the cursor column

        // Get the line targeted by the cursor.
        Line targetLine = this.lines.get(this.cursor.getLineNumber());

        if (this.cursor.getColumnNumber() == 0) {
            // If this is the first line then we cannot backspace onto another line.
            if (this.cursor.getLineNumber() == 0) {
                return;
            }

            // Gather any columns that follow the cursor on the current line.
            ArrayList<Character> choppedCharacters = targetLine.chop(this.cursor.getColumnNumber());

            // Get rid of the current line.
            this.lines.remove(targetLine);

            // Move the cursor super far to the right so that it will be clamped to the end of the previous line.
            this.cursor.setColumnNumber(Integer.MAX_VALUE);

            // Move the cursor up to the previous line.
            this.moveCursor(CursorMovement.UP);

            // Get the line we are now targeting.
            targetLine = this.lines.get(this.cursor.getLineNumber());

            // Get the column number that we need to set the cursor at after moving the chopped text up.
            int finalColumnNumber = this.cursor.getColumnNumber();

            // Move the chopped characters to the current line.
            for (Character chopped : choppedCharacters) {
                // Add the character to the current line (the one targeted by the cursor)
                targetLine.addCharacter(chopped, this.cursor.getColumnNumber());

                // The cursor column position will have to be moved to the right to account for the added column.
                this.moveCursor(CursorMovement.RIGHT);
            }

            // Move the cursor back to the position that was originally the end of the line that we backspaced to.
            this.cursor.setColumnNumber(finalColumnNumber);

            // Focus on the cursor.
            this.focusCursor();
        } else {
            // Get rid of the column before the cursor.
            targetLine.removeCharacter(this.cursor.getColumnNumber() - 1);

            // Update the cursor position to account for the removed column.
            this.moveCursor(CursorMovement.LEFT);
        }

        // Update the text area text.
        this.updateTextAreaText();
    }

    public void delete() {
        // TODO If not on last document character then move forward 1 and do a backspace.
    }

    /**
     * Try to process a pointer down event and return whether it was processed by the text area.
     * @param pointerX The pointer x position.
     * @param pointerY The pointer y position.
     * @return Whether the pointer down event was processed by the text area.
     */
    public boolean processPointerDown(int pointerX, int pointerY) {
        if (!this.isPointerInTextAreaBounds(pointerX, pointerY)) {
            return false;
        }

        // Get the relative x/y pointer position in the text area, excluding the line number column.
        int relativeX    = pointerX - (int) (this.getX() + this.getLineNumberColumnWidth() * columnWidth);
        int relativeY    = (int) this.height - (int) (pointerY - this.getY());

        // Get the line/column that the pointer is over.
        int targetLine   = lineOffset + (int) (relativeY / lineHeight);
        int targetColumn = columnOffset + (int) (relativeX / columnWidth);

        // Set the cursor position to match the pointer location.
        this.setCursorPosition(targetLine, targetColumn);

        // The pointer going down on a line/column marks the start of a text selection.
        this.cursor.setSelectionOrigin(new SelectionOrigin(targetLine, targetColumn));

        return true;
    }

    /**
     * Try to process a pointer drag event and return whether it was processed by the text area.
     * @param pointerX The pointer x position.
     * @param pointerY The pointer y position.
     * @return Whether the pointer down event was processed by the text area.
     */
    public boolean processPointerDrag(int pointerX, int pointerY) {
        if (!this.isPointerInTextAreaBounds(pointerX, pointerY)) {
            return false;
        }

        // Get the relative x/y pointer position in the text area, excluding the line number column.
        int relativeX  = pointerX - (int) (this.getX() + this.getLineNumberColumnWidth() * columnWidth);
        int relativeY  = (int) this.height - (int) (pointerY - this.getY());

        // Get the line/column that the pointer is over.
        int targetLine   = lineOffset + (int) (relativeY / lineHeight);
        int targetColumn = columnOffset + (int) (relativeX / columnWidth);

        // Set the cursor position to match the pointer location.
        this.setCursorPosition(targetLine, targetColumn);

        return true;
    }

    /**
     * Process a pointer up event and return whether it was processed by the text area.
     * @param pointerX The pointer x position.
     * @param pointerY The pointer y position.
     * @return Whether the pointer down event was processed by the text area.
     */
    public boolean processPointerUp(int pointerX, int pointerY) {
        return false;
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

        // TODO Draw the selection if there is one.
        if (this.cursor.getSelectionOrigin() != null) {
            int selectionMinLine   = Math.min(this.cursor.getLineNumber(), this.cursor.getSelectionOrigin().getLine());
            int selectionMaxLine   = Math.max(this.cursor.getLineNumber(), this.cursor.getSelectionOrigin().getLine());
            int selectionMinColumn = Math.min(this.cursor.getColumnNumber(), this.cursor.getSelectionOrigin().getColumn());
            int selectionMaxColumn = Math.max(this.cursor.getColumnNumber(), this.cursor.getSelectionOrigin().getColumn());

            for (int line = selectionMinLine; line <= selectionMaxLine; line++) {
                for (int column = selectionMinColumn; column <= selectionMaxColumn; column++) {
                    // Draw the selection column.
                    this.fillColumn(
                            line - lineOffset,
                            (column - columnOffset) + this.getLineNumberColumnWidth(),
                            Palette.getColour(Colour.NAVY),
                            shapeRenderer
                    );
                }
            }
        }

        // Draw the cursor.
        this.fillColumn(
                this.cursor.getLineNumber() - lineOffset,
                (this.cursor.getColumnNumber() - columnOffset) + this.getLineNumberColumnWidth(),
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
        // Draw the line numbers if we need to.
        if (this.configuration.includeLineNumbers) {
            // Set the line number font colour.
            font.setColor(Palette.getColour(this.configuration.lineNumberFontColour));

            // Draw each line number.
            for (int lineIndex = lineOffset + lineCount - 1; lineIndex >= lineOffset; lineIndex--) {

                String lineNumber = String.valueOf(lineIndex);

                // Create a glyph layout so we can get the actual size of the character we are about to draw.
                GlyphLayout glyphLayout = new GlyphLayout();
                glyphLayout.setText(font, lineNumber);

                float lineNumberY = y + ((lineCount - (lineIndex - lineOffset)) * lineHeight) - (lineHeight / 2 - glyphLayout.height / 2);

                // Draw the number for the line!
                font.draw(batch, lineNumber, x, lineNumberY);
            }

            // Reset the default editor font colour.
            font.setColor(Palette.getColour(this.configuration.fontColour));
        }

        int lineNumberColumnOffset = this.getLineNumberColumnWidth();

        // Draw the actual text.
        for (int lineIndex = lineOffset + lineCount - 1; lineIndex >= lineOffset; lineIndex--) {
            // There is nothing to do if the current line index exceeds the actual number of lines in the area.
            if (lineIndex >= this.lines.size()) {
                continue;
            }

            // Get the line at the current line index.
            Line line = this.lines.get(lineIndex);

            for (int columnIndex = columnOffset; columnIndex < (columnOffset + columnCount) - lineNumberColumnOffset; columnIndex++) {
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

                float columnX = x + (lineNumberColumnOffset * columnWidth) + ((columnIndex - columnOffset) * columnWidth) + (columnWidth / 2 - glyphLayout.width / 2);
                float columnY = y + ((lineCount - (lineIndex - lineOffset)) * lineHeight) - (lineHeight / 2 - glyphLayout.height / 2);

                // Draw the character!
                font.draw(batch, String.valueOf(character), columnX, columnY);
            }
        }
    }

    /**
     * Clear the current text selection if there is any.
     */
    private void clearSelectedText() {
        // There is nothing to do if there is no text selection origin.
        if (this.cursor.getSelectionOrigin() == null) {
            return;
        }

        // TODO Clear text selection.
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
     * Updates the text area text string to reflect the state of the text area.
     */
    private void updateTextAreaText() {
        this.text = "";
        for (int lineIndex = 0; lineIndex< this.lines.size(); lineIndex++) {
            this.text += this.lines.get(lineIndex).getText();

            if (lineIndex != this.lines.size() - 1) {
                this.text += "\n";
            }
        }
    }

    /**
     * Gets the width of the line number column in individual columns.
     * @return The width of the line number column in individual column
     */
    private int getLineNumberColumnWidth() {
        // Are we even showing a line number column?
        if (!this.configuration.includeLineNumbers) {
            return 0;
        }

        return String.valueOf(lineOffset + lineCount - 1).length() + 1;
    }

    /**
     * Gets whether the given x/y pointer position is within the text area bounds.
     * @param screenX The x position of the pointer.
     * @param screenY The y position of the pointer.
     * @return Whether the given x/y pointer position is within the text area bounds.
     */
    private boolean isPointerInTextAreaBounds(int screenX, int screenY) {
        if (screenX < this.getX()) {
            return false;
        }
        if (screenX > (this.getX() + this.getWidth())) {
            return false;
        }
        if (screenY < this.getY()) {
            return false;
        }
        if (screenY > (this.getY() + this.getHeight())) {
            return false;
        }

        return true;
    }
}
