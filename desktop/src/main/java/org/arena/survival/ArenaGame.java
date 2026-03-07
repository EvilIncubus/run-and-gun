package org.arena.survival;

import com.badlogic.gdx.Game;
import org.arena.survival.assets.Assets;
import org.arena.survival.screen.MenuScreen;

public class ArenaGame extends Game {
    @Override
    public void create() {
        Assets.load();
        setScreen(new MenuScreen(this));
    }
}
