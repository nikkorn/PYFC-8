package com.dumbpug.jfc8.terminal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.dumbpug.jfc8.Constants;
import com.dumbpug.jfc8.components.terminal.CursorMovement;
import com.dumbpug.jfc8.components.terminal.TerminalArea;
import com.dumbpug.jfc8.components.terminal.TerminalAreaConfiguration;
import com.dumbpug.jfc8.font.FontProvider;
import com.dumbpug.jfc8.palette.Colour;
import com.dumbpug.jfc8.palette.Palette;
import com.dumbpug.jfc8.state.State;
import java.util.ArrayList;

/**
 * The terminal state of the application.
 */
public class Terminal extends State implements InputProcessor {
    /**
     * The text area used as the terminal.
     */
    TerminalArea terminalArea;
    /**
     * The shape renderer to use in drawing the editor.
     */
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    /**
     * The terminal font.
     */
    private BitmapFont terminalFont;
    /**
     * The terminal command processor.
     */
    private CommandProcessor commandProcessor = new CommandProcessor();
    /**
     * This string contains text we can't edit (eg previously executed commands/command output).
     */
    String outputGarbage = "RUBBISH";
    /**
     * The previously executed commands.
     */
    ArrayList<String> executed = new ArrayList<String>();

    /**
     * Create a new instance of the Terminal class.
     */
    public Terminal() {
        // Create the terminal font.
        terminalFont = FontProvider.getFont(Constants.SCRIPT_EDITOR_FONT_SIZE * Constants.DISPLAY_PIXEL_SIZE);
        terminalFont.setColor(Palette.getColour(Colour.WHITE));

        // Create the terminal text area.
        terminalArea = new TerminalArea(
                Constants.SCRIPT_EDITOR_MARGIN_SIZE * Constants.DISPLAY_PIXEL_SIZE,
                150, //Constants.SCRIPT_EDITOR_MARGIN_SIZE * Constants.DISPLAY_PIXEL_SIZE,
                (Constants.SCRIPT_EDITOR_FONT_SIZE + Constants.SCRIPT_EDITOR_LINE_MARGIN_SIZE) * Constants.DISPLAY_PIXEL_SIZE,
                (Constants.SCRIPT_EDITOR_FONT_SIZE + Constants.SCRIPT_EDITOR_COLUMN_MARGIN_SIZE) * Constants.DISPLAY_PIXEL_SIZE,
                4, //18,
                46,
                new TerminalAreaConfiguration()
        );

        // Text print output garbage
        terminalArea.printLine(outputGarbage);
    }

    @Override
    public void onEntry(State state) {
        // Set the application input processor to be the one associated with this state.
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void onExit() {
        // Unset the application input processor to be the one associated with this state.
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void update() {
        // Check whether the user is attempting to move state.
        if (Gdx.input.isKeyJustPressed(Input.Keys.F2)) {
            this.changeState("SCRIPT_EDITOR");
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        // Draw the terminal text area.
        terminalArea.draw(batch, shapeRenderer, this.terminalFont);
    }

    @Override
    public boolean keyDown(int keycode) {
        // Process any arrow keys, as this will impact the text area cursor position.
        if (keycode == Input.Keys.UP) {
            // Cycle up through previous commands.
            return true;
        } else if (keycode == Input.Keys.DOWN) {
            // Cycle down through previous command
            return true;
        } else if (keycode == Input.Keys.LEFT) {
            this.terminalArea.moveCursor(CursorMovement.LEFT);
            return true;
        } else if (keycode == Input.Keys.RIGHT) {
            this.terminalArea.moveCursor(CursorMovement.RIGHT);
            return true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        // Check whether the current character is a new-line character.
        if (character == '\n' || character == '\r') {
            // Get the current input.
            String input = this.terminalArea.getInput();

            // TODO Execute the current input as a command!
            this.terminalArea.printLine("input: " + input);

            //  Clear the current input.
            this.terminalArea.setInput("");
        } else if (Constants.INPUT_VALID_CHARACTERS.indexOf(character) != -1) {
            // Add the character to the current terminal input.
            this.terminalArea.insertText(String.valueOf(character));
        } else if (character == '\b') {
            // Take care of the backspace.
            this.terminalArea.backspace();
        }

        // The event was handled here.
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public String getStateName() {
        return "TERMINAL";
    }
}
