package org.arena.survival;

import com.badlogic.gdx.Game;
import org.arena.survival.assets.Assets;
import org.arena.survival.screen.MenuScreen;

/**
 * ArenaGame is the main entry point of the game.
 * <p>
 * It extends {@link Game} from libGDX and is responsible for:
 * <ul>
 *     <li>Loading game assets</li>
 *     <li>Setting the initial screen (MenuScreen)</li>
 * </ul>
 */
public class ArenaGame extends Game {

    /**
     * Called once when the application is created.
     * <p>
     * Loads all assets and sets the initial screen to {@link MenuScreen}.
     */
    @Override
    public void create() {
        Assets.load();                 // Load all game assets
        setScreen(new MenuScreen(this)); // Set the main menu as the first screen
    }
}
