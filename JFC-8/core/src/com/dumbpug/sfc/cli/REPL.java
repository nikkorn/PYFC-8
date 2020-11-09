package com.dumbpug.sfc.cli;

import com.dumbpug.sfc.SFC;
import java.util.Scanner;

/**
 * Handles the application Read-Evaluate-Print loop to process user commands and interaction with SFC.
 */
public class REPL {
    /**
     * The currently active SFC.
     */
    private SFC sfc;

    /**
     * Creates a new instance of the REPL class.
     * @param sfc The currently active SFC.
     */
    public REPL(SFC sfc) {
        this.sfc = sfc;
    }

    /**
     * Start a Read-Evaluate-Print loop to process user commands and launch/interact with SFC.
     */
    public void start() {
        // Start a REPL in which the user can:
        // - Create a new cart (> create some-cool-game) which generates directory (/some-cool-name/*) with same name full of unpacked cart resource files.
        // - Edit a cart image and create directory with unpacked files (> edit some-cool-game.png) which creates /some-cool-name/*
        // - Save to a cart image (> save some-cool-game) which creates or updates any cart image with a name same-cool-game with the resources in the directory some-cool-game.
        // - Save to a different cart image (> save some-cool-game some-cool-game-2) which creates or updates any cart image with a name same-cool-game-2 with the resources in the directory some-cool-game.
        // - Validate whether a cart directory could be made into an image and spit out result (> validate some-cool-game)
        //   - Result could be:
        //     - OK CHARACTERS(123/1230)
        //     - ERROR CHARACTERS(1250/1230)   (too many characters in game/server code)

        // - Launch a window with nothing loaded (> launch) leaving nothing as the current context.
        // - Launch a fullscreen window with nothing loaded (> launch fs) leaving nothing as the current context.
        // - Launch a window with a cart image already loaded (> launch some-cool-game.png) setting the cart image as the current context.
        // - Launch a window with a cart directory already loaded (> launch some-cool-game) setting the cart directory as the current context.

        // - Close a window (> close) leaving nothing as the current context.

        // - Load a cart image into existing window (> load some-cool-game.png) setting the cart image as the current context.
        // - Load a cart directory into existing window (> load some-cool-game) setting the cart directory as the current context.
        //   - Passing (hot-reload) as an additional argument will cause a reload of the cart whenever the loaded cart image or directory is updated.
        // - Unload a cart from window (> unload) leaving nothing as the current context.
        // - Reload whatever cart is currently loaded in the window (> reload)

        // - Deploy a cart image server locally (> deploy some-cool-game.png)
        // - Deploy a cart directory server locally (> deploy some-cool-game)

        //  - Exit the REPL (> exit)

        // Create scanner to read console input.
        Scanner scanner = new Scanner(System.in);

        System.out.print("> ");
        String input = scanner.nextLine();

        while (true) {
            switch (input.toLowerCase()) {
                case "help":
                    System.out.println("here is some help!");
                    break;

                case "exit":
                    // Close the active SFC application and leave REPL.
                    this.sfc.addCommand("exit");
                    return;

                default:
            }

            System.out.println();
            System.out.print("> ");
            input = scanner.nextLine();
        }
    }
}
