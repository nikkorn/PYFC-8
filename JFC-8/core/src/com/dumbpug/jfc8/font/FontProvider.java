package com.dumbpug.jfc8.font;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/**
 * The provider of application fonts.
 */
public class FontProvider {

    /**
     * Get the default application font with the specified size.
     * @param size The font size.
     * @return The font of the specified type.
     */
    public static BitmapFont getFont(int size) {
        FreeTypeFontGenerator generator                       = new FreeTypeFontGenerator(Gdx.files.internal("fonts/kongtext.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        // Set the size of the font.
        parameter.size = size;

        // Generate and return the font.
        BitmapFont font = generator.generateFont(parameter);

        font.getData().markupEnabled = true;

        generator.dispose();

        // Return the font.
        return font;
    }
}