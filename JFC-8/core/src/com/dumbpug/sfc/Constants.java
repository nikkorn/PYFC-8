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
    public static final int WINDOW_HEIGHT 	                        = 512;
    public static final int WINDOW_WIDTH	                        = 768;
    public static final int DISPLAY_HEIGHT 	                        = 256;
    public static final int DISPLAY_WIDTH	                        = 384;
    public static final float DISPLAY_ASPECT_RATIO                  = DISPLAY_HEIGHT / DISPLAY_WIDTH;
    public static final int DISPLAY_PIXEL_SIZE 	                    = Gdx.graphics.getHeight() / DISPLAY_HEIGHT;
    public static final int DISPLAY_CURSOR_SIZE 	                = 10;

    //===========================================================
    // SPLASH
    //===========================================================
    public static final long SPLASH_DURATION 	                    = 500l;
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
    public static final int SPRITE_EDITOR_PAINT_AREA_SIZE	        = 64;
    public static final int SPRITE_EDITOR_SHEET_UNIT            	= 16;
    public static final int SPRITE_EDITOR_SHEET_UNITS_HIGH          = 15;
    public static final int SPRITE_EDITOR_SHEET_UNITS_WIDE          = 13;
    public static final int SPRITE_EDITOR_SELECTION_SMALL_PIXELS    = 16;
    public static final int SPRITE_EDITOR_SELECTION_MEDIUM_PIXELS   = 32;
    public static final int SPRITE_EDITOR_SELECTION_LARGE_PIXELS    = 64;
    public static final int SPRITE_EDITOR_SELECTION_POINTER_PIXELS  = 10;

    //===========================================================
    // FILE SYSTEM
    //===========================================================
    public static final String FILE_SYSTEM_ROOT	                    = "./filesystem/";

}
