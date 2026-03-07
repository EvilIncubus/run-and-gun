package org.arena.survival.input;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerMapping;
import com.badlogic.gdx.math.Vector2;
import org.arena.survival.entity.Player;

public class InputManager {
    private PlayerController keyboardController;
    private Controller gamepad;   // геймпад от LibGDX
    private boolean useGamepad;

    public InputManager(PlayerController keyboardController, Controller detectedGamepad) {
        this.keyboardController = keyboardController;
        this.gamepad = detectedGamepad;
        this.useGamepad = detectedGamepad != null;
    }

    public Vector2 getMoveDirection() {
        // TODO game-pad is under debugging
        if (useGamepad) {
            return getGamepadMove();
        } else {
            return keyboardController.movementInput();
        }
    }

    public Vector2 getAimDirection(Player player) {
        if (useGamepad) {
            return getGamepadAim();
        } else {
            return keyboardController.mouseDirection(player);
        }
    }

    public boolean isShootPressed() {
        if (useGamepad) {
            return getGamepadShoot();
        } else {
            return keyboardController.isShootPressed();
        }
    }

    // Пример чтения с геймпада (левый стик для движения)
    private Vector2 getGamepadMove() {
        float x = gamepad.getAxis(0); // ось X левого стика
        float y = -gamepad.getAxis(1); // ось Y (обычно инвертирована)
        return new Vector2(x, y);
    }

    // Правка направления для стрельбы (правый стик)
    private Vector2 getGamepadAim() {
        float x = gamepad.getAxis(2);
        float y = -gamepad.getAxis(3);
        if (x == 0 && y == 0) {
            return new Vector2(1, 0); // дефолтное направление
        }
        return new Vector2(x, y);
    }

    private boolean getGamepadShoot() {
        return gamepad.getButton(0); // например, кнопка A / X
    }
}
