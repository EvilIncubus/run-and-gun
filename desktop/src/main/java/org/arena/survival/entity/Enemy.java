package org.arena.survival.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import org.arena.survival.assets.Assets;

import java.util.Random;

public class Enemy {

    // позиция врага
    private Vector2 position;

    // размер
    private float size = 40;

    private float rotation;

    // хитбокс для столкновений
    private Rectangle bounds;

    private int health = MathUtils.random(3, 15); // количество жизней врага

    private Texture texture;

    private SpriteBatch batch;

    private float speed = 100f + (float) Math.random() * (250f - 100f);

    public Enemy(float x, float y, SpriteBatch batch) {
        position = new Vector2(x, y);
        bounds = new Rectangle(position.x, position.y, size, size);
        this.batch = batch;
        texture = Assets.enemy;
    }

    // обновление хитбокса (если враг будет двигаться)
    public void update() {
        bounds.setPosition(position.x, position.y);
    }

    public void render() {
        batch.begin();

        batch.draw(texture,
                position.x,
                position.y,
                size / 2f,
                size / 2f,
                size,
                size,
                1f,
                1f,
                rotation,
                0,
                0,
                texture.getWidth(),
                texture.getHeight(),
                false,
                false);

        batch.end();
    }

    private void renderEnemy(){

    }

    public void update(Player player, float delta) {
        // скорость движения врага


        // вектор к игроку
        Vector2 direction = new Vector2(
                player.getCenterX() - position.x,
                player.getCenterY() - position.y
        );

        // нормализация
        if (direction.len() > 0) {
            direction.nor();
            position.mulAdd(direction, speed * delta);
        }

        float dx = player.getCenterX() - position.x;
        float dy = player.getCenterY() - position.y;

        rotation = MathUtils.atan2(dy, dx) * MathUtils.radiansToDegrees;
        rotation -= 90;


        // обновляем хитбокс
        bounds.setPosition(position.x, position.y);
    }

    /**
     * Уменьшает здоровье врага
     * @param damage количество урона
     * @return true если враг умер
     */
    public boolean takeDamage(int damage) {
        health -= damage;
        return health <= 0;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public Vector2 getPosition() {
        return position;
    }

    public float getSize() {
        return size;
    }

    public int getHealth() {
        return health;
    }
}
