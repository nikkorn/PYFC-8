package com.dumbpug.jfc8.device.filesystem;

import com.dumbpug.jfc8.Constants;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;

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
        return this.rootDir.toPath().relativize(this.currentDir.toPath()).normalize().toString();
    }

    /**
     * Gets a sorted list of file names for files in the current directory.
     * @return A sorted list of file names for files in the current directory.
     */
    public ArrayList<String> getFileNamesInCurrentDirectory() {
        ArrayList<String> names = new ArrayList<String>();

        for (File file : this.currentDir.listFiles()) {
            if (!file.isDirectory()) {
                names.add(file.getName());
            }
        }

        Collections.sort(names);

        return names;
    }

    /**
     * Gets a sorted list of directory names for directories in the current directory.
     * @return A sorted list of directory names for directories in the current directory.
     */
    public ArrayList<String> getDirectoryNamesInCurrentDirectory() {
        ArrayList<String> names = new ArrayList<String>();

        for (File file : this.currentDir.listFiles()) {
            if (file.isDirectory()) {
                names.add(file.getName());
            }
        }

        Collections.sort(names);

        return names;
    }

    /**
     * Change the current directory.
     * @param path The directory path.
     * @throws InvalidPathException The exception thrown when the requested path is not valid.
     */
    public void changeDirectory(String path) throws InvalidPathException {
        // Get the file that is a join of the current path and the new directory path.
        File targetDirectoryFile = new File(this.currentDir, path);

        // Check whether the given path is a valid one and inside the root directory.
        if (!this.isPathValid(targetDirectoryFile.toPath())) {
            throw new InvalidPathException(this.rootDir.toPath().relativize(targetDirectoryFile.toPath()).normalize().toString());
        }

        // Check to make sure that this is a valid directory to move to.
        if (targetDirectoryFile.exists() && targetDirectoryFile.isDirectory()) {
            this.currentDir = targetDirectoryFile;
        } else {
            throw new InvalidPathException(this.rootDir.toPath().relativize(targetDirectoryFile.toPath()).normalize().toString());
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

    /**
     * Gets whether the given path is valid.
     * @param path The path to check.
     * @return whether the given path is valid.
     */
    private boolean isPathValid(Path path) {
        try {
            return path.normalize().toFile().getCanonicalPath().startsWith(this.rootDir.getCanonicalPath());
        } catch (IOException e) {}
        return false;
    }
}
