package com.dumbpug.jfc8.components.textarea.history;

import java.util.Stack;

/**
 * A stack of undo-able/redo-able operations.
 * @param <TUndoable> The undo-able type.
 */
public class UndoStack<TUndoable extends IUndoable> {
    /**
     * The item limit of undo-ables, or 0 if there is no limit.
     */
    private int limit;
    /**
     * The undo-ables list.
     */
    private Stack<TUndoable> undoStack = new Stack<TUndoable>();
    /**
     * The redo-ables list.
     */
    private Stack<TUndoable> redoStack = new Stack<TUndoable>();

    /**
     * Creates a new instance of the UndoStack class with an operations limit.
     * @param limit The limit of undo-able operations.
     */
    public UndoStack(int limit) {
        this.limit = limit;
    }

    /**
     * Creates a new instance of the UndoStack class without an operations limit.
     */
    public UndoStack() {
        this(0);
    }

    /**
     * Add an undo-able operation to the stack.
     * @param undoable The undo-able operation to add to the stack.
     */
    public void add(TUndoable undoable) {
        // Add the undoable to the top of the undo-able stack.
        undoStack.push(undoable);

        // Clear all of the redo operations.
        redoStack.clear();

        // Trim any unwanted undo-ables.
        while (this.limit > 0 && this.undoStack.size() > this.limit) {
            undoStack.remove(0);
        }
    }

    /**
     * Gets whether an undo operation can be done.
     * @return Whether an undo operation can be done.
     */
    public boolean canUndo() {
        return !this.undoStack.isEmpty();
    }

    /**
     * Gets whether a redo operation can be done.
     * @return Whether a redo operation can be done.
     */
    public boolean canRedo() {
        return !this.redoStack.isEmpty();
    }

    /**
     * Do the next undo operation.
     */
    public void undo() {
        if(!this.canUndo()){
            return;
        }

        // Get the next undo-able operation.
        TUndoable undoable = this.undoStack.pop();

        // Undo the operation.
        undoable.undo();

        // Push the undo-able onto the redo-able stack.
        this.redoStack.push(undoable);
    }

    /**
     * Do the next redo operation.
     */
    public void redo() {
        if(!this.canRedo()){
            return;
        }

        // Get the next redo-able operation.
        TUndoable redoable = this.redoStack.pop();

        // Redo the operation.
        redoable.redo();

        // Push the redo-able onto the undo-able stack.
        this.undoStack.push(redoable);
    }
}
