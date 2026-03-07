package org.arena.survival.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import org.arena.survival.entity.Enemy;
import org.arena.survival.entity.Player;

public class KnockbackSystem {

    public void fromEnemyToPlayer(Enemy enemy, Player player, float knockbackStrength){
        // можно отталкивать врага или делать cooldown урона

        // вектор от врага к игроку
        Vector2 knockbackDir = new Vector2(
                player.getCenterX() - (enemy.getPosition().x + enemy.getSize() / 2f),
                player.getCenterY() - (enemy.getPosition().y + enemy.getSize() / 2f)
        );

        if (knockbackDir.len() > 0) {
            knockbackDir.nor();
            player.getPosition().mulAdd(knockbackDir, knockbackStrength * Gdx.graphics.getDeltaTime());
        }
    }
}
