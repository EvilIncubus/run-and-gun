package org.arena.survival.system;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import org.arena.survival.assets.Assets;
import org.arena.survival.entity.Bullet;
import org.arena.survival.entity.Enemy;
import org.arena.survival.entity.Player;

/**
 * WeaponSystem manages shooting mechanics for both the Player and Enemy entities.
 * <p>
 * It handles bullet creation, shooting cooldowns, and plays shooting sounds.
 */
public class WeaponSystem {

    private final BulletSystem bulletSystem;
    private final EnemyBulletSystem enemyBulletSystem;
    private final Sound shootSound = Assets.shootSound;

    /**
     * Constructor for WeaponSystem.
     *
     * @param bulletSystem      system managing player bullets
     * @param enemyBulletSystem system managing enemy bullets
     */
    public WeaponSystem(BulletSystem bulletSystem, EnemyBulletSystem enemyBulletSystem) {
        this.bulletSystem = bulletSystem;
        this.enemyBulletSystem = enemyBulletSystem;
    }

    /**
     * Player shooting logic.
     * <p>
     * Spawns a new bullet in the given direction if the player's cooldown has elapsed,
     * then resets the cooldown timer and plays the shooting sound.
     *
     * @param player    the player who is shooting
     * @param direction the direction vector in which the bullet should move
     */
    public void shoot(Player player, Vector2 direction) {
        Vector2 spawn = new Vector2(player.getCenterX(), player.getCenterY());
        if (player.isDoubleShot()) {

            if (player.getPlayerTimer() <= 0) {
                // нормализованное направление движения
                Vector2 dirNorm = new Vector2(direction).nor();

                // перпендикуляр для смещения пуль
                Vector2 perp = new Vector2(-dirNorm.y, dirNorm.x);

                float offset = 10f; // расстояние между пулями

                // смещаем только позицию, направление одно и то же
                Vector2 spawn1 = new Vector2(spawn).add(new Vector2(perp).scl(offset));
                Vector2 spawn2 = new Vector2(spawn).add(new Vector2(perp).scl(-offset));

                bulletSystem.addBullet(
                        new Bullet(spawn1, direction, player.isHomingBullets())
                );

                bulletSystem.addBullet(
                        new Bullet(spawn2, direction, player.isHomingBullets())
                );
                shootSound.play(0.5f);
                player.setPlayerTimer(player.getShootCooldownPlayer());
            }

        } else {
        if (player.getPlayerTimer() <= 0) {
            bulletSystem.addBullet(new Bullet(spawn, direction.nor(), player.isHomingBullets()));
            shootSound.play(0.5f);
            player.setPlayerTimer(player.getShootCooldownPlayer());
        }
        }
    }

    /**
     * Enemy shooting logic.
     * <p>
     * Spawns a new bullet from the enemy's position if the enemy's cooldown has elapsed,
     * sets the bullet speed, adds it to the enemy bullet system, and plays the shooting sound.
     *
     * @param enemyShooter the enemy shooting the bullet
     * @param direction    the direction vector in which the bullet should move
     */
    public void shoot(Enemy enemyShooter, Vector2 direction) {
        if (enemyShooter.getEnemyTimer() <= 0) {
            Vector2 spawn = new Vector2(enemyShooter.getCenterX(), enemyShooter.getCenterY());
            Bullet bullet = new Bullet(spawn, direction, false);
            bullet.setSpeed(200);
            enemyBulletSystem.addBullet(bullet);
            shootSound.play(0.5f);
            enemyShooter.setEnemyTimer(enemyShooter.getShootCooldownEnemy());
        }
    }
}