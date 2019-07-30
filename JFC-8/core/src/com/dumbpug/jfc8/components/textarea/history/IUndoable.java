package com.dumbpug.jfc8.components.textarea.history;

public interface IUndoable {
    /**
     * Undo the undoable operation.
     */
    void undo();

    /**
     * Redo the undoable operation.
     */
    void redo();
}
