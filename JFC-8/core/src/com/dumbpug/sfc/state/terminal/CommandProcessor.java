package com.dumbpug.sfc.state.terminal;

import com.badlogic.gdx.Gdx;
import com.dumbpug.sfc.Constants;
import com.dumbpug.sfc.components.terminal.TerminalArea;
import com.dumbpug.sfc.palette.Colour;

/**
 * Processor of terminalState commands.
 */
public class CommandProcessor {
    /**
     * The terminal area.
     */
    TerminalArea terminalArea;

    /**
     * Creates a new instance of the CommandProcessor
     * @param terminalArea The terminal area.
     */
    public CommandProcessor(TerminalArea terminalArea) {
        this.terminalArea = terminalArea;
    }

    /**
     * Process the terminalState command.
     * @param command The terminalState command.
     */
    public void process(String command) {
        // Is this an 'exit' command?
        if (command.toLowerCase().equals("exit")) {
            // Exit the application now!
            Gdx.app.exit();
            return;
        }

        // Is this a 'version' command?
        if (command.toLowerCase().equals("version")) {
            onVersionCommand();
            return;
        }

        // Is this a 'clear' command?
        if (command.toLowerCase().equals("clear")) {
            onClearCommand();
            return;
        }

        // Is this a 'help' command?
        if (command.toLowerCase().equals("help")) {
            onHelpCommand();
            return;
        }

        // We have no idea what to do with this command.
        onUnknownCommand(command);
    }

    /**
     * Handle a 'exit' command.
     */
    private void onClearCommand() {
        this.terminalArea.clear();
    }

    /**
     * Handle a help request command.
     */
    private void onHelpCommand() {
        terminalArea.printLine("commands", Colour.PURPLE);
        terminalArea.print("clear                ", Colour.YELLOW);
        terminalArea.printLine("version              ", Colour.YELLOW);
        terminalArea.print("exit                 ", Colour.YELLOW);

        terminalArea.printLine("\nshortcuts", Colour.PURPLE);
        terminalArea.print("F1             ", Colour.YELLOW);
        terminalArea.printLine("go to terminal", Colour.GREY);
        terminalArea.print("F2             ", Colour.YELLOW);
        terminalArea.printLine("go to cart", Colour.GREY);
        terminalArea.print("F9             ", Colour.YELLOW);
        terminalArea.printLine("take screenshot", Colour.GREY);
    }

    /**
     * Handle a version request command.
     */
    private void onVersionCommand() {
        terminalArea.printLine(Constants.APPLICATION_VERSION, Colour.YELLOW);
    }

    /**
     * Handle an unknown command.
     * @param command The unknown command.
     */
    private void onUnknownCommand(String command) {
        terminalArea.printLine("invalid command: " + command, Colour.RED);
    }
}
