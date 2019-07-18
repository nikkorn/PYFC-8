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

        // Is this a 'help' command?
        if (command.toLowerCase().equals("help")) {
            this.terminal.onHelpCommand();
            return;
        }

        // Is this a 'ls' command?
        if (command.toLowerCase().equals("ls")) {
            this.terminal.onListFilesCommand();
            return;
        }

        // Is this a 'cd' command?
        if (command.length() >= 3 && command.toLowerCase().substring(0, 3).equals("cd ")) {
            this.terminal.onChangeDirectoryCommand(command.substring(3).trim());
            return;
        }

        // Is this a 'mkdir' command?
        if (command.length() >= 6 && command.toLowerCase().substring(0, 6).equals("mkdir ")) {
            this.terminal.onMakeDirectoryCommand(command.substring(6).trim());
            return;
        }

        // We have no idea what to do with this command.
        this.terminal.onUnknownCommand(command);
    }
}
