package org.arena.survival.system;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import org.arena.survival.assets.Assets;
import org.arena.survival.entity.Bullet;
import org.arena.survival.entity.Enemy;
import org.arena.survival.entity.EnemyShooter;
import org.arena.survival.entity.Player;

public class WeaponSystem {
    private float shootCooldown = 0.1f;
    private float timer = 0f;

    private final BulletSystem bulletSystem;
    private final EnemyBulletSystem enemyBulletSystem;
    private final Sound shootSound = Assets.shootSound;

    public WeaponSystem(BulletSystem bulletSystem, EnemyBulletSystem enemyBulletSystem) {
        this.bulletSystem = bulletSystem;
        this.enemyBulletSystem = enemyBulletSystem;
    }

    public void update(float delta) {
        timer -= delta;
    }

    public void shoot(Player player, Vector2 direction) {

        if (timer <= 0) {

            Vector2 spawn = new Vector2(
                    player.getCenterX(),
                    player.getCenterY()
            );

            bulletSystem.addBullet(
                    new Bullet(spawn, direction)
            );

            shootSound.play(0.5f);

            timer = shootCooldown;
        }
    }

    public void shoot(Enemy enemyShooter, Vector2 direction) {

        if (enemyShooter.getEnemyTimer() <= 0) {

            Vector2 spawn = new Vector2(
                    enemyShooter.getCenterX(),
                    enemyShooter.getCenterY()
            );

            enemyBulletSystem.addBullet(
                    new Bullet(spawn, direction)
            );

            shootSound.play(0.5f);

            enemyShooter.setEnemyTimer(enemyShooter.getShootCooldownEnemy());
        }
    }
}
