package com.dumbpug.sfc.components.interactable;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * An interactable area that contains other interactables.
 */
public class InteractableArea extends Interactable {
    /**
     * The interactables nested within this interactable area.
     */
    private ArrayList<Interactable> children = new ArrayList<Interactable>();

    /**
     * Creates a new instance of the InteractableArea class.
     * @param x The x of the interactable.
     * @param y The y of the interactable.
     * @param width The width/height of the interactable.
     * @param height The width/height of the interactable.
     */
    public InteractableArea(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    /**
     * Add an interactable to this area.
     * @param interactable The interactable to add.
     */
    public void addInteractable(Interactable interactable) {
        if (!this.children.contains(interactable)) {
            this.children.add(interactable);
        }
    }

    @Override
    public boolean onClick(float x, float y) {
        // We will not be processing this click if the area is disabled or the click is outside the area.
        if (!this.isEnabled() || !this.isPositionInBounds(x, y)) {
            return false;
        }

        ArrayList<Interactable> candidates = new ArrayList<Interactable>();

        // Get all child interactables that are enabled and are in the click bounds.
        for (Interactable child : this.children) {
            if (child.isEnabled() && child.isPositionInBounds(x, y)) {
                candidates.add(child);
            }
        }

        // There is nothing to do if there are no interactables to handle the click.
        if (candidates.isEmpty()) {
            // The click wasn't handled by an interactable element in this area.
            return false;
        } else {
            // Sort the candidates based on the child layer.
            Collections.sort(candidates, new Comparator<Interactable>() {
                @Override
                public int compare(Interactable first, Interactable second) {
                    return first.getLayer() - second.getLayer();
                }
            });

            // Call onClick on the top-most interactable and return the result.
            return candidates.get(0).onClick(x, y);
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        // Draw all child interactables.
        for (Interactable child : this.children) {
            child.draw(batch);
        }
    }
}
