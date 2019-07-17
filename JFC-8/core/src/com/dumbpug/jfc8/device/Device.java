package com.dumbpug.jfc8.device;

/**
 * Represents the physical console device.
 */
public class Device {
    /**
     * The loaded cartridge.
     */
    private Cartridge cartridge = null;
    /**
     * The device file system.
     */
    private FileSystem fileSystem;

    /**
     * Creates a new instance of the Device class.
     */
    public Device() {
        // Create the console file system.
        this.fileSystem = new FileSystem();
    }

    /**
     * Gets the currently loaded cartridge.
     * @return The currently loaded cartridge.
     */
    public Cartridge getCartridge() {
        return cartridge;
    }

    /**
     * Load the specified cartridge.
     * @param cartridge The cartridge to load.
     */
    public void load(Cartridge cartridge) {
        this.cartridge = cartridge;
    }

    /**
     * Unload the currently loaded cartridge.
     */
    public void unload() {
        this.cartridge = null;
    }
}
