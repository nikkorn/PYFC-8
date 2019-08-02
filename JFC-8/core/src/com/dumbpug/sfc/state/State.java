package com.dumbpug.sfc.state;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Represents an application state.
 */
public abstract class State {
    /**
     * This will be set to a state name if this state wants the state manager to render a different state.
     */
    private String targetStateName = null;

    /**
     * This method can be called to set flags to tell the state manager that a new state should be loaded.
     * @param newStateName
     */
    public void changeState(String newStateName){
        targetStateName = newStateName;
    }

    /**
     * Called by the state manager before every render. null = no request, any other value will indicate a request and will be the name of the new state.
     * @return targetStateName The target state name.
     */
    public String getTargetStateName() {
        return targetStateName;
    }

    /**
     * Handle moving into this state.
     * @param state The previously active state.
     */
    public abstract void onEntry(State state);

    /**
     * Handle moving into this state.
     */
    public abstract void onExit();

    /**
     * Update the state.
     */
    public abstract void update();

    /**
     * Render the state.
     * @param batch The sprite batch.
     */
    public abstract void render(SpriteBatch batch);

    /**
     * Get the name of this state.
     * @return name The name of this state
     */
    public abstract String getStateName();
}
