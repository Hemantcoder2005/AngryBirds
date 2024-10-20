package CodeSmashers.AngryBirds.Screens;

import CodeSmashers.AngryBirds.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import CodeSmashers.AngryBirds.AudioPlayer;

public class MainMenuScreen implements Screen {

    private final Main game;
    private final Texture background;
    private final Texture angry;
    private final Texture birds;
    private final Texture allBirds;
    private final SpriteBatch batch;
    private final Stage stage;
    private final ImageButton playButton;
    private final ImageButton exitButton;
    private final AudioPlayer backgroundSound;
    private final AudioPlayer mouseClick;
    private final String BaseDir = "MainMenu/";
    float imageHeight = 0;
    private float allBirdsHeight = 380f;
    private final float maxAllBirdsHeight = 450f;
    private float animationSpeed = 4.0f;

    public MainMenuScreen(Main game) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.stage = new Stage(new ScreenViewport());
        this.backgroundSound = new AudioPlayer("background.mp3",game.getAssets());
        this.mouseClick = new AudioPlayer("mouseClicked.mp3",game.getAssets(),true);
        background = game.getAssets().getTexture(BaseDir + "background.png");

        Texture playButtonTexture = game.getAssets().getTexture(BaseDir + "play.png");
        Texture exitButtonTexture = game.getAssets().getTexture(BaseDir + "exit.png");

        angry = game.getAssets().getTexture(BaseDir + "angry.png");
        birds = game.getAssets().getTexture(BaseDir + "birds.png");
        allBirds = game.getAssets().getTexture(BaseDir + "allBirds.png");

        Drawable playDrawable = new TextureRegionDrawable(playButtonTexture);
        playButton = new ImageButton(playDrawable);
        playButton.setPosition(Gdx.graphics.getWidth() / 2f - playButton.getWidth() / 2 - 150, Gdx.graphics.getHeight() / 2f - 300);

        Drawable exitDrawable = new TextureRegionDrawable(exitButtonTexture);
        exitButton = new ImageButton(exitDrawable);
        exitButton.setPosition(Gdx.graphics.getWidth() / 2f - exitButton.getWidth() / 2 + 150, Gdx.graphics.getHeight() / 2f - 300);

        stage.addActor(playButton);
        stage.addActor(exitButton);

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mouseClick.playSoundEffect();  // Play click sound
                // Add screen transition logic here
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mouseClick.playSoundEffect();  // Play click sound
                Gdx.app.exit();
            }
        });

        Gdx.input.setInputProcessor(stage);

        startImageAnimations();
        backgroundSound.playBackgroundMusic();  // Play background music
    }

    private void startImageAnimations() {
        stage.addAction(Actions.sequence(
            Actions.alpha(0),
            Actions.fadeIn(2f)
        ));
    }

    @Override
    public void show() {
        game.getMuliplexer().addProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        batch.draw(background, 0, 0, screenWidth, screenHeight);

        float angryAspectRatio = angry.getWidth() / (float) angry.getHeight();
        float birdsAspectRatio = birds.getWidth() / (float) birds.getHeight();

        float angryWidth = imageHeight * angryAspectRatio;
        float birdsWidth = imageHeight * birdsAspectRatio;

        float angryX = screenWidth / 2f - (angryWidth + birdsWidth) / 2 - 20;
        float birdsX = angryX + angryWidth + 40;

        float imageY = screenHeight - imageHeight - 40;

        batch.draw(angry, angryX, imageY, angryWidth, imageHeight);
        batch.draw(birds, birdsX, imageY, birdsWidth, imageHeight);

        if (imageHeight < 150f) {
            imageHeight += animationSpeed;
        }
        if (allBirdsHeight < maxAllBirdsHeight) {
            allBirdsHeight += animationSpeed;
        }

        float allBirdsAspectRatio = allBirds.getWidth() / (float) allBirds.getHeight();
        float allBirdsWidth = allBirdsHeight * allBirdsAspectRatio;

        float allBirdsX = screenWidth / 2f - allBirdsWidth / 2 - 20;
        float allBirdsY = imageY - allBirdsHeight + 70;

        batch.draw(allBirds, allBirdsX, allBirdsY, allBirdsWidth, allBirdsHeight);

        batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
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
        batch.dispose();
        stage.dispose();
        backgroundSound.dispose();
        mouseClick.dispose();
    }
}
