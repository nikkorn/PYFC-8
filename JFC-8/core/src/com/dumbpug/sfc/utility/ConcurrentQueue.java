package com.dumbpug.sfc.utility;

/**
 * A thread-safe variation of the Queue class.
 * @param <TItem>
 */
public class ConcurrentQueue<TItem> extends Queue<TItem> {
    /**
     * Get whether the queue contains an item.
     * @return Whether the queue contains an item.
     */
    public boolean hasNext() {
        synchronized (this.items) {
            return super.hasNext();
        }
    }

    /**
     * Get the size of the queue.
     * @return The size of the queue.
     */
    public int size() {
        synchronized (this.items) {
            return super.size();
        }
    }

    /**
     * Gets the next item from the queue.
     * @return The next item from the queue.
     */
    public TItem next() {
        synchronized (this.items) {
            return super.next();
        }
    }

    /**
     * Add an item to the end of the queue.
     * @param item The item to add.
     */
    public void add(TItem item) {
        synchronized (this.items) {
            super.add(item);
        }
    }

    /**
     * Add all items from another queue to the end of the queue.
     * @param queue The queue to pull items from.
     */
    public void add(Queue<TItem> queue) {
        synchronized (this.items) {
            super.add(queue);
        }
    }
}
