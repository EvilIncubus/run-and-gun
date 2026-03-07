package org.arena.survival.system;

import com.badlogic.gdx.utils.Array;
import org.arena.survival.entity.Enemy;
import org.arena.survival.entity.EnemyMelee;
import org.arena.survival.entity.EnemyShooter;

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

        for (int i = 0; i < enemyCount; i++) {
            float x = (float) Math.random() * worldWidth;
            float y = (float) Math.random() * worldHeight;
            enemies.add(new EnemyMelee(x, y));

            float x1 = (float) Math.random() * worldWidth;
            float y1 = (float) Math.random() * worldHeight;
            enemies.add(new EnemyShooter(x1, y1));
        }

        System.out.println("Wave " + wave + " spawned with " + enemyCount + " enemies!");
    }
}