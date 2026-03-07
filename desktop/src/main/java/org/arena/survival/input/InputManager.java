package org.arena.survival.input;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.math.Vector2;
import org.arena.survival.entity.Player;

/**
 * Manages player input from both keyboard/mouse and gamepad.
 * <p>
 * This class abstracts input handling so the game logic can retrieve:
 * <ul>
 *     <li>Movement direction</li>
 *     <li>Aiming direction</li>
 *     <li>Shoot action</li>
 * </ul>
 * without worrying about the input device.
 */
public class InputManager {

    /** Handles keyboard and mouse input. */
    private PlayerController keyboardController;

    /** Connected gamepad controller (if any). */
    private Controller gamepad;

    /** Flag indicating whether a gamepad is in use. */
    private boolean useGamepad;

    /**
     * Constructs an InputManager with a keyboard controller and optional gamepad.
     *
     * @param keyboardController controller for keyboard/mouse input
     * @param detectedGamepad detected gamepad controller, or null if none
     */
    public InputManager(PlayerController keyboardController, Controller detectedGamepad) {
        this.keyboardController = keyboardController;
        this.gamepad = detectedGamepad;
        this.useGamepad = detectedGamepad != null;
    }

    /**
     * Returns the movement direction vector for the player.
     * <p>
     * If a gamepad is connected, returns the left stick direction.
     * Otherwise, uses keyboard input (WASD or arrow keys).
     *
     * @return normalized movement direction as a Vector2
     */
    public Vector2 getMoveDirection() {
        if (useGamepad) {
            return getGamepadMove();
        } else {
            return keyboardController.movementInput();
        }
    }

    /**
     * Returns the aiming direction vector for the player.
     * <p>
     * If a gamepad is connected, returns the right stick direction.
     * Otherwise, calculates direction toward the mouse cursor.
     *
     * @param player the player entity
     * @return normalized aiming direction as a Vector2
     */
    public Vector2 getAimDirection(Player player) {
        if (useGamepad) {
            return getGamepadAim();
        } else {
            return keyboardController.mouseDirection(player);
        }
    }

    /**
     * Returns whether the shoot action is currently pressed.
     * <p>
     * Supports both gamepad button and keyboard/mouse input.
     *
     * @return true if the shoot input is active
     */
    public boolean isShootPressed() {
        if (useGamepad) {
            return getGamepadShoot();
        } else {
            return keyboardController.isShootPressed();
        }
    }

    /**
     * Reads the movement input from a connected gamepad (left stick).
     *
     * @return movement vector
     */
    private Vector2 getGamepadMove() {
        float x = gamepad.getAxis(0); // left stick X-axis
        float y = -gamepad.getAxis(1); // left stick Y-axis (inverted)
        return new Vector2(x, y);
    }

    /**
     * Reads the aiming input from a connected gamepad (right stick).
     *
     * @return aiming vector
     */
    private Vector2 getGamepadAim() {
        float x = gamepad.getAxis(2); // right stick X-axis
        float y = -gamepad.getAxis(3); // right stick Y-axis (inverted)
        if (x == 0 && y == 0) {
            return new Vector2(1, 0); // default direction if stick is idle
        }
        return new Vector2(x, y);
    }

    /**
     * Checks if the gamepad shoot button is pressed.
     *
     * @return true if the shoot button is pressed
     */
    private boolean getGamepadShoot() {
        return gamepad.getButton(0); // typically button A/X
    }
}