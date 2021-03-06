package com.dumbpug.sfc.terminal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.dumbpug.sfc.device.filesystem.InvalidPathException;
import com.dumbpug.sfc.palette.Colour;
import com.dumbpug.sfc.palette.Palette;
import com.dumbpug.sfc.Constants;
import com.dumbpug.sfc.components.terminal.CursorMovement;
import com.dumbpug.sfc.components.terminal.TerminalArea;
import com.dumbpug.sfc.components.terminal.TerminalAreaConfiguration;
import com.dumbpug.sfc.device.Device;
import com.dumbpug.sfc.font.FontProvider;
import com.dumbpug.sfc.state.State;

import java.util.ArrayList;

/**
 * The terminal state of the application.
 */
public class TerminalState extends com.dumbpug.sfc.state.State implements InputProcessor {
    /**
     * The console device.
     */
    private com.dumbpug.sfc.device.Device device;
    /**
     * The text area used as the terminal.
     */
    com.dumbpug.sfc.components.terminal.TerminalArea terminalArea;
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
    private CommandProcessor commandProcessor = new CommandProcessor(this);
    /**
     * The previously executed commands.
     */
    ArrayList<String> executed = new ArrayList<String>();
    /**
     * The background sprite.
     */
    private Sprite background;

    /**
     * Create a new instance of the TerminalState class.
     * @param device The console device.
     */
    public TerminalState(Device device) {
        // Get a reference to the console device.
        this.device = device;

        // Create the terminal font.
        terminalFont = FontProvider.getFont(com.dumbpug.sfc.Constants.SCRIPT_EDITOR_FONT_SIZE * com.dumbpug.sfc.Constants.DISPLAY_PIXEL_SIZE);
        terminalFont.setColor(Palette.getColour(Colour.WHITE));

        // Create the terminal text area.
        terminalArea = new TerminalArea(
                (com.dumbpug.sfc.Constants.SCRIPT_EDITOR_MARGIN_SIZE * 3) * com.dumbpug.sfc.Constants.DISPLAY_PIXEL_SIZE,
                (com.dumbpug.sfc.Constants.SCRIPT_EDITOR_MARGIN_SIZE * 2) * com.dumbpug.sfc.Constants.DISPLAY_PIXEL_SIZE,
                (com.dumbpug.sfc.Constants.SCRIPT_EDITOR_FONT_SIZE + com.dumbpug.sfc.Constants.SCRIPT_EDITOR_LINE_MARGIN_SIZE) * com.dumbpug.sfc.Constants.DISPLAY_PIXEL_SIZE,
                (com.dumbpug.sfc.Constants.SCRIPT_EDITOR_FONT_SIZE + com.dumbpug.sfc.Constants.SCRIPT_EDITOR_COLUMN_MARGIN_SIZE) * com.dumbpug.sfc.Constants.DISPLAY_PIXEL_SIZE,
                18,
                44,
                new TerminalAreaConfiguration()
        );

        // Print the terminal header.
        terminalArea.print("(c) nikolas howard 2019 ", Colour.GREY);
        terminalArea.printLine("v" + com.dumbpug.sfc.Constants.APPLICATION_VERSION, Colour.IRON);
        terminalArea.printLine("Type 'help' for help\n", Colour.GREY);

        // Create and position the background sprite.
        background = new Sprite(new Texture(Gdx.files.internal("images/terminal/background.png")));
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        background.setPosition(0, 0);
    }

    /**
     * Handle a clear command.
     */
    public void onClearCommand() {
        this.terminalArea.clear();
    }

    /**
     * Handle an unknown command.
     * @param command The unknown command.
     */
    public void onUnknownCommand(String command) {
        terminalArea.printLine("invalid command: " + command, Colour.RED);
    }

    /**
     * Handle a help request command.
     */
    public void onHelpCommand() {
        terminalArea.printLine("commands", Colour.PURPLE);
        terminalArea.print("clear                ", Colour.YELLOW);
        terminalArea.printLine("create [name]        ", Colour.YELLOW);
        terminalArea.print("load [name]          ", Colour.YELLOW);
        terminalArea.printLine("save                 ", Colour.YELLOW);
        terminalArea.print("ls                   ", Colour.YELLOW);
        terminalArea.printLine("cd [path]            ", Colour.YELLOW);
        terminalArea.print("mkdir [path]         ", Colour.YELLOW);
        terminalArea.printLine("version              ", Colour.YELLOW);
        terminalArea.print("exit                 ", Colour.YELLOW);

        terminalArea.printLine("\nshortcuts", Colour.PURPLE);
        terminalArea.print("F1             ", Colour.YELLOW);
        terminalArea.printLine("go to terminal", Colour.GREY);
        terminalArea.print("F2             ", Colour.YELLOW);
        terminalArea.printLine("go to script editor", Colour.GREY);
        terminalArea.print("F3             ", Colour.YELLOW);
        terminalArea.printLine("run", Colour.GREY);
        terminalArea.print("F9             ", Colour.YELLOW);
        terminalArea.printLine("take screenshot", Colour.GREY);
    }

    /**
     * Handle a version request command.
     */
    public void onVersionCommand() {
        terminalArea.printLine(com.dumbpug.sfc.Constants.APPLICATION_VERSION, Colour.YELLOW);
    }

    /**
     * Handle a 'list files' command.
     */
    public void onListFilesCommand() {
        for (String name : this.device.getFileSystem().getDirectoryNamesInCurrentDirectory()) {
            terminalArea.printLine(name + "/", Colour.PURPLE);
        }
        for (String name : this.device.getFileSystem().getFileNamesInCurrentDirectory()) {
            terminalArea.printLine(name, Colour.YELLOW);
        }
    }

    /**
     * Handle a 'change directory' command.
     * @param path The directory path.
     */
    public void onChangeDirectoryCommand(String path) {
        try {
            // Attempt to change the current directory in the filesystem.
            this.device.getFileSystem().changeDirectory(path);

            // Update the terminal input prefix to represent the current path.
            this.terminalArea.setInputLinePrefix(this.device.getFileSystem().getPath(), Colour.FOREST);
        } catch (InvalidPathException exception) {
            terminalArea.printLine("invalid path: " + exception.getPath(), Colour.RED);
        }
    }

    /**
     * Handle a 'make directory' command.
     * @param path The directory path.
     */
    public void onMakeDirectoryCommand(String path) {
        // Attempt to change the current directory in the filesystem.
        this.device.getFileSystem().makeDirectory(path);
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
        if (Gdx.input.isKeyJustPressed(Input.Keys.F3)) {
            this.changeState("RUNTIME");
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        // Draw the state background.
        this.background.draw(batch);

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
            this.terminalArea.moveCursor(com.dumbpug.sfc.components.terminal.CursorMovement.LEFT);
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

            // Do nothing if there is no input.
            if (input.equals("")) {
                return true;
            }

            // Print the input to the terminal.
            terminalArea.printLine(input.trim());

            // Execute the trimmed current input as a command!
            this.commandProcessor.process(input.trim());

            //  Clear the current input.
            this.terminalArea.setInput("");
        } else if (character == '\b') {
            // Take care of the backspace.
            this.terminalArea.backspace();
        } else if (Constants.INPUT_VALID_CHARACTERS.indexOf(character) != -1) {
            // Add the character to the current terminal input.
            this.terminalArea.insertText(String.valueOf(character));
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
