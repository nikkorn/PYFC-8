package com.dumbpug.sfc.resources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.dumbpug.sfc.Constants;

/**
 * Holder of sprite editor resources.
 */
public class SpriteEditorResources {
    private static Sprite selection_box_small;
    private static Sprite selection_box_medium;
    private static Sprite selection_box_large;
    private static Sprite button_selection_pointer;

    static {
        selection_box_small = new Sprite(new Texture(Gdx.files.internal("images/sprite_editor/selection_small.png")));
        selection_box_small.setSize(
                Constants.SPRITE_EDITOR_SELECTION_SMALL_PIXELS * Constants.DISPLAY_PIXEL_SIZE,
                Constants.SPRITE_EDITOR_SELECTION_SMALL_PIXELS * Constants.DISPLAY_PIXEL_SIZE
        );
        selection_box_medium = new Sprite(new Texture(Gdx.files.internal("images/sprite_editor/selection_medium.png")));
        selection_box_medium.setSize(
                Constants.SPRITE_EDITOR_SELECTION_MEDIUM_PIXELS * Constants.DISPLAY_PIXEL_SIZE,
                Constants.SPRITE_EDITOR_SELECTION_MEDIUM_PIXELS * Constants.DISPLAY_PIXEL_SIZE
        );
        selection_box_large = new Sprite(new Texture(Gdx.files.internal("images/sprite_editor/selection_large.png")));
        selection_box_large.setSize(
                Constants.SPRITE_EDITOR_SELECTION_LARGE_PIXELS * Constants.DISPLAY_PIXEL_SIZE,
                Constants.SPRITE_EDITOR_SELECTION_LARGE_PIXELS * Constants.DISPLAY_PIXEL_SIZE
        );
        button_selection_pointer = new Sprite(new Texture(Gdx.files.internal("images/sprite_editor/button_selection_pointer.png")));
        button_selection_pointer.setSize(
                Constants.SPRITE_EDITOR_SELECTION_POINTER_PIXELS * Constants.DISPLAY_PIXEL_SIZE,
                Constants.SPRITE_EDITOR_SELECTION_POINTER_PIXELS * Constants.DISPLAY_PIXEL_SIZE
        );
    }

    public static Sprite getSelectionBoxSmall() {
        return selection_box_small;
    }

    public static Sprite getSelectionBoxMedium() {
        return selection_box_medium;
    }

    public static Sprite getSelectionBoxLarge() {
        return selection_box_large;
    }

    public static Sprite getButtonSelectionPointer() {
        return button_selection_pointer;
    }
}
