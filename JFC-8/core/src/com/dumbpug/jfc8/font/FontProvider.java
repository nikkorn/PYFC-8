package com.dumbpug.jfc8.font;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 * The provider of application fonts.
 */
public class FontProvider {
    private static BitmapFont SPEECH_BOX_FONT;
    private static BitmapFont SHELL_FONT;
    private static BitmapFont GAME_PANEL_FONT;

    /**
     * Load the application fonts.
     */
    public static void loadFonts() {
        SPEECH_BOX_FONT = new BitmapFont(Gdx.files.internal("fonts/speech.fnt"), Gdx.files.internal("fonts/speech.png"), false);
        SHELL_FONT      = new BitmapFont(Gdx.files.internal("fonts/shellfont.fnt"), Gdx.files.internal("fonts/shellfont.png"), false);
        GAME_PANEL_FONT = new BitmapFont(Gdx.files.internal("fonts/gamepanel.fnt"), Gdx.files.internal("fonts/gamepanel.png"), false);
    }

    /**
     * Get the font of the specified type.
     * @param type The font type.
     * @return The font of the specified type.
     */
    public static BitmapFont getFont(FontType type) {
        switch(type) {
            case GAME_PANEL_FONT:
                return GAME_PANEL_FONT;
            case SHELL_FONT:
                return SHELL_FONT;
            case SPEECH_BOX_FONT:
                return SPEECH_BOX_FONT;
        }
        return null;
    }
}