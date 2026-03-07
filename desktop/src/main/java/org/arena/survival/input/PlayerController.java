package org.arena.survival.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import org.arena.survival.entity.Player;

/**
 * Handles keyboard and mouse input for the player.
 * <p>
 * Provides methods for:
 * <ul>
 *     <li>Movement input (WASD)</li>
 *     <li>Mouse aiming direction</li>
 *     <li>Shooting input (left mouse button)</li>
 * </ul>
 * Integrates with an {@link OrthographicCamera} to convert screen coordinates to world coordinates.
 */
public class PlayerController {

    /** Camera used to convert screen coordinates to world coordinates. */
    private OrthographicCamera camera;

    /**
     * Constructs a PlayerController with a given camera.
     *
     * @param camera the camera used for converting screen coordinates
     */
    public PlayerController(OrthographicCamera camera) {
        this.camera = camera;
    }

    /**
     * Returns the movement input vector based on keyboard keys W, A, S, D.
     * <p>
     * The returned vector is not normalized.
     *
     * @return movement direction as a Vector2
     */
    public Vector2 movementInput() {

        float x = 0;
        float y = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.W)) y += 1;
        if (Gdx.input.isKeyPressed(Input.Keys.S)) y -= 1;
        if (Gdx.input.isKeyPressed(Input.Keys.A)) x -= 1;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) x += 1;

        return new Vector2(x, y);
    }

    /**
     * Calculates the direction vector from the player to the mouse cursor.
     * <p>
     * Converts the mouse screen coordinates to world coordinates using the camera.
     *
     * @param player the player entity
     * @return direction vector from player to mouse cursor
     */
    public Vector2 mouseDirection(Player player) {

        Vector3 mouse = new Vector3(
                Gdx.input.getX(),
                Gdx.input.getY(),
                0
        );

        camera.unproject(mouse);

        return new Vector2(
                mouse.x - player.getCenterX(),
                mouse.y - player.getCenterY()
        );
    }

    /**
     * Returns whether the shoot input is currently pressed.
     * <p>
     * Uses the left mouse button as the shooting input.
     *
     * @return true if the left mouse button is pressed
     */
    public boolean isShootPressed() {
        return Gdx.input.isButtonPressed(Input.Buttons.LEFT);
    }
}