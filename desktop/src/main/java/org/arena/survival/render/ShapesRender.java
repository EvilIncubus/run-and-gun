package org.arena.survival.render;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import org.arena.survival.entity.Enemy;
import org.arena.survival.entity.Player;
import org.arena.survival.system.BulletSystem;
import org.arena.survival.system.EnemyBulletSystem;
import org.arena.survival.system.HUDSystem;

/**
 * Responsible for rendering all primitive shapes in the game using {@link ShapeRenderer}.
 * <p>
 * This renderer is used for:
 * <ul>
 *     <li>Player and enemy bullets</li>
 *     <li>Enemy health bars</li>
 *     <li>HUD elements such as the player's health</li>
 * </ul>
 * It operates in world coordinates using the provided {@link OrthographicCamera}.
 */
public class ShapesRender {

    /** Renderer used for drawing primitive shapes. */
    private final ShapeRenderer shapeRenderer;

    /** System responsible for rendering player bullets. */
    private final BulletSystem bulletSystem;

    /** System responsible for rendering enemy bullets. */
    private final EnemyBulletSystem enemyBulletSystem;

    /** HUD rendering system (health bars, UI elements). */
    private final HUDSystem hudSystem;

    /**
     * Creates a new {@code ShapesRender} instance and initializes
     * the required rendering systems.
     */
    public ShapesRender(BulletSystem bulletSystem, EnemyBulletSystem enemyBulletSystem) {
        this.shapeRenderer = new ShapeRenderer();
        this.bulletSystem = bulletSystem;
        this.enemyBulletSystem = enemyBulletSystem;
        this.hudSystem = new HUDSystem();
    }

    /**
     * Updates the projection matrix of the {@link ShapeRenderer}
     * using the given camera.
     *
     * @param orthographicCamera the camera used to project world coordinates
     */
    public void render(OrthographicCamera orthographicCamera) {
        shapeRenderer.setProjectionMatrix(orthographicCamera.combined);
    }

    /**
     * Renders all game shapes including bullets, HUD elements,
     * and enemy health bars.
     *
     * @param enemies list of all enemies in the game world
     * @param worldHeight height of the world used for HUD positioning
     * @param player the player entity whose HUD information will be displayed
     */
    public void allShapesRender(Array<Enemy> enemies, float worldHeight, Player player) {

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Render bullets
        bulletSystem.render(shapeRenderer);
        enemyBulletSystem.render(shapeRenderer);

        // Render player HUD
        hudSystem.renderHUDHealth(shapeRenderer, worldHeight, player);

        // Render enemy health bars
        for (Enemy enemy : enemies) {
            hudSystem.renderHUDHealth(shapeRenderer, enemy);
        }

        shapeRenderer.end();
    }

    /**
     * Disposes of the {@link ShapeRenderer} and frees GPU resources.
     * This should be called when the renderer is no longer needed.
     */
    public void dispose() {
        shapeRenderer.dispose();
    }
}
