package org.arena.survival.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import org.arena.survival.ArenaGame;

public class DesktopLauncher {
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Arena Survival");
        config.setWindowedMode(800, 600);
        config.useVsync(true);

        new Lwjgl3Application(new ArenaGame(), config);
    }
}