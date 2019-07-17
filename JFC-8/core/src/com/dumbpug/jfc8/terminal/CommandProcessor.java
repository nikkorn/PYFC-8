package com.dumbpug.jfc8.terminal;

/**
 * Processor of terminal commands.
 */
public class CommandProcessor {
    /**
     * The terminal.
     */
    private Terminal terminal;

    /**
     * Creates a new instance of the CommandProcessor
     * @param terminal The terminal.
     */
    public CommandProcessor(Terminal terminal) {
        this.terminal = terminal;
    }

    /**
     * Process the terminal command.
     * @param command The terminal command.
     */
    public void process(String command) {
        // Is this a 'clear' command?
        if (command.toLowerCase().equals("clear")) {
            this.terminal.onClearCommand();
            return;
        }

        // Is this a 'clear' command?
        if (command.toLowerCase().equals("help")) {
            this.terminal.onHelpCommand();
            return;
        }

        // We have no idea what to do with this command.
        this.terminal.onUnknownCommand(command);
    }
}
