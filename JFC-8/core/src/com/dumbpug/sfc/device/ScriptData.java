package com.dumbpug.sfc.device;

/**
 * Represents the script data in the context of the console device.
 */
public class ScriptData {
    /**
     * The script editor text.
     */
    private String text = "var r = 0;\nvar c = 1;\nfunction update() {\n var radius = Math.random()*300;\n circle(191,128,radius,c,false);\n " +
            "r++;c++;\n if (c == 14) c = 1;\n};";

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

