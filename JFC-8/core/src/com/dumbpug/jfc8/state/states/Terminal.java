package com.dumbpug.jfc8.state.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.dumbpug.jfc8.font.FontProvider;
import com.dumbpug.jfc8.font.FontType;
import com.dumbpug.jfc8.state.State;
import com.dumbpug.jfc8.terminal.CommandProcessor;

/**
 * The terminal state of the application.
 */
public class Terminal extends State {
    /**
     * The stage in which the terminal is placed.
     */
    Stage terminalStage;
    /**
     * The text area used as the terminal.
     */
    TextArea terminalTextArea;
    /**
     * The terminal command processor.
     */
    private CommandProcessor commandProcessor = new CommandProcessor();
    /**
     * This string contains text we can't edit (eg previously executed commands/command output).
     */
    String outputGarbage = "hello";
    /**
     * This string is the current line in the shell, this text we CAN edit.
     */
    String currentInput = "";

    /**
     * Create a new instance of the Terminal class.
     */
    public Terminal() {
        // Set the text field style.
        TextField.TextFieldStyle txtStyle = new TextField.TextFieldStyle();
        txtStyle.font                     = FontProvider.getFont(FontType.SHELL_FONT);
        txtStyle.fontColor                = Color.WHITE;

        // Create the terminal text area.
        terminalStage    = new Stage();
        terminalTextArea = new TextArea("", txtStyle);
        terminalTextArea.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        terminalTextArea.setFocusTraversal(true);
        terminalStage.addActor(terminalTextArea);
    }

    @Override
    public void onEntry(State state) {
    }

    @Override
    public void onExit() {
    }

    @Override
    public void update() {
        // Clear the terminal text area.
        terminalTextArea.setText("");

        // We must use 'appendText' to force focus to bottom of terminal
        terminalTextArea.appendText(outputGarbage + ">> " + currentInput);
        terminalTextArea.act(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void render(SpriteBatch batch) {
        // Draw the terminal stage wrapping the text area.
        terminalStage.draw();
    }

    @Override
    public String getStateName() {
        return "TERMINAL";
    }
}
