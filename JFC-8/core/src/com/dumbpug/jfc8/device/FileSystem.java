package com.dumbpug.jfc8.device;

import java.io.File;

/**
 * Represents the console file system.
 */
public class FileSystem {
    /**
     * The root directory.
     */
    private File rootDir;
    /**
     * The current directory.
     */
    private File currentDir;

    /**
     * Create a new instance of the FileSystem class.
     */
    public FileSystem() {
        // Get the root and current directories.
        this.rootDir    = new File("./filesystem").getAbsoluteFile();
        this.currentDir = new File("./filesystem").getAbsoluteFile();
    }

    /**
     * Get the path of the current directory.
     * @return The path of the current directory.
     */
    public String getPath() {
        return this.currentDir.getPath();
    }

    /**
     * Change the current directory.
     * @param path The directory path.
     */
    public void changeDirectory(String path) {

    }

    /**
     * Attempt to make a directory in the current directory.
     * @param directory The name of the directory.
     */
    public void makeDirectory(String directory) {

    }

    /**
     * Attempt to get a cartridge from the current directory.
     * @param cartridge The name of the cartridge.
     */
    public void readCartridge(String cartridge) {

    }

    /**
     * Attempt to create a cartridge in the current directory.
     * @param cartridge The name of the cartridge.
     * @returns The created cartridge.
     */
    public void createCartridge(String cartridge) {

    }
}
