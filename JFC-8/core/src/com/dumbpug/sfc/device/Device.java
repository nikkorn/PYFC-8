package com.dumbpug.sfc.device;

/**
 * Represents the physical console device.
 */
public class Device {
    /**
     * The loaded cartridge.
     */
    private Cartridge cartridge = null;
    /**
     * The script data in the context of the console device.
     */
    private ScriptData scriptData = new ScriptData();
    /**
     * The sprite data in the context of the console device.
     */
    private SpriteData spriteData = new SpriteData();

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
     * Gets the script data in the context of the console device.
     * @return The script data in the context of the console device.
     */
    public ScriptData getScriptData() {
        return this.scriptData;
    }

    /**
     * Gets the sprite data in the context of the console device.
     * @return The sprite data in the context of the console device.
     */
    public SpriteData getSpriteData() {
        return this.spriteData;
    }
}
