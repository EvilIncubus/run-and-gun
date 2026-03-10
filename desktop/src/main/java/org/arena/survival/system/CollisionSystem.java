package org.arena.survival.system;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import org.arena.survival.entity.Bullet;
import org.arena.survival.entity.Enemy;
import org.arena.survival.entity.Player;

import java.util.Iterator;
import java.util.Random;

/**
 * Handles all collision logic in the game.
 * <p>
 * Responsibilities include:
 * <ul>
 *     <li>Detecting collisions between player bullets and enemies</li>
 *     <li>Applying damage to enemies</li>
 *     <li>Removing bullets that go out of bounds</li>
 *     <li>Detecting collisions between enemy bullets and the player</li>
 *     <li>Updating player health and game state based on collisions</li>
 * </ul>
 */
public class CollisionSystem {

    /**
     * Updates all collisions between player bullets and enemies.
     * <p>
     * Each bullet is moved according to its update logic. If it overlaps an enemy,
     * it deals damage and may remove the enemy if its health reaches zero.
     * Bullets that go out of bounds are also removed.
     *
     * @param bullets array of player bullets
     * @param enemies array of active enemies
     * @param delta time elapsed since last frame (seconds)
     * @param score current player score
     * @return updated score after handling collisions
     */
    public int update(Array<Bullet> bullets, Array<Enemy> enemies, float delta, int score, Player player) {
        Iterator<Bullet> bulletIter = bullets.iterator();
        while (bulletIter.hasNext()) {
            Bullet bullet = bulletIter.next();
            bullet.update(delta, enemies);

            // check collision with enemies
            for (Iterator<Enemy> enemyIter = enemies.iterator(); enemyIter.hasNext(); ) {
                Enemy enemy = enemyIter.next();
                if (bullet.getBounds().overlaps(enemy.getBounds())) {
                    bulletIter.remove();

                    // apply damage to enemy
                    boolean dead = enemy.takeDamage(player.getDamage());

                    if (dead) {
                        enemyIter.remove();
                        score += 1;
                    }
                    break; // one bullet hits only one enemy
                }
            }

            // remove bullets that go out of bounds
            if (bullet.isOutOfBounds()) {
                bulletIter.remove();
            }
        }
        return score;
    }

    /**
     * Checks collisions between enemy bullets and the player.
     * <p>
     * Each enemy bullet is updated, and if it overlaps the player,
     * the player takes damage. Bullets are removed if they hit the player
     * or go out of bounds.
     *
     * @param enemyBullets array of bullets fired by enemies
     * @param player the player entity
     * @param delta time elapsed since last frame (seconds)
     */
    public void checkEnemyBullets(Array<Bullet> enemyBullets, Player player, float delta) {

        Iterator<Bullet> bulletIter = enemyBullets.iterator();
        Rectangle playerBounds = new Rectangle(
                player.getCenterX() - player.getSize() / 2,
                player.getCenterY() - player.getSize() / 2,
                player.getSize(),
                player.getSize()
        );

        while (bulletIter.hasNext()) {
            Bullet bullet = bulletIter.next();

            // update bullet movement
            bullet.updateEnemyBullet(delta);

            // check collision with player
            if (bullet.getBounds().overlaps(playerBounds)) {
                player.takeDamage(1);

                if (player.getHealth() <= 0) {
                    return; // player dead, stop further processing
                }

                bulletIter.remove();
                continue;
            }

            // remove bullets that go out of bounds
            if (bullet.isOutOfBounds()) {
                bulletIter.remove();
            }
        }
    }
}