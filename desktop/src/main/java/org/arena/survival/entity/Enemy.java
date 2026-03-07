package org.arena.survival.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import org.arena.survival.assets.Assets;

/**
 * Abstract base class representing an enemy in the game.
 * <p>
 * Each enemy has a position, health, size, shooting cooldown, and a bounding box for collisions.
 * Subclasses must implement rendering and update logic.
 */
public abstract class Enemy {

    /** Position of the enemy in the game world. */
    private Vector2 position;

    /** Current health points of the enemy. */
    private int health;

    /** Size of the enemy (width and height). */
    private float size = 40;

    /** Time between shots in seconds. */
    private float shootCooldownEnemy = 1.0f;

    /** Timer for shooting or other timed actions. */
    private float enemyTimer = 0f;

    /** Bounding rectangle used for collision detection. */
    private Rectangle bounds;

    /**
     * Constructs a new enemy at the given position.
     * Health is randomly initialized between 5 and 10.
     *
     * @param x the x-coordinate of the enemy's initial position
     * @param y the y-coordinate of the enemy's initial position
     */
    public Enemy(float x, float y) {
        position = new Vector2(x, y);
        health = MathUtils.random(5, 10);
        bounds = new Rectangle(position.x, position.y, size, size);
    }

    /**
     * Updates the enemy state each frame. Must be implemented by subclasses.
     */
    public abstract void update();

    /**
     * Renders the enemy using the provided SpriteBatch. Must be implemented by subclasses.
     *
     * @param batch the SpriteBatch used for rendering
     */
    public abstract void render(SpriteBatch batch);

    /**
     * Updates the enemy with respect to the player and delta time. Must be implemented by subclasses.
     *
     * @param player the player object
     * @param delta  the time elapsed since the last frame in seconds
     */
    public abstract void update(Player player, float delta);

    /**
     * Reduces the enemy's health by the specified damage.
     *
     * @param damage the amount of damage to inflict
     * @return true if the enemy's health drops to zero or below (enemy dies)
     */
    public boolean takeDamage(int damage) {
        health -= damage;
        return health <= 0;
    }

    /**
     * Returns the current position of the enemy.
     *
     * @return the position vector
     */
    public Vector2 getPosition() {
        return position;
    }

    /**
     * Returns the current health of the enemy.
     *
     * @return health points
     */
    public int getHealth() {
        return health;
    }

    /**
     * Sets the health of the enemy.
     *
     * @param health the new health value
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Returns the size of the enemy.
     *
     * @return size in units
     */
    public float getSize() {
        return size;
    }

    /**
     * Returns the bounding rectangle of the enemy for collision detection.
     *
     * @return bounding rectangle
     */
    public Rectangle getBounds() {
        return bounds;
    }

    /**
     * Sets the bounding rectangle of the enemy.
     *
     * @param bounds the new bounding rectangle
     */
    public void setBoundsPosition(Rectangle bounds) {
        this.bounds = bounds;
    }

    /**
     * Returns the x-coordinate of the enemy's center.
     *
     * @return center x-coordinate
     */
    public float getCenterX() {
        return position.x + size / 2f;
    }

    /**
     * Returns the y-coordinate of the enemy's center.
     *
     * @return center y-coordinate
     */
    public float getCenterY() {
        return position.y + size / 2f;
    }

    /**
     * Returns the enemy's internal timer.
     *
     * @return timer value in seconds
     */
    public float getEnemyTimer() {
        return enemyTimer;
    }

    /**
     * Sets the enemy's internal timer.
     *
     * @param enemyTimer new timer value
     */
    public void setEnemyTimer(float enemyTimer) {
        this.enemyTimer = enemyTimer;
    }

    /**
     * Reduces the enemy timer by delta seconds.
     *
     * @param delta time in seconds to reduce
     */
    public void setEnemyTimerCooldown(float delta) {
        this.enemyTimer -= delta;
    }

    /**
     * Returns the enemy's shooting cooldown.
     *
     * @return shooting cooldown in seconds
     */
    public float getShootCooldownEnemy() {
        return shootCooldownEnemy;
    }

    /**
     * Sets the shooting cooldown of the enemy.
     *
     * @param shootCooldownEnemy cooldown in seconds
     */
    public void setShootCooldownEnemy(float shootCooldownEnemy) {
        this.shootCooldownEnemy = shootCooldownEnemy;
    }
}