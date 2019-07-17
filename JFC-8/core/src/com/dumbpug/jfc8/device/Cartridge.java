package com.dumbpug.jfc8.device;

import java.io.File;

/**
 * Represents a physical game cartridge.
 */
public class Cartridge {
    /**
     * The file on disk that represents the cartridge.
     */
    private File cartridgeFile;

    /**
     * Creates a new instance of the cartridge class.
     * @param cartridgeFile The file on disk that represents the cartridge.
     */
    public Cartridge(File cartridgeFile) {
        this.cartridgeFile = cartridgeFile;
    }

    /**
     * Overwrite the cartridge on disk.
     */
    public void overwrite() {
        // TODO ....
    }
}
