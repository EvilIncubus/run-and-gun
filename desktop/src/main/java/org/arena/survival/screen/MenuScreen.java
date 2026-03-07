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

public class MenuScreen implements Screen {

    private Stage stage;
    private final ArenaGame game; // твой класс, который наследует Game
    private Skin skin;
    private Music gameMusic;

    private Controller controller; // текущий джойстик

    public MenuScreen(ArenaGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        initStage();
        initController();
        initMusic();
        initUI();

        Gdx.input.setInputProcessor(stage);
    }

    private void initStage() {
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("assets/data/uiskin.json"));
    }

    private void initController() {
        if (!Controllers.getControllers().isEmpty()) {
            controller = Controllers.getControllers().first();
            Gdx.app.log("Controller", "Connected: " + controller.getName());

            // Логируем mapping
            ControllerMapping mapping = controller.getMapping();
            if(mapping != null) {
                Gdx.app.log("Controller", "Mapping detected, jump button = " + mapping.buttonA);
            } else {
                Gdx.app.log("Controller", "No mapping available, fallback to default axes/buttons");
            }
        }
    }

    private void initMusic() {
        gameMusic = Assets.gameMusic;
        gameMusic.setLooping(true);
        gameMusic.setVolume(0.3f);
        gameMusic.play();
    }

    private void initUI() {
        // Очищаем цветовой буфер
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // стандартная skin для кнопок
        skin = new Skin(Gdx.files.internal("assets/data/uiskin.json"));

        // создаём кнопки
        TextButton playButton = new TextButton("Play", skin);
        TextButton exitButton = new TextButton("Exit", skin);

        playButton.setColor(0,1,0,1); // зелёная
        exitButton.setColor(1,0,0,1); // красная

        // обработка нажатий
        playButton.addListener(event -> {
            if (playButton.isPressed()) {

                Gdx.input.setInputProcessor(null); // убрать Stage

                game.setScreen(new GameScreen(game, controller));
                return true;
            }
            return false;
        });

        exitButton.addListener(event -> {
            if (exitButton.isPressed()) {
                Gdx.app.exit();
                return true;
            }
            return false;
        });

        // таблица для центрирования
        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.add(playButton).width(300).height(50).pad(50).row();
        table.add(exitButton).width(300).height(50).pad(50).row();

        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        initUI();
        stage.act(delta);
        stage.draw();
    }

    @Override public void resize(int width, int height) { stage.getViewport().update(width, height, true); }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() { stage.dispose(); skin.dispose(); controller = null; gameMusic.dispose();}
}
