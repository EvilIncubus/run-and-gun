package org.arena.survival.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import org.arena.survival.assets.Assets;

/**
 * Represents a melee-type enemy that moves directly towards the player.
 * <p>
 * This enemy has a texture, speed, and rotation angle.
 * It updates its position each frame to follow the player and rotates to face the player.
 */
public class EnemyMelee extends Enemy {

    /** Texture used for rendering the enemy. */
    private Texture texture;

    /** Rotation angle of the enemy in degrees. */
    private float rotation;

    /**
     * Constructs a new EnemyMelee at the specified coordinates.
     * The speed is randomly initialized between 100 and 250 units per second.
     *
     * @param x initial x-coordinate
     * @param y initial y-coordinate
     */
    public EnemyMelee(float x, float y) {
        super(x, y);
        this.texture = Assets.enemy;
        super.setSpeed(100f + (float) Math.random() * (250f - 100f));
    }

    /**
     * Renders the enemy using the provided SpriteBatch.
     * The enemy is drawn at its current position, rotated to face the player.
     *
     * @param batch the SpriteBatch used for rendering
     */
    @Override
    public void render(SpriteBatch batch) {
        batch.draw(
                texture,
                super.getPosition().x,
                super.getPosition().y,
                super.getSize() / 2f,
                super.getSize() / 2f,
                super.getSize(),
                super.getSize(),
                1f,
                1f,
                rotation,
                0,
                0,
                texture.getWidth(),
                texture.getHeight(),
                false,
                false
        );
    }

    /**
     * Updates the enemy's internal state.
     * Primarily used to update the bounding box position.
     */
    @Override
    public void update() {
        super.setBoundsPosition(super.getBounds().setPosition(super.getPosition().x, super.getPosition().y));
    }

    /**
     * Updates the enemy's movement and rotation towards the player.
     * <p>
     * Calculates the direction vector to the player, normalizes it,
     * moves the enemy by speed * delta in that direction, and updates rotation
     * to face the player. Also updates the bounding box.
     *
     * @param player the player to follow
     * @param delta  time elapsed since the last frame (in seconds)
     */
    @Override
    public void update(Player player, float delta) {

        // Calculate vector to player
        Vector2 direction = new Vector2(
                player.getCenterX() - super.getPosition().x,
                player.getCenterY() - super.getPosition().y
        );

        // Normalize and move towards player
        if (direction.len() > 0) {
            direction.nor();
            super.getPosition().mulAdd(direction, super.getSpeed() * delta);
        }

        // Update rotation to face player
        float dx = player.getCenterX() - super.getPosition().x;
        float dy = player.getCenterY() - super.getPosition().y;
        rotation = MathUtils.atan2(dy, dx) * MathUtils.radiansToDegrees - 90;

        // Update bounding box position
        super.setBoundsPosition(super.getBounds().setPosition(super.getPosition().x, super.getPosition().y));
    }
}