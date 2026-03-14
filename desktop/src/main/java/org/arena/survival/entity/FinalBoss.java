package org.arena.survival.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import org.arena.survival.assets.Assets;
import org.arena.survival.system.MapSystem;

public class FinalBoss extends Enemy{

    /** Texture used for rendering the enemy. */
    private Texture texture;

    /** Movement speed of the enemy in units per second. */
    private float speed = 250;

    /** Rotation angle of the enemy in degrees. */
    private float rotation;

    public FinalBoss(float x, float y) {
        super(x, y);
        setHealth(1000);     // намного больше здоровья
        setMaxHealth(1000);
        texture = Assets.enemy;
    }

    @Override
    public void update(Player player, float delta, MapSystem mapSystem) {

        // Calculate vector to player
        Vector2 direction = new Vector2(
                player.getCenterX() - super.getPosition().x,
                player.getCenterY() - super.getPosition().y
        );

        // Normalize and move towards player
        if (direction.len() > 0) {
            direction.nor();
        }

        float size = getSize();

        boolean moved = false;

        float moveX = direction.x * super.getSpeed() * delta;
        float moveY = direction.y * super.getSpeed() * delta;

        float newX = super.getPosition().x + moveX;
        float newY = super.getPosition().y + moveY;

        // 1️⃣ пробуем полное движение
        if (!isColliding(newX, newY, size, mapSystem)) {
            getPosition().set(newX, newY);
            moved = true;
        }

        // 2️⃣ пробуем только X
        if (!moved && !isColliding(newX, getPosition().y, size, mapSystem)) {
            getPosition().x = newX;
            moved = true;
        }

        // 3️⃣ пробуем только Y
        if (!moved && !isColliding(getPosition().x, newY, size, mapSystem)) {
            getPosition().y = newY;
        }

        // Update rotation to face player
        float dx = player.getCenterX() - super.getPosition().x;
        float dy = player.getCenterY() - super.getPosition().y;
        rotation = MathUtils.atan2(dy, dx) * MathUtils.radiansToDegrees - 90;

        // Update bounding box position
        super.setBoundsPosition(super.getBounds().setPosition(super.getPosition().x, super.getPosition().y));
    }

    private boolean isColliding(float x, float y, float size, MapSystem mapSystem) {

        return mapSystem.isWall(x, y) ||
                mapSystem.isWall(x + size - 1, y) ||
                mapSystem.isWall(x, y + size - 1) ||
                mapSystem.isWall(x + size - 1, y + size - 1);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(
                texture,
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
                false
        );
    }

    @Override
    public void update() {
        super.setBoundsPosition(super.getBounds().setPosition(super.getPosition().x, super.getPosition().y));
    }

}
