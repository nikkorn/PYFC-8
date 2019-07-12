package com.dumbpug.jfc8.scripteditor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.dumbpug.jfc8.Constants;
import com.dumbpug.jfc8.components.textarea.TextArea;
import com.dumbpug.jfc8.font.FontProvider;
import com.dumbpug.jfc8.palette.Colour;
import com.dumbpug.jfc8.palette.Palette;
import com.dumbpug.jfc8.resources.ImageResources;
import com.dumbpug.jfc8.state.State;

/**
 * The script editor state of the application.
 */
public class ScriptEditor extends State {
    /**
     * The input processor for this state.
     * TODO These can be chained and input event can fall through. Have a general state one and THEN a text area one.
     */
    private ScriptEditorInputProcessor scriptEditorInputProcessor;
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
     * The background sprite.
     */
    private Sprite background;

    /**
     * Create a new instance of the ScriptEditor class.
     */
    public ScriptEditor() {
        // Create the editor font.
        editorFont = FontProvider.getFont(Constants.SCRIPT_EDITOR_FONT_SIZE * Constants.DISPLAY_PIXEL_SIZE);
        editorFont.setColor(Palette.getColour(Colour.WHITE));

        // Create the editor text area.
        editorTextArea = new TextArea(
                Constants.SCRIPT_EDITOR_MARGIN_SIZE * Constants.DISPLAY_PIXEL_SIZE,
                Constants.SCRIPT_EDITOR_MARGIN_SIZE * Constants.DISPLAY_PIXEL_SIZE,
                (Constants.SCRIPT_EDITOR_FONT_SIZE + Constants.SCRIPT_EDITOR_LINE_MARGIN_SIZE) * Constants.DISPLAY_PIXEL_SIZE,
                (Constants.SCRIPT_EDITOR_FONT_SIZE + Constants.SCRIPT_EDITOR_COLUMN_MARGIN_SIZE) * Constants.DISPLAY_PIXEL_SIZE,
                19,
                44
        );

        // Create the input processor for this state.
        scriptEditorInputProcessor = new ScriptEditorInputProcessor(editorTextArea);

        // Create and position the background sprite.
        background = new Sprite(new Texture(Gdx.files.internal("images/script_editor/background.png")));
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        background.setPosition(0, 0);
    }

    @Override
    public void onEntry(State state) {
        // Set the application input processor to be the one associated with this state.
        Gdx.input.setInputProcessor(this.scriptEditorInputProcessor);
    }

    @Override
    public void onExit() {
        // Unset the application input processor to be the one associated with this state.
        Gdx.input.setInputProcessor(this.scriptEditorInputProcessor);
    }

    @Override
    public void update() {

    }

    @Override
    public void render(SpriteBatch batch) {
        // Draw the state background.
        this.background.draw(batch);

        // Draw the editor text area.
        editorTextArea.draw(batch, shapeRenderer, this.editorFont);

        // Create the text to use as the character count limit.
        String characterCountText = this.editorTextArea.getText().length() + "/" + Constants.SCRIPT_EDITOR_MAX_CHARACTERS;

        // Create a glyph layout so we can get the actual size of the character count text.
        GlyphLayout glyphLayout = new GlyphLayout();
        glyphLayout.setText(this.editorFont, characterCountText);

        // Draw the character count.
        this.editorFont.draw(
                batch,
                characterCountText,
                Gdx.graphics.getWidth() - (glyphLayout.width + (4 * Constants.DISPLAY_PIXEL_SIZE)),
                Gdx.graphics.getHeight() - 6 * Constants.DISPLAY_PIXEL_SIZE
        );

        // Draw the cursor.
        Sprite cursor = ImageResources.getCursorSprite();
        cursor.setPosition(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY() - cursor.getHeight());
        cursor.draw(batch);
    }

    @Override
    public String getStateName() {
        return "SCRIPT_EDITOR";
    }
}
