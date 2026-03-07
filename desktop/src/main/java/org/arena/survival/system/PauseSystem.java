package org.arena.survival.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import org.arena.survival.ArenaGame;
import org.arena.survival.screen.MenuScreen;

public class PauseSystem {



    public boolean setPause(boolean paused){
        // Обработка ESC и ENTER только для паузы
        return !paused && Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE); // включаем паузу
    }
}
