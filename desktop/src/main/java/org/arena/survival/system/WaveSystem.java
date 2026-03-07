package org.arena.survival.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import org.arena.survival.entity.Enemy;
import org.arena.survival.entity.EnemyMelee;
import org.arena.survival.entity.EnemyShooter;
import org.arena.survival.entity.Player;

import java.util.List;

public class WaveSystem {

    public void spawnWave(Array<Enemy> enemies, int wave) {
        int enemyCount = wave+1; // например, количество врагов растёт с каждой волной
        float worldWidth = 1920;
        float worldHeight = 1200;

        for (int i = 0; i < enemyCount; i++) {
            float x = (float) Math.random() * worldWidth;
            float y = (float) Math.random() * worldHeight;

            // создаём врага и добавляем в массив
            enemies.add(new EnemyMelee(x, y));

            float x1 = (float) Math.random() * worldWidth;
            float y1 = (float) Math.random() * worldHeight;

            enemies.add(new EnemyShooter(x1, y1));
        }

        System.out.println("Wave " + wave + " spawned with " + enemyCount + " enemies!");
    }

}
