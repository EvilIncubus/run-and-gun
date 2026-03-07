package org.arena.survival.render;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.arena.survival.entity.Player;

public class PlayerRenderer {
    private Texture texture;

    public PlayerRenderer(Texture texture) {
        this.texture = texture;
    }

    public void render(SpriteBatch batch, Player player) {

        float size = player.getSize();

        batch.draw(
                texture,
                player.getPosition().x,
                player.getPosition().y,
                size / 2f,
                size / 2f,
                size,
                size,
                1,
                1,
                player.getRotation(),
                0,
                0,
                texture.getWidth(),
                texture.getHeight(),
                false,
                false
        );
    }
}
