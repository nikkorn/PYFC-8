package com.dumbpug.sfc.font;

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
        FreeTypeFontGenerator generator                       = new FreeTypeFontGenerator(Gdx.files.internal("fonts/press-start-2p.regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        // Set the size of the font.
        parameter.size = size;

        // Give all fonts a cool shadow (for now)
        parameter.shadowOffsetX = 1;
        parameter.shadowOffsetY = 1;

        // Give all fonts a cool border (for now)
        // parameter.borderWidth = 2;

        // Generate and return the font.
        BitmapFont font = generator.generateFont(parameter);

        generator.dispose();

        // Return the font.
        return font;
    }
}