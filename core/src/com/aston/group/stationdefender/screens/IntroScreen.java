package com.aston.group.stationdefender.screens;

import com.aston.group.stationdefender.callbacks.MenuCallback;
import com.aston.group.stationdefender.config.Constants;
import com.aston.group.stationdefender.engine.GameEngine;
import com.aston.group.stationdefender.utils.FileUtils;
import com.aston.group.stationdefender.utils.FontManager;
import com.aston.group.stationdefender.utils.TextureManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Cursor.SystemCursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.Objects;

/**
 * IntroScreen is the first screen shown in the game and contains
 * the game title and buttons that link to other screens such as the
 * background information screen, instruction screen etc.
 *
 * @author Jonathon Fitch
 */
public class IntroScreen implements Screen {
    private final SpriteBatch batch = GameEngine.getBatch();
    private final Stage stage;
    private final BitmapFont font = FontManager.getFont(50);
    private final BitmapFont smallerFont = FontManager.getFont(16);
    private final TextButton backgroundButton, instructionButton, playButton, exitButton;
    private final TextButton[] buttons;
    private float fadeElapsed;

    /**
     * Construct a new IntroScreen
     *
     * @param menuCallback The MenuCallback to use
     */
    public IntroScreen(MenuCallback menuCallback) {
        EventListener buttonListener = new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.graphics.setSystemCursor(SystemCursor.Hand);
                if (Objects.equals(actor, backgroundButton)) {
                    menuCallback.onDisplayBackground();
                } else if (Objects.equals(actor, instructionButton)) {
                    menuCallback.onDisplayInstructions();
                } else if (Objects.equals(actor, playButton)) {
                    menuCallback.onPlay(true);
                } else if (Objects.equals(actor, exitButton)) {
                    menuCallback.onExit();
                }
            }
        };

        //Delete save files to reset inventory
        FileUtils.deleteLevelInfo();

        //Buttons
        stage = new Stage();
        TextButtonStyle textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = font;
        Texture hoverTexture = TextureManager.loadTexture(TextureManager.BLACK_HOVER);
        textButtonStyle.over = new TextureRegionDrawable(new TextureRegion(hoverTexture));
        backgroundButton = new TextButton(Constants.MENU_ITEMS[0], textButtonStyle);
        instructionButton = new TextButton(Constants.MENU_ITEMS[1], textButtonStyle);
        playButton = new TextButton(Constants.MENU_ITEMS[2], textButtonStyle);
        exitButton = new TextButton(Constants.MENU_ITEMS[3], textButtonStyle);
        buttons = new TextButton[]{backgroundButton, instructionButton, playButton, exitButton};

        for (TextButton button : buttons) {
            button.setColor(1, 1, 1, 0);
        }

        backgroundButton.addListener(buttonListener);
        instructionButton.addListener(buttonListener);
        playButton.addListener(buttonListener);
        exitButton.addListener(buttonListener);

        Table table = new Table();
        table.setFillParent(true);
        table.add(backgroundButton).row();
        table.add(instructionButton).row();
        table.add(playButton).row();
        table.add(exitButton).row();

        Group background = new Group();
        background.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Image image = new Image();
        image.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Texture texture = TextureManager.loadTexture(TextureManager.BACKGROUND_TITLE);
        image.setDrawable(new TextureRegionDrawable(new TextureRegion(texture)));

        background.addActor(image);
        stage.addActor(background);
        stage.addActor(table);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        // fade in animation
        fadeElapsed += delta;
        float fadeInTime = .5f;
        float fadeDelay = 0f;
        float fade = Interpolation.fade.apply((fadeElapsed - fadeDelay) / fadeInTime);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        GameEngine.render();
        stage.act(delta);
        batch.begin();
        stage.draw();

        // delay animation by a certain amount for each menu item
        font.setColor(1, 1, 1, fade);
        for (int i = 0; i < buttons.length; i++) {
            fade = Interpolation.fade.apply((fadeElapsed - (fadeDelay + i + 1f)) / fadeInTime);
            buttons[i].setColor(1, 1, 1, fade);
        }
        batch.end();
        batch.begin();
        smallerFont.draw(batch, "v" + Constants.VERSION, 20, 30);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        GameEngine.update(width, height);
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        stage.dispose();
        font.dispose();
        batch.dispose();
    }
}