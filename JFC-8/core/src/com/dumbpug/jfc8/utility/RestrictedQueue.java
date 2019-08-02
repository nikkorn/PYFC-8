package com.dumbpug.jfc8.utility;

/**
 * A queue of items with a limited size.
 * @param <TItem> The item type.
 */
public class RestrictedQueue<TItem> extends Queue<TItem> {
    /**
     * The queue size limit.
     */
    private int limit;

    /**
     * Creates a new instance of the RestrictedQueue class.
     * @param limit The item size limit.
     */
    public RestrictedQueue(int limit) {
        this.limit = limit;
    }

    /**
     * Add an item to the end of the queue.
     * @param item The item to add.
     */
    @Override
    public void add(TItem item) {
        super.add(item);

        // Trim items from the end of the queue until the size limit is met.
        this.trim();
    }

    /**
     * Add all items from another queue to the end of the queue.
     * @param queue The queue to pull items from.
     */
    @Override
    public void add(Queue<TItem> queue) {
        super.add(queue);

        // Trim items from the end of the queue until the size limit is met.
        this.trim();
    }

    /**
     * Trim items from the end of the queue until the size limit is met.
     */
    private void trim() {
        while (this.items.size() > limit) {
            this.items.remove(this.items.size() - 1);
        }
    }
}
