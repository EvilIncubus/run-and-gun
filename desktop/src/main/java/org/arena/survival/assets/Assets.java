package org.arena.survival.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class Assets {

    public static final AssetManager manager = new AssetManager();

    // -------- textures --------
    public static Texture player;
    public static Texture enemy;
    public static Texture bullet;

    // -------- audio --------
    public static Music gameMusic;
    public static Sound shootSound;

    public static void load() {

        // textures
        manager.load("assets/data/player.png", Texture.class);
        manager.load("assets/data/enemy.png", Texture.class);
        //manager.load("assets/data/bullet.png", Texture.class);

        // audio
        manager.load("assets/data/game_music.mp3", Music.class);
        manager.load("assets/data/shotgun.wav", Sound.class);

        manager.finishLoading();

        // присваиваем
        player = manager.get("assets/data/player.png");
        enemy = manager.get("assets/data/enemy.png");
        //bullet = manager.get("assets/data/bullet.png");

        gameMusic = manager.get("assets/data/game_music.mp3");
        shootSound = manager.get("assets/data/shotgun.wav");
    }

    public static void dispose() {
        manager.dispose();
    }
}
