package org.arena.survival.system;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import org.arena.survival.entity.Bullet;

/**
 * Manages bullets fired by enemies.
 * <p>
 * Responsibilities include:
 * <ul>
 *     <li>Storing all active enemy bullets</li>
 *     <li>Updating bullet positions</li>
 *     <li>Rendering bullets with a ShapeRenderer</li>
 * </ul>
 */
public class EnemyBulletSystem {

    /** List of active enemy bullets. */
    private final Array<Bullet> bullets = new Array<>();

    /**
     * Adds a new bullet to the enemy bullet system.
     *
     * @param bullet the Bullet instance to add
     */
    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
    }

    /**
     * Updates all bullets in the system.
     * <p>
     * This moves each bullet according to its velocity and delta time.
     *
     * @param delta time elapsed since last frame (in seconds)
     */
    public void update(float delta, MapSystem mapSystem) {
        for (int i = bullets.size - 1; i >= 0; i--) {
            Bullet bullet = bullets.get(i);
            boolean destroy = bullet.updateEnemyBullet(delta, mapSystem);
            if (destroy) {
                bullets.removeIndex(i);
            }
        }
    }

    /**
     * Renders all bullets using the given ShapeRenderer.
     *
     * @param shapeRenderer the ShapeRenderer used to draw bullets
     */
    public void render(ShapeRenderer shapeRenderer) {
        for (Bullet bullet : bullets) {
            bullet.render(shapeRenderer);
        }
    }

    /**
     * Returns the list of active bullets.
     *
     * @return an Array containing all active bullets
     */
    public Array<Bullet> getBullets() {
        return bullets;
    }
}