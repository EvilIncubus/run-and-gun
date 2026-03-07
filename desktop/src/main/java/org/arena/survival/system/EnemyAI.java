package org.arena.survival.system;

import com.badlogic.gdx.math.Rectangle;
import org.arena.survival.ArenaGame;
import org.arena.survival.entity.Enemy;
import org.arena.survival.entity.Player;

/**
 * Handles enemy AI behavior including movement, damage to the player, and knockback effects.
 * <p>
 * Responsibilities:
 * <ul>
 *     <li>Detect collisions between enemies and the player</li>
 *     <li>Apply damage to the player with a cooldown</li>
 *     <li>Apply knockback effects when enemies collide with the player</li>
 * </ul>
 */
public class EnemyAI {

    /** System that handles knockback effects between enemies and the player. */
    private final KnockbackSystem knockbackSystem = new KnockbackSystem();

    /** Cooldown duration (in seconds) between consecutive damages to the player. */
    private float damageCooldown = 1f;

    /** Timer to track time until next possible damage to the player. */
    private float damageTimer = 0f;

    /**
     * Updates the AI timers.
     * <p>
     * Currently only decrements the damage cooldown timer.
     *
     * @param delta time elapsed since last frame (seconds)
     */
    public void update(float delta) {
        if (damageTimer > 0) {
            damageTimer -= delta;
        }
    }

    /**
     * Handles enemy movement towards the player and applies knockback if colliding.
     * <p>
     * If the enemy collides with the player, damage is applied based on the cooldown timer,
     * and a knockback force is applied from the enemy to the player.
     *
     * @param enemy the enemy instance
     * @param player the player instance
     * @param game reference to the game (can be used for accessing game state)
     * @param knockbackStrength magnitude of knockback applied to the player
     */
    public void enemyMovementAndKnockback(Enemy enemy, Player player, ArenaGame game, float knockbackStrength) {

        Rectangle enemyBounds = enemy.getBounds();
        Rectangle playerBounds = new Rectangle(
                player.getCenterX() - player.getSize() / 2,
                player.getCenterY() - player.getSize() / 2,
                player.getSize(),
                player.getSize()
        );

        // If enemy collides with the player
        if (enemyBounds.overlaps(playerBounds)) {

            // Apply damage if cooldown timer allows
            if (damageTimer <= 0) {
                damageTimer = damageCooldown; // reset damage timer
                player.damage(1);

                if (player.getHealth() <= 0) {
                    return; // player is dead
                }
            }

            // Apply knockback effect from enemy to player
            knockbackSystem.fromEnemyToPlayer(enemy, player, knockbackStrength);
        }
    }
}