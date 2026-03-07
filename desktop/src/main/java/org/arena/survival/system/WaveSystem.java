package org.arena.survival.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import org.arena.survival.entity.Enemy;
import org.arena.survival.entity.EnemyMelee;
import org.arena.survival.entity.EnemyShooter;

import java.util.List;

public class WaveSystem {

    public void spawnWave(Array<Enemy> enemies, int wave, SpriteBatch batch) {
        int enemyCount = wave * 2; // например, количество врагов растёт с каждой волной
        float worldWidth = 1920;
        float worldHeight = 1200;

        for (int i = 0; i < enemyCount; i++) {
            float x = (float) Math.random() * worldWidth;
            float y = (float) Math.random() * worldHeight;

            // создаём врага и добавляем в массив
            enemies.add(new EnemyMelee(x, y));
            enemies.add(new EnemyShooter(x, y));
        }

        System.out.println("Wave " + wave + " spawned with " + enemyCount + " enemies!");
    }

}
