package org.arena.survival.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import org.arena.survival.ArenaGame;

/**
 * Desktop launcher class for the Arena Survival game.
 * <p>
 * This class configures and launches the desktop version of the game using
 * the LWJGL3 backend. It sets up the window size, title, and other basic settings.
 */
public class DesktopLauncher {

    /**
     * Main entry point for the desktop application.
     * <p>
     * Configures the LWJGL3 application settings such as window size,
     * title, and vertical sync, then launches the game by creating an
     * instance of {@link ArenaGame}.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Arena Survival");
        config.setWindowedMode(1920, 1080);
        config.useVsync(true);

        new Lwjgl3Application(new ArenaGame(), config);
    }
}