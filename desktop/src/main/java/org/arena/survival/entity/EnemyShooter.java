package org.arena.survival.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import org.arena.survival.assets.Assets;
import org.arena.survival.system.MapSystem;

/**
 * Represents a ranged-type enemy that moves towards the player and can shoot.
 * <p>
 * This enemy has a texture, movement speed, and rotation angle.
 * It updates its position each frame to follow the player and rotates to face the player.
 */
public class EnemyShooter extends Enemy {

    /** Texture used for rendering the enemy. */
    private Texture texture;

    /** Movement speed of the enemy in units per second. */
    private float speed;

    /** Rotation angle of the enemy in degrees. */
    private float rotation;

    /**
     * Constructs a new EnemyShooter at the specified coordinates.
     * The health is randomly initialized between 3 and 5, and speed between 10 and 70.
     *
     * @param x initial x-coordinate
     * @param y initial y-coordinate
     */
    public EnemyShooter(float x, float y) {
        super(x, y);
        super.setHealth(MathUtils.random(3, 5));
        speed = 10f + (float) Math.random() * (70f - 10f);
        texture = Assets.enemy;
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
    public void update(Player player, float delta, MapSystem mapSystem) {
        // reduce shooting timer
        super.setEnemyTimerCooldown(delta);

        // calculate vector towards player
        Vector2 direction = new Vector2(
                player.getCenterX() - super.getPosition().x,
                player.getCenterY() - super.getPosition().y
        );

        // normalize and move
        if (direction.len() > 0) {
            direction.nor();
        }

        float size = getSize();

        boolean moved = false;

        float moveX = direction.x * super.getSpeed() * delta;
        float moveY = direction.y * super.getSpeed() * delta;

        float newX = super.getPosition().x + moveX;
        float newY = super.getPosition().y + moveY;

        // 1️⃣ пробуем полное движение
        if (!isColliding(newX, newY, size, mapSystem)) {
            getPosition().set(newX, newY);
            moved = true;
        }

        // 2️⃣ пробуем только X
        if (!moved && !isColliding(newX, getPosition().y, size, mapSystem)) {
            getPosition().x = newX;
            moved = true;
        }

        // 3️⃣ пробуем только Y
        if (!moved && !isColliding(getPosition().x, newY, size, mapSystem)) {
            getPosition().y = newY;
        }

        // update rotation to face player
        float dx = player.getCenterX() - super.getPosition().x;
        float dy = player.getCenterY() - super.getPosition().y;
        rotation = MathUtils.atan2(dy, dx) * MathUtils.radiansToDegrees - 90;

        // update bounding box
        super.setBoundsPosition(super.getBounds().setPosition(super.getPosition().x, super.getPosition().y));
    }

    private boolean isColliding(float x, float y, float size, MapSystem mapSystem) {

        return mapSystem.isWall(x, y) ||
                mapSystem.isWall(x + size - 1, y) ||
                mapSystem.isWall(x, y + size - 1) ||
                mapSystem.isWall(x + size - 1, y + size - 1);
    }


}