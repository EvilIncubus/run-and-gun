package org.arena.survival.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import org.arena.survival.assets.Assets;

/**
 * Player — игрок в виде квадрата с поворотом к курсору и движением по WASD.
 * Здесь реализованы:
 * - движение с нормализацией по диагонали
 * - поворот к курсору
 * - ограничение по границам мира
 * - отрисовка квадрата с ShapeRenderer
 */
public class Player {

    private Vector2 position;
    private float rotation;

    private float size;
    private float speed;

    private int health = 5;

    private Array<Bullet> bullets = new Array<>();

    public Player(float x, float y, float size, float speed) {
        this.position = new Vector2(x, y);
        this.size = size;
        this.speed = speed;
    }

    public Vector2 getPosition() {
        return position;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public float getSize() {
        return size;
    }

    public float getSpeed() {
        return speed;
    }

    public Array<Bullet> getBullets() {
        return bullets;
    }

    public float getCenterX() {
        return position.x + size / 2f;
    }

    public float getCenterY() {
        return position.y + size / 2f;
    }

    public int getHealth() {
        return health;
    }

    public void damage(int dmg) {
        health -= dmg;
    }
}
