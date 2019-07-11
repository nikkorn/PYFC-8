package com.dumbpug.jfc8;

import com.badlogic.gdx.Gdx;

public class Constants {
    //===========================================================
    // INPUT
    //===========================================================
    public static final String INPUT_VALID_CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz<>:;[]{}=+-_/?!\"£$%^&*())#~\\|\n\r ";

    //===========================================================
    // DISPLAY
    //===========================================================
    public static final int DISPLAY_HEIGHT 	          = 256;
    public static final int DISPLAY_WIDTH	          = 384;
    public static final int DISPLAY_PIXEL_SIZE 	      = Gdx.graphics.getHeight() / DISPLAY_HEIGHT;

    //===========================================================
    // SPLASH
    //===========================================================
    public static final long SPLASH_DURATION 	      = 4000l;
    public static final int SPLASH_LOGO_SIZE 	      = DISPLAY_PIXEL_SIZE * (DISPLAY_HEIGHT / 2);

    //===========================================================
    // TERMINAL
    //===========================================================
    public static final int TERMINAL_FONT_SIZE 	      = 8;
    public static final int TERMINAL_MARGIN_SIZE      = 8;

    //===========================================================
    // SCRIPT EDITOR
    //===========================================================
    public static final int SCRIPT_EDITOR_FONT_SIZE	  = 8;
    public static final int SCRIPT_EDITOR_MARGIN_SIZE = 4;
}
