package com.dumbpug.jfc8.state;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;

/**
 * Manages the updating of and navigation between states.
 */
public class StateManager {
    /**
     * The current state that will be rendered.
     */
    private State currentState = null;
    /**
     * The list of available states.
     */
    private ArrayList<State> states = new ArrayList<State>();

    /**
     * Set the current state.
     * @param state The state to make the current one.
     */
    public void setCurrentState(State state){
        this.currentState = state;

        // Do any on-entry logic for the state that is being made the current one.
        state.onEntry(currentState);
    }

    /**
     * Set the current state.
     * @param state The name of the state to make the current one.
     */
    public void setCurrentState(String state){
        this.setCurrentState(this.getState(state));
    }

    /**
     * Get the state with the provided state name.
     * @param stateName The name of the state.
     * @return state The state with the given name.
     */
    public State getState(String stateName){
        for(State state: states) {
            if(state.getStateName().equals(stateName)) {
                // We have found the state we want.
                return state;
            }
        }

        // We did not find a matching state.
        return null;
    }

    /**
     * Remove an active state, although this cannot be the current state.
     * @param stateName The name of the state to remove.
     */
    public void removeState(String stateName){
        // Get the state we want to remove.
        State stateToRemove = getState(stateName);

        // Were we able to find the state we want to delete?
        if(stateToRemove != null) {
            // Make sure that we are not removing the current state.
            if(stateToRemove == this.currentState) {
                throw new RuntimeException("error: cannot remove current state.");
            }

            // Remove the target state.
            states.remove(stateName);
        } else {
            throw new RuntimeException("error: cannot remove state '" + stateName + "' as is does not exist.");
        }
    }

    /**
     * Add a State if it is not already an active state.
     * @param state The state to add.
     */
    public void addState(State state){
        // Only add this state if it does not already exist in our states list.
        if(getState(state.getStateName()) == null) {
            states.add(state);
        }
    }

    /**
     * Updates the current state and handles any requests to change the current state.
     */
    public void update(){
        // There is nothing to do if there is no state to update.
        if (this.currentState == null) {
            throw new RuntimeException("error: no state to update.");
        }

        String targetStateName;

        // Check for a state change request
        if((targetStateName = currentState.getTargetStateName()) != null) {
            // A state change request has been made by the current state
            // Firstly, reset the change request in the current state, in
            // case we want to use it again at some point.
            currentState.changeState(null);

            // Next, get the state we want to make current.
            State targetState = getState(targetStateName);

            // Make sure that we actually got a state.
            if(targetState == null) {
                throw new RuntimeException("error: cannot change to state '" + targetStateName + "' as is does not exist.");
            }

            // Do any on-exit logic for the state that will stop being the current one.
            currentState.onExit();

            // Set the current state to be the target one.
            setCurrentState(targetState);
        }

        // Update the current state.
        currentState.update();
    }

    /**
     * Renders the current state
     * @param batch The sprite batch.
     */
    public void render(SpriteBatch batch) {
        // There is nothing to do if there is no state to render.
        if (this.currentState == null) {
            throw new RuntimeException("error: no state to render.");
        }

        // Render the current state.
        currentState.render(batch);
    }
}
