package org.arena.survival.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import org.arena.survival.entity.Player;

public class PlayerController {

    private OrthographicCamera camera;

    public PlayerController(OrthographicCamera camera) {
        this.camera = camera;
    }

    public Vector2 movementInput() {

        float x = 0;
        float y = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.W)) y += 1;
        if (Gdx.input.isKeyPressed(Input.Keys.S)) y -= 1;
        if (Gdx.input.isKeyPressed(Input.Keys.A)) x -= 1;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) x += 1;

        return new Vector2(x, y);
    }

    public Vector2 mouseDirection(Player player) {

        Vector3 mouse = new Vector3(
                Gdx.input.getX(),
                Gdx.input.getY(),
                0
        );

        camera.unproject(mouse);

        return new Vector2(
                mouse.x - player.getCenterX(),
                mouse.y - player.getCenterY()
        );
    }

    public boolean isShootPressed() {
        return Gdx.input.isButtonPressed(Input.Buttons.LEFT);
    }
}
