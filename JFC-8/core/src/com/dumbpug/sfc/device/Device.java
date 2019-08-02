package com.dumbpug.sfc.device;

import com.dumbpug.sfc.device.Cartridge;
import com.dumbpug.sfc.device.ScriptEditor;
import com.dumbpug.sfc.device.filesystem.FileSystem;

/**
 * Represents the physical console device.
 */
public class Device {
    /**
     * The loaded cartridge.
     */
    private com.dumbpug.sfc.device.Cartridge cartridge = null;
    /**
     * The device file system.
     */
    private com.dumbpug.sfc.device.filesystem.FileSystem fileSystem = new com.dumbpug.sfc.device.filesystem.FileSystem();
    /**
     * The representation of the script editor in the context of the console device.
     */
    private com.dumbpug.sfc.device.ScriptEditor scriptEditor = new com.dumbpug.sfc.device.ScriptEditor();

    /**
     * Gets the currently loaded cartridge.
     * @return The currently loaded cartridge.
     */
    public com.dumbpug.sfc.device.Cartridge getCartridge() {
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
