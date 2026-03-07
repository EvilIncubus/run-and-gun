package org.arena.survival.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import org.arena.survival.entity.Enemy;
import org.arena.survival.entity.Player;

/**
 * KnockbackSystem handles knockback effects between enemies and the player.
 * <p>
 * It calculates the direction from an enemy to the player and applies a displacement
 * to the player proportional to a specified knockback strength.
 */
public class KnockbackSystem {

    /**
     * Applies knockback to the player away from the enemy.
     *
     * @param enemy the Enemy causing the knockback
     * @param player the Player to be knocked back
     * @param knockbackStrength the strength of the knockback (distance per second)
     */
    public void fromEnemyToPlayer(Enemy enemy, Player player, float knockbackStrength){
        // Vector from enemy to player
        Vector2 knockbackDir = new Vector2(
                player.getCenterX() - (enemy.getPosition().x + enemy.getSize() / 2f),
                player.getCenterY() - (enemy.getPosition().y + enemy.getSize() / 2f)
        );

        // Apply knockback if vector is non-zero
        if (knockbackDir.len() > 0) {
            knockbackDir.nor();
            player.getPosition().mulAdd(knockbackDir, knockbackStrength * Gdx.graphics.getDeltaTime());
        }
    }
}