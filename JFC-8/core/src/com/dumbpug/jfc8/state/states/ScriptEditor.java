package com.dumbpug.jfc8.state.states;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.dumbpug.jfc8.Constants;
import com.dumbpug.jfc8.components.textarea.TextArea;
import com.dumbpug.jfc8.font.FontProvider;
import com.dumbpug.jfc8.state.State;

/**
 * The script editor state of the application.
 */
public class ScriptEditor extends State {
    /**
     * The shape renderer to use in drawing the editor.
     */
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    /**
     * The editor font.
     */
    private BitmapFont editorFont;
    /**
     * The editor text area.
     */
    private TextArea editorTextArea;

    /**
     * Create a new instance of the ScriptEditor class.
     */
    public ScriptEditor() {
        // Create the editor font.
        editorFont = FontProvider.getFont(Constants.SCRIPT_EDITOR_FONT_SIZE);
        editorFont.setColor(Color.WHITE);

        // Create the editor text area.
        editorTextArea = new TextArea(
                Constants.SCRIPT_EDITOR_MARGIN_SIZE * Constants.DISPLAY_PIXEL_SIZE,
                Constants.SCRIPT_EDITOR_MARGIN_SIZE * Constants.DISPLAY_PIXEL_SIZE,
                Constants.SCRIPT_EDITOR_FONT_SIZE * Constants.DISPLAY_PIXEL_SIZE,
                Constants.SCRIPT_EDITOR_FONT_SIZE * Constants.DISPLAY_PIXEL_SIZE,
                29,
                45
        );
    }

    @Override
    public void onEntry(State state) {

    }

    @Override
    public void onExit() {

    }

    @Override
    public void update() {

    }

    @Override
    public void render(SpriteBatch batch) {
        editorTextArea.draw(batch, shapeRenderer, this.editorFont);
    }

    @Override
    public String getStateName() {
        return "SCRIPT_EDITOR";
    }
}
