package org.arena.survival.entity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Represents the player character in the game.
 * <p>
 * The player is represented as a square that can move and rotate toward the cursor
 * or joystick input. Features include:
 * <ul>
 *     <li>Movement with diagonal normalization</li>
 *     <li>Rotation to face cursor or target direction</li>
 *     <li>Boundary limits within the world</li>
 *     <li>Health management</li>
 *     <li>Shooting with cooldown</li>
 * </ul>
 */
public class Player {

    /** Current position of the player in world coordinates. */
    private Vector2 position;

    /** Current rotation of the player in degrees. */
    private float rotation;

    /** Size of the player square (width = height). */
    private float size;

    /** Movement speed in units per second. */
    private float speed;

    /** Current health of the player. */
    private int health = 5;

    /** Cooldown between shots in seconds. */
    private float shootCooldown = 0.5f;

    /** Timer to track shooting cooldown. */
    private float timer = 0f;

    private boolean doubleShot = false;

    private boolean homingBullets = false;

    private int damage = 10;

    /** Array of bullets shot by the player. */
    private Array<Bullet> bullets = new Array<>();

    /**
     * Constructs a new player at the given position with the specified size and speed.
     *
     * @param x horizontal position
     * @param y vertical position
     * @param size width and height of the player
     * @param speed movement speed of the player
     */
    public Player(float x, float y, float size, float speed) {
        this.position = new Vector2(x, y);
        this.size = size;
        this.speed = speed;
    }

    /**
     * Returns the current position of the player.
     *
     * @return position as a Vector2
     */
    public Vector2 getPosition() {
        return position;
    }

    /**
     * Returns the current rotation of the player in degrees.
     *
     * @return rotation in degrees
     */
    public float getRotation() {
        return rotation;
    }

    /**
     * Sets the player's rotation in degrees.
     *
     * @param rotation rotation angle
     */
    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    /**
     * Returns the size (width and height) of the player.
     *
     * @return size of the player
     */
    public float getSize() {
        return size;
    }

    /**
     * Returns the movement speed of the player.
     *
     * @return speed in units per second
     */
    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    /**
     * Returns the array of bullets shot by the player.
     *
     * @return Array of Bullet objects
     */
    public Array<Bullet> getBullets() {
        return bullets;
    }

    /**
     * Returns the X-coordinate of the center of the player.
     *
     * @return center X-coordinate
     */
    public float getCenterX() {
        return position.x + size / 2f;
    }

    /**
     * Returns the Y-coordinate of the center of the player.
     *
     * @return center Y-coordinate
     */
    public float getCenterY() {
        return position.y + size / 2f;
    }

    /**
     * Returns the current health of the player.
     *
     * @return health points
     */
    public int getHealth() {
        return health;
    }

    /**
     * Reduces the player's health by the specified damage amount.
     *
     * @param dmg damage to apply
     */
    public void damage(int dmg) {
        health -= dmg;
    }

    /**
     * Reduces the player's health by the specified damage amount.
     *
     * @param dmg damage to apply
     */
    public void takeDamage(int dmg) {
        health -= dmg;
    }

    /**
     * Increases the player's health by the given amount.
     *
     * @param extraHealth health points to add
     */
    public void setAddHealth(int extraHealth){
        health += extraHealth;
    }

    /**
     * Returns the current internal timer (used for shooting cooldown, etc.).
     *
     * @return timer value
     */
    public float getPlayerTimer() {
        return timer;
    }

    /**
     * Sets the internal timer value.
     *
     * @param timer new timer value
     */
    public void setPlayerTimer(float timer) {
        this.timer = timer;
    }

    /**
     * Returns the cooldown between player shots.
     *
     * @return shoot cooldown in seconds
     */
    public float getShootCooldownPlayer() {
        return shootCooldown;
    }

    /**
     * Sets the cooldown between player shots.
     *
     * @param shootCooldown new cooldown in seconds
     */
    public void setShootCooldownPlayer(float shootCooldown) {
        this.shootCooldown = shootCooldown;
    }

    /**
     * Updates the player's internal state.
     * <p>
     * Currently decreases the shooting timer by the delta time.
     *
     * @param delta time elapsed since last frame (in seconds)
     */
    public void update(float delta) {
        timer -= delta;
    }

    public boolean isDoubleShot() {
        return doubleShot;
    }

    public void setDoubleShot(boolean doubleShot) {
        this.doubleShot = doubleShot;
    }

    public boolean isHomingBullets() {
        return homingBullets;
    }

    public void setHomingBullets(boolean homingBullets) {
        this.homingBullets = homingBullets;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}