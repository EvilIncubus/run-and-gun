package org.arena.survival.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import org.arena.survival.system.MapSystem;

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
    private float size = 3;

    private boolean homing;

    /** Bounding rectangle for collision detection. */
    private Rectangle bounds;

    /**
     * Constructs a new Bullet.
     *
     * @param startPosition the initial position of the bullet
     * @param direction the movement direction; will be normalized internally
     */
    public Bullet(Vector2 startPosition, Vector2 direction, boolean homing) {
        this.position = new Vector2(startPosition);
        this.direction = new Vector2(direction).nor();
        this.homing = homing;
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
        // Длина пули (можно масштабировать под скорость)
        float lengthFactor = 10f; // подбираешь по вкусу
        float bulletWidth = size; // толщина пули

        // start = текущая позиция пули
        Vector2 start = new Vector2(position);

        // end = позиция + direction * length
        Vector2 end = new Vector2(direction).scl(speed / 100f * lengthFactor).add(start);

        shapeRenderer.rectLine(start, end, bulletWidth);
    }

    /**
     * Updates the bullet logic, including movement.
     *
     * @param delta the time elapsed since the last frame in seconds
     */
    public boolean update(float delta, Array<Enemy> enemies, MapSystem mapSystem) {
        movementUpdate(delta, enemies);

        if (bulletHitsWall(mapSystem)) {
            return true; // пулю нужно удалить
        }

        return false;
    }

    private boolean bulletHitsWall(MapSystem mapSystem) {

        float r = size/2;

        return mapSystem.isWall(position.x - r, position.y - r) ||
                mapSystem.isWall(position.x + r, position.y - r) ||
                mapSystem.isWall(position.x - r, position.y + r) ||
                mapSystem.isWall(position.x + r, position.y + r);
    }

    /**
     * Updates the bullet logic, including movement.
     *
     * @param delta the time elapsed since the last frame in seconds
     */
    public boolean updateEnemyBullet(float delta, MapSystem mapSystem) {
        position.mulAdd(direction, speed * delta);
        bounds.setPosition(position.x - size / 2, position.y - size / 2);
        if (mapSystem.isWall(position.x, position.y)) {
            return true; // пулю нужно удалить
        }

        return false;
    }



    /**
     * Moves the bullet based on its direction and speed.
     * Updates the bounding rectangle position accordingly.
     *
     * @param delta the time elapsed since the last frame in seconds
     */
    public void movementUpdate(float delta, Array<Enemy> enemies) {
        if (homing && enemies.size > 0) {

            Enemy closest = null;
            float minDist = Float.MAX_VALUE;

            for (Enemy enemy : enemies) {

                float dist = position.dst(enemy.getPosition());

                if (dist < minDist) {
                    minDist = dist;
                    closest = enemy;
                }
            }

            if (closest != null) {

                Vector2 targetDir = new Vector2(
                        closest.getPosition()
                ).sub(position).nor();

                direction.lerp(targetDir, 0.05f);
            }
        }
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
    public boolean isOutOfBounds(float worldWidth, float worldHeight) {
        return position.x < 0 ||
                position.x > worldWidth ||
                position.y < 0 ||
                position.y > worldHeight;
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