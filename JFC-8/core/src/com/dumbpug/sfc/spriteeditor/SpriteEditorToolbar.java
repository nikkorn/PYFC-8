package com.dumbpug.sfc.spriteeditor;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dumbpug.sfc.Constants;
import com.dumbpug.sfc.components.interactable.IInteractionHandler;
import com.dumbpug.sfc.components.interactable.InteractableArea;
import com.dumbpug.sfc.components.paintarea.InputMode;
import com.dumbpug.sfc.palette.Colour;
import java.util.ArrayList;

/**
 * The toolbar wrapping the colour selection, input selection and selection transformation controls.
 */
public class SpriteEditorToolbar extends InteractableArea {
    /**
     * The current colour.
     */
    private Colour colour = Colour.WHITE;
    /**
     * The current input mode.
     */
    private InputMode inputMode = InputMode.PAINT_SMALL;
    /**
     * The toolbar colour buttons.
     */
    private ArrayList<ToolbarColourButton> toolbarColourButtons;
    /**
     * The toolbar input mode buttons.
     */
    private ArrayList<ToolbarInputModeButton> toolbarInputModeButtons;

    /**
     * Creates a new instance of the SpriteEditorToolbar class.
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public SpriteEditorToolbar(float x, float y, float width, float height) {
        super(x, y, width, height);

        // Create the button interactable elements for the colour bar.
        toolbarColourButtons = new ArrayList<ToolbarColourButton>() {{
            add(createColourBarButton(14,54, Colour.CHARCOAL));
            add(createColourBarButton(22,54, Colour.IRON));
            add(createColourBarButton(30,54, Colour.GREY));
            add(createColourBarButton(38,54, Colour.CYAN));
            add(createColourBarButton(46,54, Colour.CERULEAN));
            add(createColourBarButton(54,54, Colour.BLUE));
            add(createColourBarButton(62,54, Colour.NAVY));
            add(createColourBarButton(70,54, Colour.FOREST));
            add(createColourBarButton(78,54, Colour.GREEN));
            add(createColourBarButton(86,54, Colour.LIME));
            add(createColourBarButton(94,54, Colour.YELLOW));
            add(createColourBarButton(102,54, Colour.ORANGE));
            add(createColourBarButton(110,54, Colour.RED));
            add(createColourBarButton(118,54, Colour.PURPLE));
            add(createColourBarButton(126,54, Colour.BLACK));
            add(createColourBarButton(134,54, Colour.WHITE));
        }};

        // Add the button interactable elements for the colour bar to this area.
        for (ToolbarColourButton button : toolbarColourButtons) {
            this.addInteractable(button);
        }

        // Create the button interactable elements for the input mode bar.
        toolbarInputModeButtons = new ArrayList<ToolbarInputModeButton>() {{
            add(createInputModeBarButton(9,73, InputMode.PAINT_SMALL));
            add(createInputModeBarButton(26,73, InputMode.PAINT_MEDIUM));
            add(createInputModeBarButton(43,73, InputMode.PAINT_LARGE));
            add(createInputModeBarButton(60,73, InputMode.FILL));
            add(createInputModeBarButton(77,73, InputMode.SELECTION));
        }};

        // Add the button interactable elements for the input mode bar to this area.
        for (ToolbarInputModeButton button : toolbarInputModeButtons) {
            this.addInteractable(button);
        }
    }

    /**
     * Gets the current colour.
     * @return The current colour.
     */
    public Colour getColour() {
        return colour;
    }

    /**
     * Gets the current input mode.
     * @return The current input mode.
     */
    public InputMode getInputMode() {
        return inputMode;
    }

    /**
     * Draw the toolbar.
     * @param batch The sprite batch.
     */
    public void draw(SpriteBatch batch) {
        // Draw the colour button selection.
        for (ToolbarColourButton button : toolbarColourButtons) {
            button.draw(batch, button.getColour() == this.colour);
        }

        // Draw the input mode button selection.
        for (ToolbarInputModeButton button : toolbarInputModeButtons) {
            button.draw(batch, button.getInputMode() == this.inputMode);
        }
    }

    /**
     * Create a colour bar button.
     * @param x The x position.
     * @param y The y position.
     * @param colourSelection The colour to be made the current colour on clicking the button.
     * @return A colour bar button.
     */
    private ToolbarColourButton createColourBarButton(float x , float y, final Colour colourSelection) {
        return new ToolbarColourButton(
            x * Constants.DISPLAY_PIXEL_SIZE,
            y * Constants.DISPLAY_PIXEL_SIZE,
            colourSelection,
            new IInteractionHandler() {
                @Override
                public boolean onElementClick(float x, float y) {
                    colour = colourSelection;
                    return true;
                }
            });
    }

    /**
     * Create an input mode bar button.
     * @param x The x position.
     * @param y The y position.
     * @param inputModeSelection The input mode to be made the current input mode on clicking the button.
     * @return An input mode bar button.
     */
    private ToolbarInputModeButton createInputModeBarButton(float x , float y, final InputMode inputModeSelection) {
        return new ToolbarInputModeButton(
                x * Constants.DISPLAY_PIXEL_SIZE,
                y * Constants.DISPLAY_PIXEL_SIZE,
                inputModeSelection,
                new IInteractionHandler() {
                    @Override
                    public boolean onElementClick(float x, float y) {
                        inputMode = inputModeSelection;
                        return true;
                    }
                });
    }
}
