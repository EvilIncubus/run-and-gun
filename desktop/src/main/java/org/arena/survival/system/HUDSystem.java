package org.arena.survival.system;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import org.arena.survival.entity.Player;

public class HUDSystem {

    public void renderHUDHealth(ShapeRenderer shapeRenderer, float worldHeight, Player player) {
        // Полоса здоровья (уже есть)
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(140, worldHeight - 30, player.getHealth() * 20, 20);
    }
    public void renderHUDInfo(BitmapFont font, SpriteBatch batch, Player player, float worldHeight, float waveNumber, int score, int maxWaves){
        font.draw(batch, "Health: " + player.getHealth(), 10, worldHeight - 10);
        font.draw(batch, "Wave: " + waveNumber + "/" + maxWaves, 10, worldHeight - 40);
        font.draw(batch, "Score: " + score, 10, worldHeight - 70);
    }
}
