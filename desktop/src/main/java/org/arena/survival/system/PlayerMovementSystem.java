package org.arena.survival.system;

import com.badlogic.gdx.math.Vector2;
import org.arena.survival.entity.Player;

/**
 * PlayerMovementSystem handles movement of the player within the game world.
 * <p>
 * It applies movement based on a direction vector and delta time,
 * and ensures the player remains within world bounds.
 */
public class PlayerMovementSystem {

    private float worldWidth;
    private float worldHeight;

    /**
     * Constructor for PlayerMovementSystem.
     *
     * @param worldWidth  width of the game world
     * @param worldHeight height of the game world
     */
    public PlayerMovementSystem(float worldWidth, float worldHeight) {
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
    }

    /**
     * Moves the player in the specified direction.
     * Normalizes the direction vector to maintain consistent speed in all directions.
     *
     * @param player    the player to move
     * @param direction movement direction vector
     * @param delta     time elapsed since last frame
     */
    public void move(Player player, Vector2 direction, float delta) {
        if (direction.len() > 0) {
            direction.nor();
            player.getPosition().mulAdd(direction, player.getSpeed() * delta);
        }

        clamp(player);
    }

    /**
     * Ensures the player remains within the bounds of the world.
     *
     * @param player the player whose position will be clamped
     */
    private void clamp(Player player) {
        float size = player.getSize();

        player.getPosition().x = Math.max(0, Math.min(worldWidth - size, player.getPosition().x));
        player.getPosition().y = Math.max(0, Math.min(worldHeight - size, player.getPosition().y));
    }
}