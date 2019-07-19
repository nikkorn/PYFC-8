package com.dumbpug.jfc8.terminal;

/**
 * Processor of terminalState commands.
 */
public class CommandProcessor {
    /**
     * The terminalState.
     */
    private TerminalState terminalState;

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

        // Is this a 'ls' command?
        if (command.toLowerCase().equals("ls")) {
            this.terminalState.onListFilesCommand();
            return;
        }

        // Is this a 'cd' command?
        if (command.length() >= 3 && command.toLowerCase().substring(0, 3).equals("cd ")) {
            this.terminalState.onChangeDirectoryCommand(command.substring(3).trim());
            return;
        }

        // Is this a 'mkdir' command?
        if (command.length() >= 6 && command.toLowerCase().substring(0, 6).equals("mkdir ")) {
            this.terminalState.onMakeDirectoryCommand(command.substring(6).trim());
            return;
        }

        // We have no idea what to do with this command.
        this.terminalState.onUnknownCommand(command);
    }
}
