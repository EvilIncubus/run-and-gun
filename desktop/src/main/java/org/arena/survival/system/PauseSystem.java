package org.arena.survival.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controller;
import org.arena.survival.ArenaGame;
import org.arena.survival.screen.MenuScreen;

public class PauseSystem {



    public boolean setPause(boolean paused){
        // Обработка ESC и ENTER только для паузы
        return !paused && (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)); // включаем паузу
    }

    public boolean setPauseWithController(boolean paused, Controller controller) {
        return !paused && (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) || controller.getButton(1)); // включаем паузу
    }
}
