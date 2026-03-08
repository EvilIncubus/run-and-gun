package org.arena.survival.system;

import com.badlogic.gdx.utils.Array;
import org.arena.survival.entity.*;

/**
 * WaveSystem is responsible for spawning enemy waves in the game.
 * <p>
 * Each wave increases the number of enemies and spawns a mix of melee and shooter types
 * at random positions within the game world.
 */
public class WaveSystem {

    /**
     * Spawns a new wave of enemies.
     * <p>
     * The number of enemies increases with the wave number.
     * Enemies are randomly positioned within the game world boundaries.
     *
     * @param enemies the array of enemies to populate
     * @param wave    the current wave number (starting from 1)
     */
    public void spawnWave(Array<Enemy> enemies, int wave) {
        int enemyCount = wave + 1; // number of enemies grows with wave number
        float worldWidth = 1920;
        float worldHeight = 1200;

        if (wave == 25){
            Enemy boss = new FinalBoss(900, 500);
            enemies.add(boss);
            return;
        }

        // каждые 5 волн мини босс
        if (wave % 5 == 0) {
            Enemy boss = new EnemyMiniBoss(900, 500);
            boss.setHealth(boss.getHealth() + wave * 10);
            boss.setMaxHealth(boss.getHealth());
            enemies.add(boss);
        }

        for (int i = 0; i < enemyCount; i++) {
            float x = (float) Math.random() * worldWidth;
            float y = (float) Math.random() * worldHeight;
            Enemy enemy = new EnemyMelee(x, y);
            enemy.setHealth(enemy.getHealth() + wave);
            enemy.setSpeed(enemy.getSpeed() + wave);
            enemy.setMaxHealth(enemy.getHealth());
            enemies.add(enemy);
        }

        for (int i = 0; i < enemyCount/2; i++) {
            float x1 = (float) Math.random() * worldWidth;
            float y1 = (float) Math.random() * worldHeight;
            Enemy shooter = new EnemyShooter(x1, y1);
            shooter.setHealth(shooter.getHealth() + wave);
            shooter.setSpeed(shooter.getSpeed() + wave);
            shooter.setMaxHealth(shooter.getHealth());
            enemies.add(shooter);
        }

        System.out.println("Wave " + wave + " spawned with " + enemyCount + " enemies!");
    }
}