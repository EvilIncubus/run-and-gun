package org.arena.survival.system;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import org.arena.survival.entity.Bullet;

/**
 * Manages all player bullets in the game.
 * <p>
 * Responsibilities include:
 * <ul>
 *     <li>Storing bullets</li>
 *     <li>Updating bullet logic each frame</li>
 *     <li>Rendering bullets using a {@link ShapeRenderer}</li>
 * </ul>
 */
public class BulletSystem {

    /** Array holding all active bullets */
    private final Array<Bullet> bullets = new Array<>();

    /**
     * Adds a bullet to the system.
     *
     * @param bullet the bullet to add
     */
    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
    }

    /**
     * Updates all bullets.
     * <p>
     * Calls {@link Bullet#update(float)} on each bullet.
     *
     * @param delta the time elapsed since the last frame in seconds
     */
    public void update(float delta) {
        for (Bullet bullet : bullets) {
            bullet.update(delta);
        }
    }

    /**
     * Renders all bullets using a {@link ShapeRenderer}.
     *
     * @param shapeRenderer the ShapeRenderer used to draw bullets
     */
    public void render(ShapeRenderer shapeRenderer) {
        for (Bullet bullet : bullets) {
            bullet.render(shapeRenderer);
        }
    }

    /**
     * Returns the array of active bullets.
     *
     * @return array of bullets
     */
    public Array<Bullet> getBullets() {
        return bullets;
    }
}