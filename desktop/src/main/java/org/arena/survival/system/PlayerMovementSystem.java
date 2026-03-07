package org.arena.survival.system;

import com.badlogic.gdx.math.Vector2;
import org.arena.survival.entity.Player;

public class PlayerMovementSystem {
    private float worldWidth;
    private float worldHeight;

    public PlayerMovementSystem(float worldWidth, float worldHeight) {
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
    }

    public void move(Player player, Vector2 direction, float delta) {

        if (direction.len() > 0) {
            direction.nor();

            player.getPosition().mulAdd(
                    direction,
                    player.getSpeed() * delta
            );
        }

        clamp(player);
    }

    private void clamp(Player player) {

        float size = player.getSize();

        player.getPosition().x =
                Math.max(0, Math.min(worldWidth - size, player.getPosition().x));

        player.getPosition().y =
                Math.max(0, Math.min(worldHeight - size, player.getPosition().y));
    }

}
