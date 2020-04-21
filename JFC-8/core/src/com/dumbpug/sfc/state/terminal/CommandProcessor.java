package com.dumbpug.sfc.state.terminal;

import com.badlogic.gdx.Gdx;

/**
 * Processor of terminalState commands.
 */
public class CommandProcessor {
    /**
     * The terminalState.
     */
    private com.dumbpug.sfc.state.terminal.TerminalState terminalState;

    /**
     * Creates a new instance of the CommandProcessor
     * @param terminalState The terminalState.
     */
    public CommandProcessor(TerminalState terminalState) {
        this.terminalState = terminalState;
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
            this.terminalState.onVersionCommand();
            return;
        }

        // Is this a 'clear' command?
        if (command.toLowerCase().equals("clear")) {
            this.terminalState.onClearCommand();
            return;
        }

        // Is this a 'help' command?
        if (command.toLowerCase().equals("help")) {
            this.terminalState.onHelpCommand();
            return;
        }

        // We have no idea what to do with this command.
        this.terminalState.onUnknownCommand(command);
    }
}
