package org.arena.survival.system;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import org.arena.survival.assets.Assets;
import org.arena.survival.entity.Bullet;
import org.arena.survival.entity.Player;

public class WeaponSystem {
    private float shootCooldown = 0.1f;
    private float timer = 0f;

    private final BulletSystem bulletSystem;
    private final Sound shootSound = Assets.shootSound;

    public WeaponSystem(BulletSystem bulletSystem) {
        this.bulletSystem = bulletSystem;
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
}
