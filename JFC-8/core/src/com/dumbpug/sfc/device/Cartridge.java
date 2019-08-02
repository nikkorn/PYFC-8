package com.dumbpug.sfc.device;

import java.awt.image.BufferedImage;
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
     * The cartridge label image.
     */
    private BufferedImage label;

    /**
     * Creates a new instance of the cartridge class.
     * @param cartridgeFile The file on disk that represents the cartridge.
     */
    public Cartridge(File cartridgeFile) {
        this.cartridgeFile = cartridgeFile;
    }

    /**
     * Sets the cartridge label.
     * @param label The cartridge label.
     */
    public void setLabel(BufferedImage label) {
        this.label = label;
    }

    /**
     * Overwrite the cartridge on disk.
     */
    public void overwrite() {
        // TODO ....
        // TODO If a label has been set then we need to create a new cart image.
    }
}
