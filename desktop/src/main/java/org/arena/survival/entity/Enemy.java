package org.arena.survival.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import org.arena.survival.assets.Assets;

public abstract class Enemy {

    // позиция врага
    private Vector2 position;

    private int health; // количество жизней врага

    private float size = 40;

    private float shootCooldownEnemy = 1.0f;
    private float enemyTimer = 0f;

    // хитбокс для столкновений
    private Rectangle bounds;

    public Enemy(float x, float y) {
        position = new Vector2(x, y);
        health = MathUtils.random(5, 10);
        bounds = new Rectangle(position.x, position.y, size, size);
    }

    // обновление хитбокса (если враг будет двигаться)
    public abstract void update();
    public abstract void render(SpriteBatch batch);
    public abstract void update(Player player, float delta);

    /**
     * Уменьшает здоровье врага
     * @param damage количество урона
     * @return true если враг умер
     */
    public boolean takeDamage(int damage) {
        health -= damage;
        return health <= 0;
    }

    public Vector2 getPosition() {
        return position;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public float getSize() {
        return size;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBoundsPosition(Rectangle bounds) {
        this.bounds = bounds;
    }

    public float getCenterX() {
        return position.x + size / 2f;
    }

    public float getCenterY() {
        return position.y + size / 2f;
    }

    public float getEnemyTimer() {
        return enemyTimer;
    }

    public void setEnemyTimer(float enemyTimer) {
        this.enemyTimer = enemyTimer;
    }

    public void setEnemyTimerCooldown(float delta) {
        this.enemyTimer -= delta;
    }


    public float getShootCooldownEnemy() {
        return shootCooldownEnemy;
    }

    public void setShootCooldownEnemy(float shootCooldownEnemy) {
        this.shootCooldownEnemy = shootCooldownEnemy;
    }
}
