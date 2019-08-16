package com.dumbpug.sfc.spriteeditor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dumbpug.sfc.Constants;
import com.dumbpug.sfc.components.interactable.InteractableArea;
import com.dumbpug.sfc.components.paintarea.PaintArea;
import com.dumbpug.sfc.components.spritesheetarea.SpriteSheetArea;
import com.dumbpug.sfc.device.Device;
import com.dumbpug.sfc.resources.GeneralResources;
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
     * The sprite sheet area.
     */
    private SpriteSheetArea spriteSheetArea;
    /**
     * The paintable area.
     */
    private PaintArea paintArea;
    /**
     * The paint area toolbar.
     */
    private SpriteEditorToolbar editorToolbar;
    /**
     * The input processor for this state.
     */
    private SpriteEditorStateInputProcessor stateInputProcessor;
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

        // Create the sprite sheet area.
        this.spriteSheetArea = new SpriteSheetArea(
                device.getSpriteData(),
                160 * Constants.DISPLAY_PIXEL_SIZE,
                0 * Constants.DISPLAY_PIXEL_SIZE,
                224 * Constants.DISPLAY_PIXEL_SIZE,
                256 * Constants.DISPLAY_PIXEL_SIZE
        );

        // Create the paintable area, passing in a reference to the active sprite sheet selection.
        this.paintArea = new PaintArea(
                this.spriteSheetArea.getPaintableTarget(),
                14 * Constants.DISPLAY_PIXEL_SIZE,
                124 * Constants.DISPLAY_PIXEL_SIZE,
                128 * Constants.DISPLAY_PIXEL_SIZE
        );

        // Create the paint editor toolbar.
        this.editorToolbar = new SpriteEditorToolbar(
                6 * Constants.DISPLAY_PIXEL_SIZE,
                47 * Constants.DISPLAY_PIXEL_SIZE,
                144 * Constants.DISPLAY_PIXEL_SIZE,
                42 * Constants.DISPLAY_PIXEL_SIZE
        );

        // Create the input processor for this state.
        this.stateInputProcessor = new SpriteEditorStateInputProcessor(device, this.createSpriteEditorInteractableArea());

        // Create and position the background sprite.
        background = new Sprite(new Texture(Gdx.files.internal("images/sprite_editor/background.png")));
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        background.setPosition(0, 0);
    }

    @Override
    public void onEntry(State state) {
        // Set the application input processor to be the one associated with this state.
        Gdx.input.setInputProcessor(this.stateInputProcessor);

        // Refresh the sprite sheet area so that the pixels displayed match the device sprite data.
        this.spriteSheetArea.refresh();
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

        // Draw the sprite sheet area.
        this.spriteSheetArea.draw(batch);

        // Draw the editor toolbar area.
        this.editorToolbar.draw(batch);

        // Draw the paint area.
        this.paintArea.draw(batch);

        // Draw the default cursor.
        Sprite cursor = GeneralResources.getDefaultCursorSprite();
        cursor.setPosition(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY() - cursor.getHeight());
        cursor.draw(batch);
    }

    @Override
    public String getStateName() {
        return "SPRITE_EDITOR";
    }

    /**
     * Create the interactable area that covers the sprite editor.
     * @return The interactable area that covers the sprite editor.
     */
    private InteractableArea createSpriteEditorInteractableArea() {
        // Create the interactable area that covers the entire editor.
        InteractableArea editorArea = new InteractableArea(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Add the sprite sheet area interactable element.
        editorArea.addInteractable(this.spriteSheetArea);

        // Add the editor toolbar area.
        editorArea.addInteractable(this.editorToolbar);

        return editorArea;
    }
}
