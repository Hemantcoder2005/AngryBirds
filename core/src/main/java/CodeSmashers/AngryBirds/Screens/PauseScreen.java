package CodeSmashers.AngryBirds.Screens;

import CodeSmashers.AngryBirds.AudioManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class PauseScreen implements Screen {
    private final GamePlay level;
    private final Stage stage;
    private final Batch batch;
    private Texture blackScreen;
    private Texture playTexture;
    private Texture saveTexture;
    private Texture soundTexture;
    private Texture backTexture;
    private Texture nextTexture;
    private Texture replayTexture;
    private Texture muteSoundTexture;
    private Image soundButton;
    private Image mutedButton;
    public PauseScreen(GamePlay level) {
        this.level = level;
        this.batch = new SpriteBatch();
        this.stage = new Stage(new ScreenViewport());
        loadAssets();
        createButtons();
    }

    private void loadAssets() {
        blackScreen = level.game.getAssets().getTexture("GamePlay/Levels/black_screen.png");
        playTexture = level.game.getAssets().getTexture("GamePlay/Levels/play.png");
        saveTexture = level.game.getAssets().getTexture("GamePlay/Levels/save.png");
        soundTexture = level.game.getAssets().getTexture("GamePlay/Levels/sound.png");
        backTexture = level.game.getAssets().getTexture("GamePlay/Levels/back.png");
        nextTexture = level.game.getAssets().getTexture("GamePlay/Levels/next.png");
        replayTexture = level.game.getAssets().getTexture("GamePlay/Levels/replay.png");
        muteSoundTexture = level.game.getAssets().getTexture("GamePlay/Levels/MuteButton.png");
    }

    private void createButtons() {
        float centerX = Gdx.graphics.getWidth() / 2f;
        float centerY = Gdx.graphics.getHeight() / 2f;

        Image playButton = createButton(playTexture, centerX - 160, centerY - 100);
        Image saveButton = createButton(saveTexture, centerX - 40, centerY - 100);
        soundButton = createButton(soundTexture, centerX + 80, centerY - 100);
        mutedButton = createButton(muteSoundTexture, centerX + 80, centerY - 100);
        mutedButton.setVisible(false);
        Image backButton = createButton(backTexture, centerX - 160, centerY - 200);
        Image replayButton = createButton(replayTexture, centerX - 40, centerY - 200);
        Image nextButton = createButton(nextTexture, centerX + 80, centerY - 200);

        stage.addActor(playButton);
        stage.addActor(saveButton);
        stage.addActor(soundButton);
        stage.addActor(mutedButton);
        stage.addActor(backButton);
        stage.addActor(replayButton);
        stage.addActor(nextButton);
    }

    private Image createButton(Texture texture, float x, float y) {
        Image button = new Image(texture);
        float buttonWidth = 100;
        float buttonHeight = 100;
        button.setSize(buttonWidth, buttonHeight);
        button.setPosition(x, y);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                handleButtonClick(texture);
            }
        });
        return button;
    }

    private void handleButtonClick(Texture texture) {
        level.mouseClick.playSoundEffect();
        if (texture == playTexture) {
            level.game.getMuliplexer().removeProcessor(stage);
            level.game.getMuliplexer().addProcessor(level.stage);
            level.game.getMuliplexer().addProcessor(level.gamePlayInput);
            level.isPaused = false;
        } else if (texture == saveTexture) {
            System.out.println("Saving");
        } else if (texture == soundTexture) {
            AudioManager.getCurrentMusic().toggleMuteBackgroundMusic(level.game.getGlobalInputHandler().isMuted());
            soundButton.setVisible(false);
            mutedButton.setVisible(true);
        } else if (texture == muteSoundTexture) {
            AudioManager.getCurrentMusic().toggleMuteBackgroundMusic(level.game.getGlobalInputHandler().isMuted());
            soundButton.setVisible(true);
            mutedButton.setVisible(false);
        } else if (texture == backTexture) {
            level.game.getState().switchScreen(new LevelSelector(level.game));
        } else if (texture == nextTexture) {
            if (level.levelNum == level.game.getAssets().levels) {
                level.game.getState().switchScreen(new LevelSelector(level.game));
            } else {
                level.game.getState().switchScreen(new GamePlay(level.game, level.levelNum + 1));
            }
        } else if (texture == replayTexture) {
            level.game.getState().switchScreen(new GamePlay(level.game, level.levelNum));
        }
    }

    @Override
    public void show() {
        level.game.getMuliplexer().addProcessor(stage);
        System.out.println("added..........");
    }

    @Override
    public void render(float delta) {
        batch.begin();
        float x = (float) (Gdx.graphics.getWidth() - blackScreen.getWidth()) / 2;
        float y = (float) (Gdx.graphics.getHeight() - blackScreen.getHeight()) / 2;
        batch.draw(blackScreen, x, y);
        batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
        level.game.getMuliplexer().removeProcessor(stage);
    }

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
    }
}
