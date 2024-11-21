package CodeSmashers.AngryBirds.Screens;

import CodeSmashers.AngryBirds.AudioManager;
import CodeSmashers.AngryBirds.AudioPlayer;
import CodeSmashers.AngryBirds.GameAssetManager;
import CodeSmashers.AngryBirds.HelperClasses.Level;
import CodeSmashers.AngryBirds.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class LevelSelector implements Screen {

    private static final String BASE_DIR = "LevelSelector/";
    private static final float LEVEL_BUTTON_SIZE = 100f;
    private static final float LEVEL_BUTTON_PADDING = 40f;

    private Main game;
    private GameAssetManager assetManager;
    private Stage stage;
    private SpriteBatch batch;
    private AudioPlayer mouseClick;
    private AudioPlayer scrollSound;
    private Texture background;
    private Texture angryBird;
    private Texture backTexture;
    private ImageButton backButton;
    private int onWhichLevel;

    public LevelSelector(Main game) {
        this.game = game;
        this.assetManager = game.getAssets();
        this.stage = new Stage(new ScreenViewport());
        this.batch = new SpriteBatch();
        this.mouseClick = new AudioPlayer("mouseClicked.wav", game.getAssets(), true);
        this.scrollSound = new AudioPlayer("mouseClicked.wav", game.getAssets(), true);
        loadAssets();
        createBackButton();
        createLevelButtons();
    }

    private void loadAssets() {
        background = game.getAssets().getTexture(BASE_DIR + "background.png");
        angryBird = game.getAssets().getTexture(BASE_DIR + "angry_bird.png");
        backTexture = game.getAssets().getTexture(BASE_DIR + "back.png");
    }
    private void createBackButton() {
        Drawable backDrawable = new TextureRegionDrawable(backTexture);
        backButton = new ImageButton(backDrawable);
        backButton.setSize(100, 100);
        backButton.setPosition(Gdx.graphics.getBackBufferWidth() - 100 - 30, Gdx.graphics.getHeight() - 120);
        stage.addActor(backButton);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mouseClick.playSoundEffect();
                game.getState().switchScreen(new MainMenuScreen(game));
            }
        });
    }
    private void createLevelButtons() {
        float buttonY = (float) Gdx.graphics.getHeight() / 2 - 100;
        int levelCount = assetManager.levels;

        Table buttonTable = new Table();
        buttonTable.left();

        for (int i = 1; i <= levelCount; i++) {
            String levelKey = "level" + i;
            Level level = assetManager.LevelChart.get(levelKey);
            boolean isCompleted = level.isCompleted();

            if (!isCompleted && onWhichLevel == 0) {
                onWhichLevel = i;
            }

            String texturePath = BASE_DIR + (i == 10 ? "boss_level" : i);

            if (isCompleted) {
                createLevelButton(buttonTable, texturePath + ".png", i, false);
            } else if (i == onWhichLevel) {
                createLevelButton(buttonTable, texturePath + ".png", i, true);
            } else {
                createDisabledLevelButton(buttonTable, texturePath + "b.png", i);
            }
        }

        ScrollPane scrollPane = new ScrollPane(buttonTable);
        scrollPane.setSize(Gdx.graphics.getWidth(), LEVEL_BUTTON_SIZE + 60);
        scrollPane.setPosition(10, buttonY - 50);
        scrollPane.setScrollingDisabled(false, true);

        scrollPane.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                scrollSound.playSoundEffect();
            }
        });

        stage.addActor(scrollPane);
    }

    private void createLevelButton(Table table, String texturePath, int levelIndex, boolean isHighlighted) {
        Texture levelTexture = game.getAssets().getTexture(texturePath);
        Drawable levelDrawable = new TextureRegionDrawable(levelTexture);

        ImageButton levelButton = new ImageButton(levelDrawable);
        levelButton.setSize(LEVEL_BUTTON_SIZE, LEVEL_BUTTON_SIZE);
        levelButton.setTouchable(Touchable.enabled);

        if (isHighlighted) {
            setHighlightedButtonStyle(levelButton, texturePath);
        }

        table.add(levelButton).padRight(LEVEL_BUTTON_PADDING);

        levelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mouseClick.playSoundEffect();
                game.getState().switchScreen(new GamePlay(game, levelIndex));
            }
        });
    }

    private void createDisabledLevelButton(Table table, String texturePath, int levelIndex) {
        Texture levelTexture = game.getAssets().getTexture(texturePath);
        Drawable levelDrawable = new TextureRegionDrawable(levelTexture);

        ImageButton levelButton = new ImageButton(levelDrawable);
        levelButton.setSize(LEVEL_BUTTON_SIZE, LEVEL_BUTTON_SIZE);
        levelButton.setTouchable(Touchable.disabled);

        table.add(levelButton).padRight(LEVEL_BUTTON_PADDING);

        levelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mouseClick.playSoundEffect();
                game.getState().switchScreen(new GamePlay(game, levelIndex));
            }
        });
    }

    private void setHighlightedButtonStyle(ImageButton levelButton, String texturePath) {
        Drawable highlightedDrawable = new TextureRegionDrawable(game.getAssets().getTexture(texturePath));
        ImageButton.ImageButtonStyle levelButtonStyle = new ImageButton.ImageButtonStyle();
        levelButtonStyle.up = highlightedDrawable;
        levelButtonStyle.down = highlightedDrawable;
        levelButtonStyle.checked = highlightedDrawable;
        levelButtonStyle.disabled = highlightedDrawable;
        levelButton.setStyle(levelButtonStyle);
        levelButton.setColor(1.2f, 1.2f, 1.2f, 1);  // Apply brightness effect
    }




    @Override
    public void show() {
        game.getMuliplexer().addProcessor(stage);
        AudioManager.playBackgroundMusic("level_background.mp3", game.getAssets());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() {
        AudioManager.stopBackgroundMusic();
        game.getMuliplexer().removeProcessor(stage);
    }

    @Override
    public void dispose() {
        game.getMuliplexer().removeProcessor(stage);
        batch.dispose();
        stage.dispose();
        angryBird.dispose();
        mouseClick.dispose();
        scrollSound.dispose();
    }
}
