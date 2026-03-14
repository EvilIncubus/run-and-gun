package org.arena.survival.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import org.arena.survival.ArenaGame;
import org.arena.survival.assets.Assets;
import org.arena.survival.entity.Enemy;
import org.arena.survival.entity.EnemyMelee;
import org.arena.survival.entity.EnemyShooter;
import org.arena.survival.entity.Player;
import org.arena.survival.render.BatchRender;
import org.arena.survival.render.ShapesRender;
import org.arena.survival.system.*;

/**
 * Main game screen. Handles camera, player, enemies, game logic, and rendering.
 */
public class GameScreen implements Screen {

    // --------------------- Fields ---------------------

    private final ArenaGame game;
    private final Controller controller;
    private final OrthographicCamera camera;
    private final OrthographicCamera hudCamera;

    private MapSystem mapSystem;

    private final PauseSystem pauseSystem = new PauseSystem();
    private final BulletSystem bulletSystem;
    private final EnemyBulletSystem enemyBulletSystem;

    private final Player player;
    private final ShapesRender shapesRender;
    private final BatchRender batchRender;
    private final GameLogicSystem gameLogicSystem;

    private final Array<Enemy> enemies;

    private final float cameraWidth = 1920;
    private final float cameraHeight = 1200;

    private final float worldWidth = 6200;
    private final float worldHeight = 6200;

    private int waveNumber = 1;
    private int score = 0;
    private final int maxWaves = 25;

    private boolean paused = false;
    private boolean screenChanging = false;
    private boolean endGame = false;
    private boolean winGame = false;
    private boolean upgradeActive = false;

    // --------------------- Constructor ---------------------

    /**
     * Initializes the GameScreen with the game instance and optional controller.
     *
     * @param game       The main ArenaGame instance
     * @param controller Optional controller for input
     */
    public GameScreen(ArenaGame game, Controller controller) {
        this.game = game;
        this.controller = controller;
        this.bulletSystem = new BulletSystem();
        this.enemyBulletSystem = new EnemyBulletSystem();

        TiledMap map = Assets.getMap();
        mapSystem = new MapSystem(map);

        // Camera setup
        camera = new OrthographicCamera();
        camera.setToOrtho(false, cameraWidth, cameraHeight);
        hudCamera = new OrthographicCamera();
        hudCamera.setToOrtho(false, 1920, 1200);

        // Renderer setup
        shapesRender = new ShapesRender(bulletSystem, enemyBulletSystem);
        batchRender = new BatchRender();

        // Game logic system
        gameLogicSystem = new GameLogicSystem(
                this,
                camera,
                controller,
                worldWidth,
                worldHeight,
                bulletSystem,
                enemyBulletSystem
        );

        // Player initialization
        player = new Player(worldWidth / 2f, worldHeight / 2f, 40, 300);

        // Enemy initialization
        enemies = new Array<>();
        float x = (float) Math.random() * worldWidth;
        float y = (float) Math.random() * worldHeight;
        enemies.add(new EnemyMelee(x, y));
        float x1 = (float) Math.random() * worldWidth;
        float y1 = (float) Math.random() * worldHeight;
        enemies.add(new EnemyShooter(x1, y1));
    }

    // --------------------- Screen Methods ---------------------

    /**
     * Called once when this screen is displayed. Initializes resources if needed.
     */
    @Override
    public void show() {
    }

    /**
     * Called every frame. Handles game logic, rendering, and pause.
     *
     * @param delta Time in seconds since the last frame
     */
    @Override
    public void render(float delta) {

        cleanScreen();
        setCamera();

        mapSystem.render(camera);

        // Render world and HUD
        batchRender.worldBatchRender(player, enemies, screenChanging, camera, worldWidth, worldHeight);
        batchRender.hudBatchRender(player, screenChanging, paused, endGame, winGame, waveNumber, maxWaves, score, hudCamera);

        // Update game logic and render shapes if not paused or ended
        if (!paused && !endGame && !winGame && !upgradeActive) {
            gameLogicSystem.update(delta, player, enemies, game, mapSystem);
            shapesRender.worldShapesRender(enemies, player, camera);
            shapesRender.hudShapesRender(player, hudCamera);
        }

        if (upgradeActive) {
            gameLogicSystem.handleUpgradeSelection(player);
            batchRender.renderUpgradeCards(
                    gameLogicSystem.getCurrentCards(),
                    hudCamera
            );
        }

        // Pause menu handling
        if (paused || endGame || winGame) {
            handlePauseInput();
        }

        // Check if player Win
        winVerify();
        // Check if player died
        deadVerify();
        // Set Pause
        setPauseGame();
    }

    /**
     * Called when screen size changes.
     *
     * @param width  New width
     * @param height New height
     */
    @Override
    public void resize(int width, int height) {
    }

    /**
     * Called when the game is paused.
     */
    @Override
    public void pause() {
    }

    /**
     * Called when the game is resumed from pause.
     */
    @Override
    public void resume() {
    }

    /**
     * Called when the screen is hidden.
     */
    @Override
    public void hide() {
    }

    /**
     * Called when disposing of the screen. Frees render resources.
     */
    @Override
    public void dispose() {
        shapesRender.dispose();
        batchRender.dispose();
    }

    // --------------------- Public Getters/Setters ---------------------

    public void setWinGame(boolean winGame) {
        this.winGame = winGame;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getWaveNumber() {
        return waveNumber;
    }

    public int getMaxWaves() {
        return maxWaves;
    }

    public void setWaveNumber(int waveNumber) {
        this.waveNumber = waveNumber;
    }

    public int getScore() {
        return score;
    }

    public float getWorldWidth() {
        return worldWidth;
    }

    public float getWorldHeight() {
        return worldHeight;
    }

    public boolean isUpgradeActive() {
        return upgradeActive;
    }

    public void setUpgradeActive(boolean upgradeActive) {
        this.upgradeActive = upgradeActive;
    }

    // --------------------- Private Helper Methods ---------------------

    /** Clears the screen with a dark grey background */
    private static void cleanScreen() {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    /** Updates camera and sets projection matrices for renderers */
    private void setCamera() {
        camera.position.set(
                player.getCenterX(),
                player.getCenterY(),
                0
        );
        camera.position.x = MathUtils.clamp(
                camera.position.x,
                cameraWidth / 2,
                worldWidth - cameraWidth / 2
        );

        camera.position.y = MathUtils.clamp(
                camera.position.y,
                cameraHeight / 2,
                worldHeight - cameraHeight / 2
        );
        camera.update();
        shapesRender.render(camera);
        batchRender.render(camera);
    }

    /** Handles pause/resume input */
    private void handlePauseInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) && paused) {
            paused = false;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            screenChanging = true;
            game.setScreen(new MenuScreen(game));
            paused = true;
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        }
    }

    /** Updates the pause flag based on controller or keyboard */
    private void setPauseGame() {
        if (controller != null) {
            paused = pauseSystem.setPauseWithController(paused, controller);
        } else {
            paused = pauseSystem.setPause(paused);
        }
    }

    /**
     * Verifies win conditions.
     *
     * <p>If the current wave number exceeds the maximum, the game returns to the main menu.</p>
     */
    private void winVerify() {
        if (waveNumber > maxWaves) {
            winGame = true;
        }
    }

    private void deadVerify() {
        if (player.getHealth() <= 0) {
            endGame = true;
        }
    }
}