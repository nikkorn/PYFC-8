package com.dumbpug.sfc;

import com.badlogic.gdx.Gdx;

public class Constants {
    //===========================================================
    // GENERAL
    //===========================================================
    public static final String APPLICATION_VERSION                  = "0.0.1";

    //===========================================================
    // DEVICE
    //===========================================================
    public static final int SPRITE_SHEET_PIXELS_WIDTH 	            = 224;
    public static final int SPRITE_SHEET_PIXELS_HEIGHT	            = 256;

    //===========================================================
    // INPUT
    //===========================================================
    public static final String INPUT_VALID_CHARACTERS               = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz<>:;.,'[]{}=+-_/?!\"£$%^&*())#~\\|\n\r\t\b ";

    //===========================================================
    // DISPLAY
    //===========================================================
    public static final int DISPLAY_HEIGHT 	                        = 256;
    public static final int DISPLAY_WIDTH	                        = 384;
    public static final int DISPLAY_PIXEL_SIZE 	                    = Gdx.graphics.getHeight() / DISPLAY_HEIGHT;
    public static final int DISPLAY_CURSOR_SIZE 	                = 10;

    //===========================================================
    // SPLASH
    //===========================================================
    public static final long SPLASH_DURATION 	                    = 4000l;
    public static final int SPLASH_LOGO_SIZE 	                    = DISPLAY_PIXEL_SIZE * (DISPLAY_HEIGHT / 2);

    //===========================================================
    // TERMINAL
    //===========================================================
    public static final int TERMINAL_FONT_SIZE 	                    = 8;
    public static final int TERMINAL_MARGIN_SIZE                    = 8;

    //===========================================================
    // SCRIPT EDITOR
    //===========================================================
    public static final int SCRIPT_EDITOR_FONT_SIZE	                = 8;
    public static final int SCRIPT_EDITOR_MARGIN_SIZE               = 4;
    public static final int SCRIPT_EDITOR_LINE_MARGIN_SIZE          = 4;
    public static final int SCRIPT_EDITOR_COLUMN_MARGIN_SIZE        = 0;
    public static final int SCRIPT_EDITOR_MAX_CHARACTERS            = 40000;
    public static final int SCRIPT_EDITOR_MAX_HISTORY_SNAPSHOTS     = 25;

    //===========================================================
    // SPRITE EDITOR
    //===========================================================
    public static final int SPRITE_EDITOR_PAINT_AREA_SIZE	        = 48;
    public static final int SPRITE_EDITOR_SHEET_UNIT            	= 16;

    //===========================================================
    // FILE SYSTEM
    //===========================================================
    public static final String FILE_SYSTEM_ROOT	                    = "./filesystem/";

}
