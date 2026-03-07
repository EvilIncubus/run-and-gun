package org.arena.survival.system;

import com.badlogic.gdx.math.Rectangle;
import org.arena.survival.ArenaGame;
import org.arena.survival.entity.Enemy;
import org.arena.survival.entity.Player;
import org.arena.survival.screen.MenuScreen;

public class EnemyAI {

    private final KnockbackSystem knockbackSystem = new KnockbackSystem();
    private float damageCooldown = 1f; // 1 секунда между уронами
    private float damageTimer = 0f;    // таймер для отслеживания кулдауна
    // таймер до следующего выстрела

    public void update(float delta) {
        if (damageTimer > 0) {
            damageTimer -= delta;
        }
    }

    public void enemyMovementAndKnockback(Enemy enemy, Player player, ArenaGame game, float knockbackStrength){
        Rectangle enemyBounds = enemy.getBounds();
        Rectangle playerBounds = new Rectangle(player.getCenterX() - player.getSize() / 2, player.getCenterY() - player.getSize() / 2, player.getSize(), player.getSize());

        if (enemyBounds.overlaps(playerBounds)) {
            if (damageTimer <= 0) {
                damageTimer = damageCooldown; // сброс таймера кулдауна
//            boolean isDead = player.takeDamageAndIsDead(1);
                player.damage(1);

                if (player.getHealth() <= 0) {
                    game.setScreen(new MenuScreen(game));
                }
            }
           knockbackSystem.fromEnemyToPlayer(enemy, player, knockbackStrength);

        }
    }

}
