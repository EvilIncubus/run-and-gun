package org.arena.survival.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Array;
import org.arena.survival.ArenaGame;
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

    private final PauseSystem pauseSystem = new PauseSystem();
    private final BulletSystem bulletSystem;
    private final EnemyBulletSystem enemyBulletSystem;

    private final Player player;
    private final ShapesRender shapesRender;
    private final BatchRender batchRender;
    private final GameLogicSystem gameLogicSystem;

    private final Array<Enemy> enemies;

    private final float worldWidth = 1920;
    private final float worldHeight = 1200;

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

        // Camera setup
        camera = new OrthographicCamera();
        camera.setToOrtho(false, worldWidth, worldHeight);

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
        enemies.add(new EnemyMelee(1000, 400));
        enemies.add(new EnemyShooter(400, 500));
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

        // Render world and HUD
        batchRender.allBatchRender(enemies, player, screenChanging, paused, endGame, winGame,
                worldHeight, worldWidth, waveNumber, maxWaves, score);

        // Update game logic and render shapes if not paused or ended
        if (!paused && !endGame && !winGame && !upgradeActive) {
            gameLogicSystem.update(delta, player, enemies, game);
            shapesRender.allShapesRender(enemies, worldHeight, player);
        }

        if (upgradeActive) {
            gameLogicSystem.handleUpgradeSelection(player);
            batchRender.renderUpgradeCards(
                    gameLogicSystem.getCurrentCards()
            );
        }

        // Pause menu handling
        if (paused || endGame) {
            handlePauseInput();
        }

        // Check if player died
        if (player.getHealth() <= 0) {
            endGame = true;
        }

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
}