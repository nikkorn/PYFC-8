package com.dumbpug.jfc8.device;

import com.dumbpug.jfc8.device.filesystem.FileSystem;

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
    private FileSystem fileSystem = new FileSystem();
    /**
     * The representation of the script editor in the context of the console device.
     */
    private ScriptEditor scriptEditor = new ScriptEditor();

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

    /**
     * Get the file system of the device.
     * @return The device file system.
     */
    public FileSystem getFileSystem() {
        return this.fileSystem;
    }

    /**
     * Gets the representation of the script editor in the context of the console device.
     * @return The representation of the script editor in the context of the console device.
     */
    public ScriptEditor getScriptEditor() {
        return this.scriptEditor;
    }
}
