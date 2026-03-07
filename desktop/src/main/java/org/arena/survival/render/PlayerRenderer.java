package org.arena.survival.render;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.arena.survival.entity.Player;

/**
 * Responsible for rendering the {@link Player} entity.
 * <p>
 * This renderer draws the player texture using {@link SpriteBatch}
 * and applies rotation based on the player's current orientation.
 * The sprite is rendered centered around the player's position.
 */
public class PlayerRenderer {

    /** Texture used to render the player. */
    private final Texture texture;

    /**
     * Creates a new {@code PlayerRenderer}.
     *
     * @param texture texture used to draw the player
     */
    public PlayerRenderer(Texture texture) {
        this.texture = texture;
    }

    /**
     * Renders the player using the provided {@link SpriteBatch}.
     *
     * @param batch sprite batch used for drawing
     * @param player player entity containing position, size and rotation
     */
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