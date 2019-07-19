package com.dumbpug.jfc8.device;

/**
 * Represents the script editor in the context of the console device.
 */
public class ScriptEditor {
    /**
     * The script editor text
     */
    private String text = "";

    /**
     * Gets the text in the script editor.
     * @return The text in the script editor.
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the text in the script editor.
     * @param text The text in the script editor.
     */
    public void setText(String text) {
        this.text = text;
    }
}

