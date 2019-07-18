package com.dumbpug.jfc8.device.filesystem;

/**
 * Exception thrown when requesting a path change to an invalid path.
 */
public class InvalidPathException extends RuntimeException {
    /**
     * The invalid path.
     */
    private String path;

    /**
     * Creates a new instance of the InvalidPathException class.
     * @param path The invalid path.
     */
    public InvalidPathException(String path) {
        super("the path '" + path + "' is not valid");
        this.path = path;
    }

    /**
     * Gets the invalid path.
     * @return The invalid path.
     */
    public String getPath() {
        return this.path;
    }
}
