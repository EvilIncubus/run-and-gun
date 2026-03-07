package org.arena.survival.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import org.arena.survival.assets.Assets;

public class EnemyShooter extends Enemy{

    private Texture texture;

    private float speed;

    private float rotation;

    public EnemyShooter(float x, float y) {
        super(x, y);
        super.setHealth(MathUtils.random(3, 5));
        speed = 10f + (float) Math.random() * (70f - 10f);
        texture = Assets.enemy;
    }

    public void render(SpriteBatch batch) {

        batch.draw(texture,
                super.getPosition().x,
                super.getPosition().y,
                super.getSize() / 2f,
                super.getSize() / 2f,
                super.getSize(),
                super.getSize(),
                1f,
                1f,
                rotation,
                0,
                0,
                texture.getWidth(),
                texture.getHeight(),
                false,
                false);

    }

    public void update() {
        super.setBoundsPosition(super.getBounds().setPosition(super.getPosition().x, super.getPosition().y));
    }

    public void update(Player player, float delta) {
        super.setEnemyTimerCooldown(delta);

        // вектор к игроку
        Vector2 direction = new Vector2(
                player.getCenterX() - super.getPosition().x,
                player.getCenterY() - super.getPosition().y
        );

        // нормализация
        if (direction.len() > 0) {
            direction.nor();
            super.getPosition().mulAdd(direction, speed * delta);
        }

        float dx = player.getCenterX() - super.getPosition().x;
        float dy = player.getCenterY() - super.getPosition().y;

        rotation = MathUtils.atan2(dy, dx) * MathUtils.radiansToDegrees;
        rotation -= 90;


        // обновляем хитбокс
        super.setBoundsPosition(super.getBounds().setPosition(super.getPosition().x, super.getPosition().y));
    }
}
