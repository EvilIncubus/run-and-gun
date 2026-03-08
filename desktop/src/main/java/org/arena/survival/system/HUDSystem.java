package org.arena.survival.system;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import org.arena.survival.entity.Enemy;
import org.arena.survival.entity.Player;

/**
 * HUDSystem handles rendering of heads-up display (HUD) elements
 * such as player health, enemy health bars, current wave, and score.
 */
public class HUDSystem {

    /**
     * Renders the player's health bar using a green rectangle.
     *
     * @param shapeRenderer the ShapeRenderer used to draw the health bar
     * @param worldHeight the height of the game world, used to position the HUD
     * @param player the Player whose health will be displayed
     */
    public void renderHUDHealth(ShapeRenderer shapeRenderer, float worldHeight, Player player) {
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(
                140,
                worldHeight - 30,
                player.getHealth() * 20,
                20);
    }

    /**
     * Renders an enemy's health bar above its sprite using a red rectangle.
     *
     * @param shapeRenderer the ShapeRenderer used to draw the health bar
     * @param enemy the Enemy whose health will be displayed
     */
    public void renderHUDHealth(ShapeRenderer shapeRenderer, Enemy enemy) {
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(
                enemy.getPosition().x,
                enemy.getPosition().y + enemy.getSize() + 2,
                enemy.getSize() * ((float) enemy.getHealth() / enemy.getMaxHealth()),
                5
        );
    }

    /**
     * Renders textual HUD information such as player's health, current wave, and score.
     *
     * @param font the BitmapFont used to draw text
     * @param batch the SpriteBatch used to render text
     * @param player the Player whose health will be displayed
     * @param worldHeight the height of the game world, used to position text
     * @param waveNumber the current wave number
     * @param score the current player score
     * @param maxWaves the maximum number of waves in the game
     */
    public void renderHUDInfo(BitmapFont font, SpriteBatch batch, Player player, float worldHeight, float waveNumber, int score, int maxWaves) {
        font.draw(batch, "Health: " + player.getHealth(), 10, worldHeight - 10);
        font.draw(batch, "Wave: " + waveNumber + "/" + maxWaves, 10, worldHeight - 40);
        font.draw(batch, "Score: " + score, 10, worldHeight - 70);
    }
}