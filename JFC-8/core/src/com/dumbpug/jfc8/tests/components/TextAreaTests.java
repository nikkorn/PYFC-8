package com.dumbpug.jfc8.tests.components;

import com.dumbpug.jfc8.components.textarea.CursorMovement;
import com.dumbpug.jfc8.components.textarea.TextArea;

public class TextAreaTests {

    public static void main(String[] args) {
        TextArea textArea = new TextArea(0, 0, 16,12, 10, 30);

        textArea.setText("My favourite thing is cake!");

        // Move back a bit.
        textArea.moveCursor(CursorMovement.LEFT);
        textArea.moveCursor(CursorMovement.LEFT);
        textArea.moveCursor(CursorMovement.LEFT);
        textArea.moveCursor(CursorMovement.LEFT);
        textArea.moveCursor(CursorMovement.LEFT);
        textArea.moveCursor(CursorMovement.LEFT);
        textArea.moveCursor(CursorMovement.LEFT);

        // Ooops too far!
        textArea.moveCursor(CursorMovement.RIGHT);
        textArea.moveCursor(CursorMovement.RIGHT);

        // Stick in some text at the cursor position.
        textArea.insertText("not actually ");

        System.out.println(textArea.getText());
    }
}
