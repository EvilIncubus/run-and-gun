package org.arena.survival.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerMapping;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.arena.survival.ArenaGame;
import org.arena.survival.assets.Assets;

/**
 * MenuScreen handles the main menu UI, including controller detection, music playback,
 * and transitioning to the GameScreen or exiting the game.
 */
public class MenuScreen implements Screen {

    /** Stage for Scene2D UI */
    private Stage stage;

    /** Reference to the main game class */
    private final ArenaGame game;

    /** Skin used for UI widgets */
    private Skin skin;

    /** Background music for the menu */
    private Music gameMusic;

    /** Currently connected controller (if any) */
    private Controller controller;

    /**
     * Creates a new MenuScreen.
     *
     * @param game The main ArenaGame instance
     */
    public MenuScreen(ArenaGame game) {
        this.game = game;
    }

    /**
     * Called when the screen is first shown. Initializes stage, controller, music, and UI.
     */
    @Override
    public void show() {
        initStage();
        initController();
        initMusic();
        initUI();

        Gdx.input.setInputProcessor(stage);
    }

    /** Initializes the Stage and UI Skin */
    private void initStage() {
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("assets/data/uiskin.json"));
    }

    /** Detects connected controller and logs its mapping */
    private void initController() {
        if (!Controllers.getControllers().isEmpty()) {
            controller = Controllers.getControllers().first();
            Gdx.app.log("Controller", "Connected: " + controller.getName());

            ControllerMapping mapping = controller.getMapping();
            if(mapping != null) {
                Gdx.app.log("Controller", "Mapping detected, jump button = " + mapping.buttonA);
            } else {
                Gdx.app.log("Controller", "No mapping available, fallback to default axes/buttons");
            }
        }
    }

    /** Initializes background music and starts playback */
    private void initMusic() {
        gameMusic = Assets.gameMusic;
        gameMusic.setLooping(true);
        gameMusic.setVolume(0.3f);
        gameMusic.play();
    }

    /** Creates UI elements (buttons, layout) and adds them to the stage */
    private void initUI() {
        // Clear color buffer
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Create buttons
        TextButton playButton = new TextButton("Play", skin);
        TextButton exitButton = new TextButton("Exit", skin);

        playButton.setColor(0, 1, 0, 1); // green
        exitButton.setColor(1, 0, 0, 1); // red

        // Play button listener
        playButton.addListener(event -> {
            if ((controller != null && controller.getButton(0)) || playButton.isPressed()) {
                Gdx.input.setInputProcessor(null); // remove stage input
                game.setScreen(new GameScreen(game, controller));
                return true;
            }
            return false;
        });

        // Exit button listener
        exitButton.addListener(event -> {
            if ((controller != null && controller.getButton(1)) || exitButton.isPressed()) {
                Gdx.app.exit();
                return true;
            }
            return false;
        });

        // Layout table for buttons
        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.add(playButton).width(300).height(50).pad(50).row();
        table.add(exitButton).width(300).height(50).pad(50).row();

        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);

        stage.addActor(table);
    }

    /**
     * Renders the menu screen each frame.
     *
     * @param delta Time in seconds since the last frame
     */
    @Override
    public void render(float delta) {
        initUI(); // refresh UI each frame (optional: could be done once)
        stage.act(delta);
        stage.draw();
    }

    /**
     * Called when screen size changes.
     *
     * @param width  New width
     * @param height New height
     */
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    /** Called when the game is paused */
    @Override
    public void pause() {}

    /** Called when the game is resumed from pause */
    @Override
    public void resume() {}

    /** Called when the screen is hidden */
    @Override
    public void hide() {}

    /**
     * Called when the screen is disposed.
     * Frees resources such as stage, skin, music, and controller reference.
     */
    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        controller = null;
        gameMusic.dispose();
    }
}