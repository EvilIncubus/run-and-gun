package org.arena.survival.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import org.arena.survival.ArenaGame;
import org.arena.survival.entity.Enemy;
import org.arena.survival.entity.EnemyShooter;
import org.arena.survival.entity.Player;
import org.arena.survival.entity.UpgradeCard;
import org.arena.survival.input.InputManager;
import org.arena.survival.input.PlayerController;
import org.arena.survival.screen.GameScreen;
import org.arena.survival.screen.MenuScreen;

/**
 * Main game logic system.
 *
 * <p>This system coordinates all key gameplay subsystems:
 * <ul>
 *     <li>Player input handling</li>
 *     <li>Updating core systems (AI, bullets, player)</li>
 *     <li>Combat handling</li>
 *     <li>Enemy movement and behavior</li>
 *     <li>Collision checks</li>
 *     <li>Wave management</li>
 *     <li>Win condition verification</li>
 * </ul>
 *
 * <p>This class acts as a central coordinator between different gameplay systems.
 */
public class GameLogicSystem {

    /** Reference to the current game screen. */
    private final GameScreen gameScreen;

    /** Player controller (camera + input). */
    private final PlayerController playerController;

    /** Input manager (keyboard/gamepad). */
    private final InputManager inputManager;

    /** Enemy bullet system. */
    private final EnemyBulletSystem enemyBulletSystem;

    /** Player bullet system. */
    private final BulletSystem bulletSystem;

    /** Collision detection system. */
    private final CollisionSystem collisionSystem;

    /** Enemy wave system. */
    private final WaveSystem waveSystem;

    /** Enemy AI system. */
    private final EnemyAI enemyAI;

    /** Player movement system. */
    private final PlayerMovementSystem movementSystem;

    /** Weapon system handling shooting for player and enemies. */
    private final WeaponSystem weaponSystem;

    private final UpgradeSystem upgradeSystem = new UpgradeSystem();

    private Array<UpgradeCard> currentCards = new Array<>();

    /** Knockback strength applied to enemies on collision. */
    private static final float knockbackStrength = 150;

    /** Timer until the next enemy wave. */
    private float waveTimer = 0f;

    /** Delay between waves in seconds. */
    private static final float waveDelay = 3f;

    private boolean upgradeForWave = false;

    /**
     * Constructs the game logic system.
     *
     * @param gameScreen current game screen
     * @param camera game camera
     * @param controller connected gamepad controller
     * @param worldWidth width of the game world
     * @param worldHeight height of the game world
     */
    public GameLogicSystem(GameScreen gameScreen, OrthographicCamera camera, Controller controller, float worldWidth, float worldHeight, BulletSystem bulletSystem, EnemyBulletSystem enemyBulletSystem) {
        this.gameScreen = gameScreen;
        this.bulletSystem = bulletSystem;
        this.enemyBulletSystem = enemyBulletSystem;
        this.collisionSystem = new CollisionSystem();
        this.waveSystem = new WaveSystem();
        this.enemyAI = new EnemyAI();

        this.playerController = new PlayerController(camera);
        this.inputManager = new InputManager(playerController, controller);

        this.weaponSystem = new WeaponSystem(bulletSystem, enemyBulletSystem);
        this.movementSystem = new PlayerMovementSystem(worldWidth, worldHeight);
    }

    /**
     * Main update method for game logic, called every frame.
     *
     * @param delta time since last frame
     * @param player the player entity
     * @param enemies array of all enemies
     * @param game the game instance
     */
    public void update(float delta, Player player, Array<Enemy> enemies, ArenaGame game) {
        handleInput(delta, player);
        updateSystems(delta, player, enemies);
        handleCombat(enemies, player);
        updateEnemies(enemies, player, game, delta);
        handleCollisions(delta, player, enemies);
        winVerify(game);
        resetWave(enemies, player);
    }

    /**
     * Handles player input: movement, rotation, and shooting.
     */
    private void handleInput(float delta, Player player) {
        Vector2 moveDir = inputManager.getMoveDirection();
        movementSystem.move(player, moveDir, delta);

        Vector2 aimDir = inputManager.getAimDirection(player);
        player.setRotation(aimDir.angleDeg());

        if (inputManager.isShootPressed()) {
            weaponSystem.shoot(player, aimDir);
        }
    }

    public Array<UpgradeCard> getCurrentCards() {
        return currentCards;
    }

    /**
     * Updates core gameplay systems: player, AI, and bullets.
     */
    private void updateSystems(float delta, Player player, Array<Enemy> enemies) {
        player.update(delta);
        enemyAI.update(delta);
        bulletSystem.update(delta, enemies);
        enemyBulletSystem.update(delta);
    }

    /**
     * Handles combat for enemies. Enemies of type {@link EnemyShooter} shoot towards the player.
     */
    private void handleCombat(Array<Enemy> enemies, Player player) {
        for (Enemy enemy : enemies) {
            if (enemy instanceof EnemyShooter) {
                Vector2 dir = new Vector2(player.getPosition())
                        .sub(enemy.getPosition())
                        .nor();
                weaponSystem.shoot(enemy, dir);
            }
        }
    }

    /**
     * Updates enemy movement and applies knockback effects.
     */
    private void updateEnemies(Array<Enemy> enemies, Player player, ArenaGame game, float delta) {
        for (Enemy enemy : enemies) {
            enemy.update(player, delta);
            enemyAI.enemyMovementAndKnockback(enemy, player, game, knockbackStrength);
        }
    }

    /**
     * Checks collisions:
     * <ul>
     *     <li>Enemy bullets hitting the player</li>
     *     <li>Player bullets hitting enemies</li>
     * </ul>
     */
    private void handleCollisions(float delta, Player player, Array<Enemy> enemies) {
        collisionSystem.checkEnemyBullets(enemyBulletSystem.getBullets(), player, delta);
        gameScreen.setScore(collisionSystem.update(bulletSystem.getBullets(), enemies, delta, gameScreen.getScore(), player));
    }

    /**
     * Verifies win conditions.
     *
     * <p>If the current wave number exceeds the maximum, the game returns to the main menu.</p>
     */
    private void winVerify(ArenaGame game) {
        if (gameScreen.getWaveNumber() > gameScreen.getMaxWaves()) {
            game.setScreen(new MenuScreen(game));
            gameScreen.setWinGame(true);
        }
    }

    /**
     * Handles spawning of new waves.
     *
     * <p>If all enemies are defeated, starts a timer before the next wave.</p>
     */
    private void resetWave(Array<Enemy> enemies, Player player) {
        if (enemies.size == 0) {

            if (!upgradeForWave) {

                currentCards = upgradeSystem.getRandomCards();
                gameScreen.setUpgradeActive(true);
                upgradeForWave = true;
                return;
            }

            if (!gameScreen.isUpgradeActive()) {
                waveTimer += Gdx.graphics.getDeltaTime();
                if (waveTimer >= waveDelay) {
                    gameScreen.setWaveNumber(gameScreen.getWaveNumber() + 1);
                    waveSystem.spawnWave(enemies, gameScreen.getWaveNumber());
                    waveTimer = 0;
                    upgradeForWave=false;
                }
            }
        }
    }

    public void handleUpgradeSelection(Player player) {

        if (!gameScreen.isUpgradeActive()) return;

        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            chooseUpgrade(player, 0);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            chooseUpgrade(player, 1);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
            chooseUpgrade(player, 2);
        }
    }

    private void chooseUpgrade(Player player, int index) {

        UpgradeCard card = currentCards.get(index);

        PlayerUpgradeApplier.apply(player, card);

        gameScreen.setUpgradeActive(false);
    }

}