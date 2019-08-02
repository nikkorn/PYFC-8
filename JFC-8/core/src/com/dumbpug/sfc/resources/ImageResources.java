package com.dumbpug.sfc.resources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.dumbpug.sfc.Constants;

/**
 * Holder of image resources
 */
public class ImageResources {
    //============================================
    // General resources.
    //============================================
    private static Sprite cursor;

    static {
        // Create the cursor sprite.
        cursor = new Sprite(new Texture(Gdx.files.internal("images/general/cursor.png")));
        cursor.setSize(Constants.DISPLAY_CURSOR_SIZE * Constants.DISPLAY_PIXEL_SIZE, Constants.DISPLAY_CURSOR_SIZE * Constants.DISPLAY_PIXEL_SIZE);
    }

    /**
     * Get the cursor sprite.
     * @return The cursor sprite.
     */
    public static Sprite getCursorSprite() {
        return ImageResources.cursor;
    }
}
