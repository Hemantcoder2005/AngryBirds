package CodeSmashers.AngryBirds.Screens;

import CodeSmashers.AngryBirds.AudioPlayer;
import CodeSmashers.AngryBirds.GameAssetManager;
import CodeSmashers.AngryBirds.HelperClasses.Level;
import CodeSmashers.AngryBirds.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
//import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Map;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class LevelSelector implements Screen {

    private static final String BASE_DIR = "LevelSelector/";
    private static final float LEVEL_BUTTON_SIZE = 100f;
    private static final float LEVEL_BUTTON_PADDING = 40f;

    private Main game;
    private GameAssetManager assetManager;
    private Stage stage;
    private SpriteBatch batch;
    private AudioPlayer mouseClick;
    private Texture background;
    private Texture angryBird;
    private Texture backTexture;
    private ImageButton backButton;
    public LevelSelector(Main game) {
        this.game = game;
        this.assetManager = game.getAssets();
        this.stage = new Stage(new ScreenViewport());
        this.batch = new SpriteBatch();
        this.mouseClick = new AudioPlayer("mouseClicked.wav", game.getAssets(), true);
        loadAssets();
        createBackButton();
        createLevelButtons();
    }
    private void loadAssets() {
        background = game.getAssets().getTexture(BASE_DIR + "background.png");
        System.out.println(backButton);
        angryBird = game.getAssets().getTexture(BASE_DIR + "angry_bird.png");
         backTexture = game.getAssets().getTexture(BASE_DIR + "back.png");
    }

    private void createBackButton() {

        Drawable backDrawable = new TextureRegionDrawable(backTexture);
         backButton = new ImageButton(backDrawable);

        backButton.setSize(100, 100);
        backButton.setPosition(20, Gdx.graphics.getHeight() - 120);

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
        float buttonY = (float) Gdx.graphics.getHeight() / 2 - 100; // Y position for all level buttons
        int levelCount = assetManager.levels; // Number of levels
        System.out.println(levelCount);
        // Create a Table to hold the buttons
        Table buttonTable = new Table();
        buttonTable.left();

        for (int i = 1; i <= levelCount; i++) {
            String levelKey = "level" + i;
            Level level = assetManager.LevelChart.get(levelKey);
            boolean isCompleted = level.isCompleted();
            String texturePath = BASE_DIR + i + ".png";
            if(i == 10){
                texturePath = BASE_DIR + "boss_level"  + ".png";
            }
            Texture levelTexture = game.getAssets().getTexture(texturePath);
            Drawable levelDrawable = new TextureRegionDrawable(levelTexture);

            ImageButton levelButton = new ImageButton(levelDrawable);
            levelButton.setSize(LEVEL_BUTTON_SIZE, LEVEL_BUTTON_SIZE);

            buttonTable.add(levelButton).padRight(LEVEL_BUTTON_PADDING); // Add button to table with padding

            // If this is the first incomplete level, we place the angry bird here
//            bo currentLevelButton;
//            if (!isCompleted && currentLevelButton == null) {
//                currentLevelButton = levelButton;
//            }

            // Set button click listener to load the respective level
            int finalI = i; // To use inside the listener
            levelButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    mouseClick.playSoundEffect(); // Play click sound
                    // Load the selected level
                    // game.getState().switchScreen(new GameScreen(game, finalI));
                }
            });
        }

        // Create a ScrollPane with the button table
        ScrollPane scrollPane = new ScrollPane(buttonTable);
        scrollPane.setSize(Gdx.graphics.getWidth(), LEVEL_BUTTON_SIZE + 60); // Set the size of the ScrollPane
        scrollPane.setPosition(0, buttonY); // Set position of ScrollPane

        // Enable horizontal scrolling
        scrollPane.setScrollingDisabled(false, true); // Enable horizontal scrolling, disable vertical

        stage.addActor(scrollPane); // Add ScrollPane to the stage
    }


    @Override
    public void show() {
        game.getMuliplexer().addProcessor(stage);
        for (InputProcessor processor : game.getMuliplexer().getProcessors()) {
            System.out.println(processor.getClass().getSimpleName());
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        stage.act(delta); // Update the stage actors
        stage.draw(); // Draw stage
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
        // Remove input processor when hiding screen
        game.getMuliplexer().removeProcessor(stage);
    }

    @Override
    public void dispose() {

        batch.dispose();
        stage.dispose();
        angryBird.dispose();
        mouseClick.dispose();

    }
}
