package com.dumbpug.sfc.spriteeditor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dumbpug.sfc.Constants;
import com.dumbpug.sfc.device.Device;
import com.dumbpug.sfc.font.FontProvider;
import com.dumbpug.sfc.palette.Colour;
import com.dumbpug.sfc.palette.Palette;
import com.dumbpug.sfc.resources.ImageResources;
import com.dumbpug.sfc.state.State;

/**
 * The sprite editor state of the application.
 */
public class SpriteEditorState extends State {
    /**
     * The console device.
     */
    private Device device;
    /**
     * The editor font.
     */
    private BitmapFont editorFont;
    /**
     * The background sprite.
     */
    private Sprite background;

    /**
     * Create a new instance of the SpriteEditorState class.
     * @param device The console device.
     */
    public SpriteEditorState(Device device) {
        // Get a reference to the console device.
        this.device = device;

        // Create the editor font.
        editorFont = FontProvider.getFont(Constants.SCRIPT_EDITOR_FONT_SIZE * Constants.DISPLAY_PIXEL_SIZE);
        editorFont.setColor(Palette.getColour(Colour.WHITE));

        // Create and position the background sprite.
        background = new Sprite(new Texture(Gdx.files.internal("images/sprite_editor/background.png")));
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        background.setPosition(0, 0);
    }

    @Override
    public void onEntry(State state) {
    }

    @Override
    public void onExit() {
    }

    @Override
    public void update() {
        // Check whether the user is attempting to move state.
        if (Gdx.input.isKeyJustPressed(Input.Keys.F1)) {
            this.changeState("TERMINAL");
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.F2)) {
            this.changeState("SCRIPT_EDITOR");
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.F4)) {
            this.changeState("RUNTIME");
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        // Draw the state background.
        this.background.draw(batch);

        // Draw the default cursor.
        Sprite cursor = ImageResources.getDefaultCursorSprite();
        cursor.setPosition(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY() - cursor.getHeight());
        cursor.draw(batch);
    }

    @Override
    public String getStateName() {
        return "SPRITE_EDITOR";
    }
}
