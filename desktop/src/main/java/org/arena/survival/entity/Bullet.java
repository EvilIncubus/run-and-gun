package org.arena.survival.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Класс пули.
 * Пуля имеет позицию и направление движения.
 */
public class Bullet {
    // текущая позиция пули
    private Vector2 position;

    // направление движения
    private Vector2 direction;

    // скорость пули
    private float speed = 600;

    // размер
    private float size = 6;

    private Rectangle bounds;

    /**
     * Конструктор пули
     *
     * @param startPosition позиция появления
     * @param direction направление движения
     */
    public Bullet(Vector2 startPosition, Vector2 direction) {

        // копируем позицию
        this.position = new Vector2(startPosition);

        // нормализуем направление (длина = 1)
        this.direction = new Vector2(direction).nor();

        bounds = new Rectangle(position.x - size/2, position.y - size/2, size, size);
    }

    /**
     * Отрисовка пули
     */
    public void render(ShapeRenderer shapeRenderer) {
        renderShape(shapeRenderer);
    }

    private void renderShape(ShapeRenderer shapeRenderer){
        // желтый квадрат
        shapeRenderer.setColor(Color.YELLOW);
        shapeRenderer.rect(
                position.x - size/2,
                position.y - size/2,
                size,
                size
        );
    }

    /**
     * Обновление логики пули
     */
    public void update(float delta) {
        movementUpdate(delta);

    }

    public void movementUpdate(float delta){
        // движение
        position.mulAdd(direction, speed * delta);
        bounds.setPosition(position.x - size/2, position.y - size/2);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    /**
     * пуля вышла за переделы
     */
    public boolean isOutOfBounds() {

        float worldSize = 2000;

        return position.x < -worldSize ||
                position.x > worldSize ||
                position.y < -worldSize ||
                position.y > worldSize;
    }
}
