package org.arena.survival.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

/**
 * Assets is a centralized class for managing all game resources.
 * <p>
 * It uses {@link AssetManager} from libGDX to load and store textures, sounds, and music.
 * All assets are publicly accessible as static fields.
 */
public class Assets {

    /** Global AssetManager instance used to load and manage game assets. */
    public static final AssetManager manager = new AssetManager();

    // -------- Textures --------

    /** Player texture */
    public static Texture player;

    /** Enemy texture */
    public static Texture enemy;

    /** Bullet texture (currently not used) */
    public static Texture bullet;

    public static Texture map;

    // -------- Audio --------

    /** Background game music */
    public static Music gameMusic;

    /** Sound played when shooting */
    public static Sound shootSound;

    /**
     * Loads all game assets (textures, sounds, music) and assigns them to static fields.
     * <p>
     * This method blocks until all assets are fully loaded using {@link AssetManager#finishLoading()}.
     */
    public static void load() {

        manager.load("assets/data/map.png", Texture.class);

        // Load textures
        manager.load("assets/data/player.png", Texture.class);
        manager.load("assets/data/enemy.png", Texture.class);
        // manager.load("assets/data/bullet.png", Texture.class); // Uncomment if bullet texture is needed

        // Load audio
        manager.load("assets/data/game_music.mp3", Music.class);
        manager.load("assets/data/shotgun.wav", Sound.class);

        // Block until all assets are loaded
        manager.finishLoading();

        map = manager.get("assets/data/map.png", Texture.class);

        // Assign loaded assets to static fields
        player = manager.get("assets/data/player.png", Texture.class);
        enemy = manager.get("assets/data/enemy.png", Texture.class);
        // bullet = manager.get("assets/data/bullet.png", Texture.class);

        gameMusic = manager.get("assets/data/game_music.mp3", Music.class);
        shootSound = manager.get("assets/data/shotgun.wav", Sound.class);
    }

    /**
     * Disposes all loaded assets and the {@link AssetManager}.
     * <p>
     * Should be called when the game exits to free memory.
     */
    public static void dispose() {
        manager.dispose();
    }
}