package org.arena.survival.system;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import org.arena.survival.entity.Bullet;

public class EnemyBulletSystem {

    private final Array<Bullet> bullets = new Array<>();

    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
    }

    public void update(float delta) {
        for (Bullet bullet : bullets) {
            bullet.update(delta);
        }
    }

    public void render(ShapeRenderer shapeRenderer) {
        for (Bullet bullet : bullets) {
            bullet.render(shapeRenderer);
        }
    }

    public Array<Bullet> getBullets() {
        return bullets;
    }
}
