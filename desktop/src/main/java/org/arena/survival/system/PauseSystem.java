package org.arena.survival.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controller;

/**
 * PauseSystem handles the game's pause state.
 * <p>
 * It provides methods to toggle pause using keyboard input (ESC key) or a controller button.
 */
public class PauseSystem {

    /**
     * Checks and sets pause state using keyboard input.
     *
     * @param paused current pause state
     * @return true if the game should be paused, false otherwise
     */
    public boolean setPause(boolean paused){
        // Toggles pause on ESC key press
        return !paused && (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE));
    }

    /**
     * Checks and sets pause state using keyboard or controller input.
     *
     * @param paused current pause state
     * @param controller the controller to check input from
     * @return true if the game should be paused, false otherwise
     */
    public boolean setPauseWithController(boolean paused, Controller controller) {
        // Toggles pause on ESC key press or controller button 1 press
        return !paused && (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) || controller.getButton(1));
    }
}