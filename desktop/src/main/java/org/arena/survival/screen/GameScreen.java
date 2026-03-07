package org.arena.survival.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import org.arena.survival.ArenaGame;
import org.arena.survival.assets.Assets;
import org.arena.survival.entity.Enemy;
import org.arena.survival.entity.Player;
import org.arena.survival.input.PlayerController;
import org.arena.survival.render.PlayerRenderer;
import org.arena.survival.system.*;

/**
 * GameScreen теперь координатор: камера, игрок, рендер.
 */
public class GameScreen implements Screen {

    // --------------------- Поля ---------------------

    private final ArenaGame game;

    private Controller controller; // текущий джойстик

    /**
     * Камера для отображения мира
     */
    private final OrthographicCamera camera;

    private final CollisionSystem collisionSystem = new CollisionSystem();
    private final WaveSystem waveSystem = new WaveSystem();
    private final HUDSystem hudSystem = new HUDSystem();
    private final PauseSystem pauseSystem = new PauseSystem();
    private final EnemyAI enemyAI = new EnemyAI();

    /**
     * ShapeRenderer для отрисовки примитивов (квадрат игрока)
     */
    private final ShapeRenderer shapeRenderer;

    private final Player player;

    private PlayerController playerController;
    private PlayerMovementSystem movementSystem;
    private WeaponSystem weaponSystem;
    private BulletSystem bulletSystem;
    private PlayerRenderer playerRenderer;

    // массив врагов
    private final Array<Enemy> enemies;

    private final float worldWidth = 1920;
    private final float worldHeight = 1200;

    private int waveNumber = 1;             // текущая волна
    private float waveTimer = 0f;           // таймер до следующей волны
    private float waveDelay = 3f;           // пауза между волнами

    private int score = 0; // очки игрока
    private final int maxWaves = 25; // победа через 25 волн

    private final SpriteBatch batch;
    private final BitmapFont font;

    private boolean paused = false; // флаг паузы

    private boolean screenChanging = false;

    public GameScreen(ArenaGame game, Controller controller) {
        this.game = game;
        this.controller = controller;

        // Камера
        camera = new OrthographicCamera();
        camera.setToOrtho(false, worldWidth, worldHeight);

        // Рендерер
        shapeRenderer = new ShapeRenderer();

        batch = new SpriteBatch();
        font = new BitmapFont(); // стандартный шрифт LibGDX
        font.getData().setScale(2f); // увеличиваем шрифт

//        // Создаём игрока в центре экрана
//        player = new Player(
//                worldWidth / 2f,
//                worldHeight / 2f,
//                40f,   // размер
//                300f,  // скорость
//                camera,
//                shapeRenderer,
//                worldWidth,
//                worldHeight,
//                batch
//        );
//
//        player.setUsingController(controller != null);

        player = new Player(worldWidth/2, worldHeight/2,40,300);

        playerController = new PlayerController(camera);

        movementSystem = new PlayerMovementSystem(worldWidth, worldHeight);

        bulletSystem = new BulletSystem();

        weaponSystem = new WeaponSystem(bulletSystem);

        playerRenderer = new PlayerRenderer(Assets.player);

        // инициализация массива врагов
        enemies = new Array<>();
        enemies.add(new Enemy(1000, 400, batch));
    }

    // --------------------- Методы Screen ---------------------

    /**
     * show() вызывается один раз при отображении экрана.
     * Здесь создаются камера, ShapeRenderer и инициализируется игрок.
     */

    @Override
    public void show() {
    }

    /**
     * render() вызывается каждый кадр (~60 FPS)
     * delta — время с прошлого кадра в секундах
     */
    @Override
    public void render(float delta) {

        // ------------------ Очистка экрана ------------------

        cleanScreen();

        // ------------------ Камера ------------------

        setCamera();

        // ------------------ Рендер и логика ------------------

        allBatchRender();

        if (!paused) {
            logicRender(delta);
            allShapesRender();
        }

        // В паузе

        if (paused) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                paused = false; // снять паузу
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                screenChanging = true;
                game.setScreen(new MenuScreen(game)); // выйти в меню
                paused = true;
                // Очищаем цветовой буфер
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            }
        }

        // Установка Паузы
        if (!paused) {
            paused = pauseSystem.setPause(paused);
        }


    }

    private void logicRender(float delta) {
//        player.update(delta, controller);

        Vector2 moveDir = playerController.movementInput();

        movementSystem.move(player, moveDir, delta);

        Vector2 aimDir = playerController.mouseDirection(player);

        player.setRotation(aimDir.angleDeg());

        weaponSystem.update(delta);

        enemyAI.update(delta);

        bulletSystem.update(delta);

        if (playerController.isShootPressed()) {
            weaponSystem.shoot(player, aimDir);
        }

        score = collisionSystem.update(bulletSystem.getBullets(), enemies, delta, score);

        float knockbackStrength = 150; // сила отталкивания

        for (Enemy enemy : enemies) {
            enemy.update(player, Gdx.graphics.getDeltaTime());

            enemyAI.enemyMovementAndKnockback(
                    enemy,
                    player,
                    game,
                    knockbackStrength
            );
        }
    }

    private void allBatchRender() {
        if (screenChanging) return; // не рендерим, если экран меняется

        batch.begin();

        playerRenderer.render(batch, player);

        hudSystem.renderHUDInfo(font, batch, player, worldHeight, waveNumber, score, maxWaves);

        if (paused) {
            font.draw(batch, "PAUSED", worldWidth / 2f - 60, worldHeight / 2f + 20);
            font.draw(batch, "Press ENTER to resume", worldWidth / 2f - 140, worldHeight / 2f);
            font.draw(batch, "Press ESC to exit to menu", worldWidth / 2f - 160, worldHeight / 2f - 20);
        }

        batch.end();
    }

    private void allShapesRender() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

//        player.render();
        bulletSystem.render(shapeRenderer);

        hudSystem.renderHUDHealth(shapeRenderer, worldHeight, player);

        for (Enemy enemy : enemies) {
            enemy.render();

            shapeRenderer.setColor(Color.RED);
            shapeRenderer.rect(enemy.getPosition().x, enemy.getPosition().y + enemy.getSize() + 2, enemy.getSize() * ((float) enemy.getHealth() / 3), 5);
        }

        resetWave();

        // проверка победы
        if (waveNumber > maxWaves) {
            game.setScreen(new MenuScreen(game)); // пока возвращаем в меню
            System.out.println("Victory! Final score: " + score);
        }

        shapeRenderer.end();
    }

    private void setCamera() {
        camera.update();

        // Передаём матрицу камеры в ShapeRenderer
        shapeRenderer.setProjectionMatrix(camera.combined);

        batch.setProjectionMatrix(camera.combined); // передаем ту же камеру, что и ShapeRenderer
    }

    private static void cleanScreen() {
        // Устанавливаем цвет фона (темно-серый)
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);

        // Очищаем цветовой буфер
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void resetWave() {
        // если все враги убиты
        if (enemies.size == 0) {
            waveTimer += Gdx.graphics.getDeltaTime();

            if (waveTimer >= waveDelay) {
                waveNumber++;
                waveSystem.spawnWave(enemies, waveNumber, batch);
                waveTimer = 0;
            }
        }
    }

    // --------------------- Методы интерфейса Screen (пустые) ---------------------
    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    /**
     * dispose — освобождение ресурсов
     */
    @Override
    public void dispose() {
        shapeRenderer.dispose();
        batch.dispose();
        font.dispose();
        controller = null;
//        player.dispose();
    }
}
