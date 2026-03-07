package org.arena.survival.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Represents a bullet in the game.
 * <p>
 * Each bullet has a position, a movement direction, speed, and size.
 * It can be rendered using a {@link ShapeRenderer} and updated each frame.
 */
public class Bullet {

    /** Current position of the bullet. */
    private Vector2 position;

    /** Normalized movement direction of the bullet. */
    private Vector2 direction;

    /** Movement speed of the bullet in units per second. */
    private float speed = 1000;

    /** Size of the bullet (width and height). */
    private float size = 6;

    /** Bounding rectangle for collision detection. */
    private Rectangle bounds;

    /**
     * Constructs a new Bullet.
     *
     * @param startPosition the initial position of the bullet
     * @param direction the movement direction; will be normalized internally
     */
    public Bullet(Vector2 startPosition, Vector2 direction) {
        this.position = new Vector2(startPosition);
        this.direction = new Vector2(direction).nor();
        this.bounds = new Rectangle(position.x - size / 2, position.y - size / 2, size, size);
    }

    /**
     * Renders the bullet using the provided {@link ShapeRenderer}.
     * The bullet is drawn as a yellow square.
     *
     * @param shapeRenderer the ShapeRenderer to use for drawing
     */
    public void render(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.YELLOW);
        shapeRenderer.rect(
                position.x - size / 2,
                position.y - size / 2,
                size,
                size
        );
    }

    /**
     * Updates the bullet logic, including movement.
     *
     * @param delta the time elapsed since the last frame in seconds
     */
    public void update(float delta) {
        movementUpdate(delta);
    }

    /**
     * Moves the bullet based on its direction and speed.
     * Updates the bounding rectangle position accordingly.
     *
     * @param delta the time elapsed since the last frame in seconds
     */
    public void movementUpdate(float delta) {
        position.mulAdd(direction, speed * delta);
        bounds.setPosition(position.x - size / 2, position.y - size / 2);
    }

    /**
     * Returns the bounding rectangle of the bullet for collision detection.
     *
     * @return the bounding {@link Rectangle}
     */
    public Rectangle getBounds() {
        return bounds;
    }

    /**
     * Checks whether the bullet has left the predefined world boundaries.
     *
     * @return true if the bullet is outside the world limits, false otherwise
     */
    public boolean isOutOfBounds() {
        float worldSize = 2000;
        return position.x < -worldSize ||
                position.x > worldSize ||
                position.y < -worldSize ||
                position.y > worldSize;
    }

    /**
     * Sets the movement speed of the bullet.
     *
     * @param speed the speed in units per second
     */
    public void setSpeed(float speed) {
        this.speed = speed;
    }
}