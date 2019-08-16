package com.dumbpug.sfc.resources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.dumbpug.sfc.Constants;

/**
 * Holder of general resources.
 */
public class GeneralResources {
    private static Sprite default_cursor;
    private static Sprite pencil_cursor;

    static {
        // Create the cursor sprites.
        default_cursor = new Sprite(new Texture(Gdx.files.internal("images/general/cursors/default.png")));
        default_cursor.setSize(Constants.DISPLAY_CURSOR_SIZE * Constants.DISPLAY_PIXEL_SIZE, Constants.DISPLAY_CURSOR_SIZE * Constants.DISPLAY_PIXEL_SIZE);
        pencil_cursor = new Sprite(new Texture(Gdx.files.internal("images/general/cursors/pencil.png")));
        pencil_cursor.setSize(Constants.DISPLAY_CURSOR_SIZE * Constants.DISPLAY_PIXEL_SIZE, Constants.DISPLAY_CURSOR_SIZE * Constants.DISPLAY_PIXEL_SIZE);
    }

    /**
     * Get the default cursor sprite.
     * @return The default cursor sprite.
     */
    public static Sprite getDefaultCursorSprite() {
        return GeneralResources.default_cursor;
    }

    /**
     * Get the pencil cursor sprite.
     * @return The pencil cursor sprite.
     */
    public static Sprite getPencilCursorSprite() {
        return GeneralResources.pencil_cursor;
    }
}
