package org.arena.survival.render;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import org.arena.survival.assets.Assets;
import org.arena.survival.entity.Enemy;
import org.arena.survival.entity.Player;
import org.arena.survival.entity.UpgradeCard;
import org.arena.survival.system.HUDSystem;

/**
 * Responsible for rendering all sprite-based objects using {@link SpriteBatch}.
 * <p>
 * This renderer handles:
 * <ul>
 *     <li>Player rendering</li>
 *     <li>Enemy rendering</li>
 *     <li>HUD information (health, wave, score)</li>
 *     <li>Game state overlays (pause, game over, win)</li>
 * </ul>
 *
 * Rendering is performed in a single {@link SpriteBatch} pass to ensure
 * optimal GPU batching performance.
 */
public class BatchRender {

    /** Batch used to render sprites and text. */
    private final SpriteBatch batch;

    /** Font used for HUD and game state text rendering. */
    private final BitmapFont font;

    /** Renderer responsible for drawing the player sprite. */
    private final PlayerRenderer playerRenderer;

    /** System used for rendering HUD information. */
    private final HUDSystem hudSystem;

    /**
     * Creates a new {@code BatchRender} instance and initializes
     * all required rendering resources.
     */
    public BatchRender() {
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
        this.hudSystem = new HUDSystem();
        this.playerRenderer = new PlayerRenderer(Assets.player);

        // Increase font size for better readability
        font.getData().setScale(2f);
    }

    /**
     * Renders all batch-based objects in the game.
     *
     * @param enemies list of enemies to render
     * @param player player entity
     * @param screenChanging whether a screen transition is occurring
     * @param paused whether the game is paused
     * @param endGame whether the game has ended with a loss
     * @param winGame whether the game has been won
     * @param worldHeight height of the game world
     * @param worldWidth width of the game world
     * @param waveNumber current wave number
     * @param maxWaves total number of waves
     * @param score player score
     */
    public void allBatchRender(Array<Enemy> enemies, Player player, boolean screenChanging, boolean paused,
                               boolean endGame, boolean winGame, float worldHeight, float worldWidth,
                               float waveNumber, int maxWaves, int score) {

        // Skip rendering if screen is changing
        if (screenChanging) return;

        batch.begin();

        renderWorld(player, enemies);
        renderHUD(player, worldHeight, waveNumber, maxWaves, score);
        renderGameState(paused, endGame, winGame, worldWidth, worldHeight, score);

        batch.end();
    }

    /**
     * Updates the projection matrix used by the {@link SpriteBatch}.
     *
     * @param camera camera providing the combined projection matrix
     */
    public void render(OrthographicCamera camera) {
        batch.setProjectionMatrix(camera.combined);
    }

    public void renderUpgradeCards(Array<UpgradeCard> cards) {
        batch.begin();
        float cardWidth = 300;
        float cardHeight = 120;

        float startX = 500;
        float startY = 600;

        for (int i = 0; i < cards.size; i++) {

            UpgradeCard card = cards.get(i);

            float x = startX + i * (cardWidth + 40);

            font.draw(
                    batch,
                    "Press " + (i + 1),
                    x + 20,
                    startY + 90
            );

            font.draw(
                    batch,
                    card.getTitle(),
                    x + 20,
                    startY + 50
            );
        }
        batch.end();
    }

    /**
     * Disposes of rendering resources.
     * Should be called when the renderer is no longer needed.
     */
    public void dispose() {
        batch.dispose();
        font.dispose();
    }

    /**
     * Renders world entities such as the player and enemies.
     *
     * @param player player entity
     * @param enemies list of enemies
     */
    private void renderWorld(Player player, Array<Enemy> enemies) {

        playerRenderer.render(batch, player);

        for (Enemy enemy : enemies) {
            enemy.render(batch);
        }
    }

    /**
     * Renders HUD information including player stats, wave number and score.
     *
     * @param player player entity
     * @param worldHeight height of the game world
     * @param waveNumber current wave number
     * @param maxWaves maximum number of waves
     * @param score player score
     */
    private void renderHUD(Player player, float worldHeight, float waveNumber, int maxWaves, int score) {

        font.setColor(Color.WHITE);
        hudSystem.renderHUDInfo(
                font,
                batch,
                player,
                worldHeight,
                waveNumber,
                score,
                maxWaves
        );
    }

    /**
     * Renders game state overlays such as pause screen, game over or victory screen.
     *
     * @param paused whether the game is paused
     * @param endGame whether the player lost
     * @param winGame whether the player won
     * @param worldWidth width of the world
     * @param worldHeight height of the world
     * @param score player score
     */
    private void renderGameState(boolean paused, boolean endGame, boolean winGame,
                                 float worldWidth, float worldHeight, int score) {

        if (paused) {
            renderPause(worldWidth, worldHeight);
        }

        if (endGame) {
            renderGameOver(worldWidth, worldHeight, score);
        }

        if (winGame) {
            renderWin(worldWidth, worldHeight, score);
        }

    }

    /**
     * Renders the pause screen overlay.
     *
     * @param worldWidth width of the world
     * @param worldHeight height of the world
     */
    private void renderPause(float worldWidth, float worldHeight) {

        font.setColor(Color.WHITE);

        font.draw(batch, "PAUSED", worldWidth / 2f - 60, worldHeight / 2f + 20);
        font.draw(batch, "Press ENTER to resume", worldWidth / 2f - 140, worldHeight / 2f);
        font.draw(batch, "Press ESC to exit to menu", worldWidth / 2f - 160, worldHeight / 2f - 20);
    }

    /**
     * Renders the game over screen.
     *
     * @param worldWidth width of the world
     * @param worldHeight height of the world
     * @param score player score
     */
    private void renderGameOver(float worldWidth, float worldHeight, int score) {

        font.setColor(Color.RED);

        font.draw(batch, "Game Over", worldWidth / 2f - 60, worldHeight / 2f + 20);
        font.draw(batch, "Your score = " + score, worldWidth / 2f - 140, worldHeight / 2f);
        font.draw(batch, "Press ESC to exit to menu", worldWidth / 2f - 160, worldHeight / 2f - 20);
    }

    /**
     * Renders the victory screen.
     *
     * @param worldWidth width of the world
     * @param worldHeight height of the world
     * @param score player score
     */
    private void renderWin(float worldWidth, float worldHeight, int score) {

        font.setColor(Color.GREEN);

        font.draw(batch, "Game WIN!", worldWidth / 2f - 60, worldHeight / 2f + 20);
        font.draw(batch, "Your score = " + score, worldWidth / 2f - 140, worldHeight / 2f);
        font.draw(batch, "Press ESC to exit to menu", worldWidth / 2f - 160, worldHeight / 2f - 20);
    }
}