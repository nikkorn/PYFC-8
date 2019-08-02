package com.dumbpug.sfc.components.textarea.history;

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
