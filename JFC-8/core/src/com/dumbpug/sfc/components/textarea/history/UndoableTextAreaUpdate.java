package com.dumbpug.sfc.components.textarea.history;

import com.dumbpug.sfc.components.textarea.history.Snapshot;
import com.dumbpug.sfc.components.textarea.TextArea;

/**
 * Represents an undo-able/redo-able text area update.
 */
public class UndoableTextAreaUpdate implements IUndoable {
    /**
     * The text area.
     */
    private com.dumbpug.sfc.components.textarea.TextArea textArea;
    /**
     * The text area snapshot pre-update.
     */
    private com.dumbpug.sfc.components.textarea.history.Snapshot preUpdateSnapshot;
    /**
     * The text area snapshot post-update.
     */
    private com.dumbpug.sfc.components.textarea.history.Snapshot postUpdateSnapshot;

    /**
     * Creates a new instance of the UndoableTextAreaUpdate class.
     * @param textArea The text area.
     * @param preUpdateSnapshot The text area snapshot pre-update.
     * @param postUpdateSnapshot The text area snapshot post-update.
     */
    public UndoableTextAreaUpdate(TextArea textArea, com.dumbpug.sfc.components.textarea.history.Snapshot preUpdateSnapshot, Snapshot postUpdateSnapshot) {
        this.textArea           = textArea;
        this.preUpdateSnapshot  = preUpdateSnapshot;
        this.postUpdateSnapshot = postUpdateSnapshot;
    }

    @Override
    public void undo() {
        this.textArea.applySnapshot(this.preUpdateSnapshot);
    }

    @Override
    public void redo() {
        this.textArea.applySnapshot(this.postUpdateSnapshot);
    }
}
