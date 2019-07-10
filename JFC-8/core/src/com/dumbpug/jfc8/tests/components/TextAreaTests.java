package com.dumbpug.jfc8.tests.components;

import com.dumbpug.jfc8.components.textarea.CursorMovement;
import com.dumbpug.jfc8.components.textarea.TextArea;

public class TextAreaTests {

    public static void main(String[] args) {
        SingleLineCursorTraversal();
        MultiLineCursorTraversal();
        BreakExistingLine();
    }

    public static void SingleLineCursorTraversal() {
        TextArea textArea = new TextArea(0, 0, 16,12, 10, 30);

        textArea.setText("My favourite thing");

        textArea.insertText(" is cake!");

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

    public static void MultiLineCursorTraversal() {
        TextArea textArea = new TextArea(0, 0, 16,12, 10, 30);

        textArea.setText("This is line one!\nThis is line nine!\nThis is the last one, line three!");

        textArea.moveCursor(CursorMovement.UP);

        textArea.insertText(" or is it?");

        System.out.println(textArea.getText());
    }

    private static void BreakExistingLine() {
        TextArea textArea = new TextArea(0, 0, 16,12, 10, 30);

        textArea.setText("This is line 1!\nThis is line 2!This is line 3!\nThis is line 4!");

        textArea.moveCursor(CursorMovement.UP);

        textArea.insertText("\nThis is line 2.5!\n");

        System.out.println(textArea.getText());
    }
}
