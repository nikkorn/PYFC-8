package com.dumbpug.sfc.components.textarea;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.dumbpug.sfc.components.textarea.history.Snapshot;
import com.dumbpug.sfc.components.textarea.history.UndoStack;
import com.dumbpug.sfc.components.textarea.history.UndoableTextAreaUpdate;
import com.dumbpug.sfc.palette.Palette;
import com.dumbpug.sfc.Constants;
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
     * The text area operation undo stack.
     */
    private UndoStack<UndoableTextAreaUpdate> undoStack = new UndoStack<UndoableTextAreaUpdate>(Constants.SCRIPT_EDITOR_MAX_HISTORY_SNAPSHOTS);

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
     * @param text The initial editor text.
     * @param x The x position of the text area.
     * @param y The y position of the text area.
     * @param lineHeight The height of each line.
     * @param columnWidth The width of each column.
     * @param lines The height of the area as a total number of lines.
     * @param columns The width of the area as a total number of columns.
     * @param config The additional text area configuration.
     */
    public TextArea(String text, float x, float y, float lineHeight, float columnWidth, int lines, int columns, TextAreaConfiguration config) {
        this(x, y, lineHeight,columnWidth, lines, columns);
        this.configuration = config;
        this.clearAndSetText(text);
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
     * Gets the text editor input processor.
     * @return The text editor input processor.
     */
    public TextAreaInputProcessor getInputProcessor() {
        return inputProcessor;
    }

    /**
     * Gets the text area text.
     * @return The text area text.
     */
    public String getText() {
        return this.text;
    }

    /**
     * Gets the selected text.
     */
    private String getSelectedText() {
        // There is nothing to do if there is no text selection origin.
        if (this.cursor.getSelectionOrigin() == null) {
            return "";
        }

        // TODO
        return "";
    }

    /**
     * Sets the text area text and resets the cursor position in an undo-able way.
     * @param text The text to set as the text area value.
     */
    public void setText(String text) {
        // Get the pre-operation snapshot.
        Snapshot preOperationSnapshot = this.createSnapshot();

        // Set the text area text.
        this.clearAndSetText(text);

        // Get the post-operation snapshot.
        Snapshot postOperationSnapshot = this.createSnapshot();

        // Add the undoable version of the 'set text' operation on the undo stack.
        this.undoStack.add(new UndoableTextAreaUpdate(this, preOperationSnapshot, postOperationSnapshot));
    }

    /**
     * Inserts the text at the cursor position in an undo-able way.
     * Any text selection will be cleared before the text is inserted.
     * @param text The text insert at the cursor position.
     */
    public void insertText(String text) {
        // Get the pre-operation snapshot.
        Snapshot preOperationSnapshot = this.createSnapshot();

        // Insert the text.
        this.insertTextAtCursorPosition(text);

        // Get the post-operation snapshot.
        Snapshot postOperationSnapshot = this.createSnapshot();

        // Add the undoable version of the 'insert text' operation on the undo stack.
        this.undoStack.add(new UndoableTextAreaUpdate(this, preOperationSnapshot, postOperationSnapshot));
    }

    /**
     * Attempt to move the cursor.
     * @param movement The cursor movement to make.
     */
    public void moveCursor(com.dumbpug.sfc.components.textarea.CursorMovement movement) {
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
                // Move to end of previous line if there is one and the cursor is at the start of the current line.
                if (this.cursor.getColumnNumber() == 0 && this.cursor.getLineNumber() > 0) {
                    this.setCursorPosition(this.cursor.getLineNumber() - 1, Integer.MAX_VALUE);
                } else {
                    this.setCursorPosition(this.cursor.getLineNumber(), this.cursor.getColumnNumber() - 1);
                }
                break;
            case RIGHT:
                // Get the current line.
                Line currentLine = this.lines.get(this.cursor.getLineNumber());

                // Move to start of the next line if there is one and the cursor is at the end of the current line.
                if (this.cursor.getColumnNumber() == currentLine.getColumnCount() && this.cursor.getLineNumber() < this.lines.size() - 1) {
                    this.setCursorPosition(this.cursor.getLineNumber() + 1, 0);
                } else {
                    this.setCursorPosition(this.cursor.getLineNumber(), this.cursor.getColumnNumber() + 1);
                }
                break;
            default:
                throw new RuntimeException("unknown cursor movement: " + movement);
        }
    }

    /**
     * Carry out an undo-able backspace operation.
     */
    public void backspace() {
        // Get the pre-operation snapshot.
        Snapshot preOperationSnapshot = this.createSnapshot();

        // If there is a text selection then we should just delete the selection.
        if (this.cursor.getSelectionOrigin() != null) {
            this.delete();
        } else {
            // We cannot do a backspace if we are already at line 0 / column 0.
            if (this.cursor.isAt(0,0)) {
                return;
            }

            // Move the cursor to the left once so that the character to be removed is actually the one to the left.
            this.moveCursor(CursorMovement.LEFT);

            // We are just clearing a single column.
            this.clearText(
                    new Position(this.cursor.getLineNumber(), this.cursor.getColumnNumber()),
                    new Position(this.cursor.getLineNumber(), this.cursor.getColumnNumber())
            );
        }

        // Get the post-operation snapshot.
        Snapshot postOperationSnapshot = this.createSnapshot();

        // Add the undoable version of the 'backspace' operation on the undo stack.
        this.undoStack.add(new UndoableTextAreaUpdate(this, preOperationSnapshot, postOperationSnapshot));
    }

    /**
     * Carry out an undo-able delete operation.
     */
    public void delete() {
        // Get the pre-operation snapshot.
        Snapshot preOperationSnapshot = this.createSnapshot();

        // If there is no text selection then a delete will remove the character IN FRONT of the cursor.
        if (this.cursor.getSelectionOrigin() == null) {
            // We are just clearing a single column.
            this.clearText(
                    new Position(this.cursor.getLineNumber(), this.cursor.getColumnNumber()),
                    new Position(this.cursor.getLineNumber(), this.cursor.getColumnNumber())
            );
        } else {
            // We are just clearing all selected text.
            this.clearText(
                    new Position(this.cursor.getSelectionOrigin().getLine(), this.cursor.getSelectionOrigin().getColumn()),
                    new Position(this.cursor.getLineNumber(), this.cursor.getColumnNumber())
            );

            // There is no more selection.
            this.cursor.setSelectionOrigin(null);
        }

        // Get the post-operation snapshot.
        Snapshot postOperationSnapshot = this.createSnapshot();

        // Add the undoable version of the 'delete' operation on the undo stack.
        this.undoStack.add(new UndoableTextAreaUpdate(this, preOperationSnapshot, postOperationSnapshot));
    }

    /**
     * Attempt to undo the last undo-able operation if there is one.
     */
    public void undo() {
        if (this.undoStack.canUndo()) {
            this.undoStack.undo();
        }
    }

    /**
     * Attempt to redo the last undo-able operation if there is one.
     */
    public void redo() {
        if (this.undoStack.canRedo()) {
            this.undoStack.redo();
        }
    }

    /**
     * Apply a snapshot representing text area state to this text area.
     * @param snapshot The snapshot to apply.
     */
    public void applySnapshot(Snapshot snapshot) {
        // Set the text area text in a non-undoable way.
        this.clearAndSetText(snapshot.getText());

        // Set the cursor position.
        this.setCursorPosition(snapshot.getCursorPosition().getLine(), snapshot.getCursorPosition().getColumn());

        // Set the cursor selection anchor.
        if (snapshot.getSelectionAnchorPosition() == null) {
            this.cursor.setSelectionOrigin(null);
        } else {
            this.cursor.setSelectionOrigin(new SelectionOrigin(snapshot.getSelectionAnchorPosition().getLine(), snapshot.getSelectionAnchorPosition().getColumn()));
        }

        // Set the line/column offset.
        this.lineOffset   = snapshot.getLineOffset();
        this.columnOffset = snapshot.getColumnOffset();
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
        int relativeX  = pointerX - (int) (this.getX() + this.getLineNumberColumnWidth() * columnWidth);
        int relativeY  = (int) this.height - (int) (pointerY - this.getY());

        // Get the line/column that the pointer is over.
        int targetLine   = lineOffset + (int) (relativeY / lineHeight);
        int targetColumn = columnOffset + (int) (relativeX / columnWidth);

        // Set the cursor position to match the pointer location.
        this.setCursorPosition(targetLine, targetColumn);

        // The pointer going down on a line/column marks the start of a text selection.
        this.cursor.setSelectionOrigin(new SelectionOrigin(this.cursor.getLineNumber(), this.cursor.getColumnNumber()));

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
        // If we have stopped dragging/clicking our pointer and the selection anchor matches the cursor position then just clear it.
        if (this.cursor.getSelectionOrigin() != null &&
                this.cursor.getSelectionOrigin().getLine() == this.cursor.getLineNumber() &&
                this.cursor.getSelectionOrigin().getColumn() == this.cursor.getColumnNumber()) {
            this.cursor.setSelectionOrigin(null);
        }

        return true;
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

        // Update the shape renderer projection matrix to match the application batch one.
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());

        // Draw the selection highlighting if there is actually a selection.
        if (this.cursor.getSelectionOrigin() != null) {
            // Get the positions of the cursor and selection origin.
            Position selectionOriginPosition = new Position(this.cursor.getSelectionOrigin().getLine(), this.cursor.getSelectionOrigin().getColumn());
            Position cursorPosition          = new Position(this.cursor.getLineNumber(), this.cursor.getColumnNumber());
            Range selectionRange             = new Range(selectionOriginPosition, cursorPosition);

            // Iterate over every line/column in the selection and highlight each position.
            for (int lineNumber = selectionRange.getMin().getLine(); lineNumber <= selectionRange.getMax().getLine(); lineNumber++) {
                // We are finished if the current line does not exist.
                if (lineNumber >= this.lines.size()) {
                    break;
                }

                // Get the current line.
                Line line = this.lines.get(lineNumber);

                for (int columnIndex = columnOffset; columnIndex < (columnOffset + columnCount) - this.getLineNumberColumnWidth(); columnIndex++) {
                    // There is nothing to do if the current column index exceeds the actual number of columns in the current line.
                    if (columnIndex > line.getColumnCount()) {
                        break;
                    }

                    // There is nothing to do if the current position is not in the selection range.
                    if ((lineNumber == selectionRange.getMin().getLine() && columnIndex < selectionRange.getMin().getColumn()) ||
                            (lineNumber == selectionRange.getMax().getLine() && columnIndex > selectionRange.getMax().getColumn())) {
                        continue;
                    }

                    // Draw the selection column.
                    this.fillColumn(
                            lineNumber - lineOffset,
                            (columnIndex - columnOffset) + this.getLineNumberColumnWidth(),
                            Palette.getColour(this.configuration.selectionColour),
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
     * Try to set the cursor at the specified line and column position.
     * @param line The line number.
     * @param column The column number.
     */
    private void setCursorPosition(int line, int column) {
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
     * Inserts the text at the cursor position.
     * Any text selection will be cleared before the text is inserted.
     * @param text The text insert at the cursor position.
     */
    private void insertTextAtCursorPosition(String text) {
        // Remove the currently selected text from the text area if there is any.
        if(this.cursor.getSelectionOrigin() != null) {
            // Remove the currently selected text from the text area.
            this.delete();
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
     * Clears and sets the text area text and resets the cursor position.
     * @param text The text to set as the text area value.
     */
    private void clearAndSetText(String text) {
        // Clear the lines in the text area.
        this.lines.clear();

        // Add the initial empty line.
        lines.add(new Line());

        // Reset the cursor position.
        this.cursor.reset();

        // Insert the text.
        this.insertTextAtCursorPosition(text);
    }

    /**
     * Creates a snapshot of the current text area state.
     * @return A snapshot of the current text area state.
     */
    private Snapshot createSnapshot() {
        // Grab the current cursor position.
        Position cursorPosition = new Position(this.cursor.getLineNumber(), this.cursor.getColumnNumber());

        // Grab the selection anchor position, if it exists.
        Position selectionAnchorPosition = null;
        if (this.cursor.getSelectionOrigin() != null) {
            selectionAnchorPosition = new Position(this.cursor.getSelectionOrigin().getLine(), this.cursor.getSelectionOrigin().getColumn());
        }

        // Create and return the snapshot.
        return new Snapshot(this.getText(), cursorPosition, selectionAnchorPosition, this.lineOffset, this.columnOffset);
    }

    /**
     * Attempt to focus on the cursor.
     */
    private void focusCursor() {
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
     * Clear all text between a start and end position.
     * @param origin The origin position.
     * @param target The target position.
     */
    private void clearText(Position origin, Position target) {
        // Get the range between the start/end position.
        Range clearRange = new Range(origin, target);

        // Set the cursor at the correct position.
        this.cursor.setLineNumber(clearRange.getMax().getLine());
        this.cursor.setColumnNumber(clearRange.getMax().getColumn());

        // Move the cursor right once, as column removal takes place to the left of the cursor.
        this.moveCursor(CursorMovement.RIGHT);

        // We will be removing a column to the left of the cursor until we reach the first position in the selection.
        while (!this.cursor.isAt(clearRange.getMin().getLine(), clearRange.getMin().getColumn())) {
            // Get the line targeted by the cursor.
            Line targetLine = this.lines.get(this.cursor.getLineNumber());

            // Check whether the cursor is at the very start of a line, if so then we are going to the previous line.
            if (this.cursor.getColumnNumber() == 0) {
                // If this is the first line then we cannot backspace onto another line.
                if (this.cursor.getLineNumber() == 0) {
                    break;
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
            } else {
                // Get rid of the column before the cursor.
                targetLine.removeCharacter(this.cursor.getColumnNumber() - 1);

                // Update the cursor position to account for the removed column.
                this.moveCursor(CursorMovement.LEFT);
            }
        }

        // Focus on the cursor.
        this.focusCursor();

        // Update the text area text.
        this.updateTextAreaText();
    }

    /**
     * Fill a single column on a line with a colour.
     * @param line The line number.
     * @param column The column number.
     * @param colour The colour to use.
     * @param shapeRenderer The shape renderer.
     */
    private void fillColumn(int line, int column, Color colour, ShapeRenderer shapeRenderer) {
        float columnX = x + (column * columnWidth);
        float columnY = (y + height) - ((line + 1) * lineHeight);

        // TODO Return early if column x,y is not in viewable area.

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
