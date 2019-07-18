package com.dumbpug.jfc8.device.filesystem;

import com.dumbpug.jfc8.Constants;
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
        this.rootDir    = new File(Constants.FILE_SYSTEM_ROOT);
        this.currentDir = new File(Constants.FILE_SYSTEM_ROOT);
    }

    /**
     * Get the path of the current directory.
     * @return The path of the current directory.
     */
    public String getPath() {
        return this.currentDir.getPath().substring(Constants.FILE_SYSTEM_ROOT.length());
    }

    /**
     * Change the current directory.
     * @param path The directory path.
     * @throws InvalidPathException The exception thrown when the requested path is not valid.
     */
    public void changeDirectory(String path) throws InvalidPathException {
        // Get the file that is a join of the current path and the new directory path.
        File targetDirectoryFile = new File(this.currentDir, path);

        // Check to make sure that this is a valid directory to move to.
        if (targetDirectoryFile.exists() && targetDirectoryFile.isDirectory()) {
            this.currentDir = targetDirectoryFile;
        } else {
            throw new InvalidPathException(targetDirectoryFile.getPath().substring(Constants.FILE_SYSTEM_ROOT.length()));
        }
    }

    /**
     * Attempt to make a directory in the current directory.
     * @param directory The path of the directory.
     */
    public void makeDirectory(String directory) {
        // Get the file that is a join of the current path and the new directory path.
        File directoryFile = new File(this.currentDir, directory);

        // Create the directory if it doesn't exist, or it does exist but is not actually a directory.
        if (!directoryFile.exists() || !directoryFile.isDirectory()) {
            try{
                directoryFile.mkdir();
            }
            catch(SecurityException se){}
        }
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
