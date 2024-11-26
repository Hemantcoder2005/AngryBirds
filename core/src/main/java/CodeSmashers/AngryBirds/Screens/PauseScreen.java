package CodeSmashers.AngryBirds.Screens;

import CodeSmashers.AngryBirds.AudioManager;
import CodeSmashers.AngryBirds.HelperClasses.Bird;
import CodeSmashers.AngryBirds.HelperClasses.LevelCache;
import CodeSmashers.AngryBirds.Serializer.LevelCacheSerializer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class PauseScreen implements Screen {
    private final GamePlay level;
    private Stage stage;
    private Batch batch;
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

        Image playButton = createButton(playTexture, centerX - 160, centerY - 120);
        Image saveButton = createButton(saveTexture, centerX - 40, centerY - 120);
        soundButton = createButton(soundTexture, centerX + 80, centerY - 120);
        mutedButton = createButton(muteSoundTexture, centerX + 80, centerY - 120);
        mutedButton.setVisible(false);
        Image backButton = createButton(backTexture, centerX - 160, centerY - 220);
        Image replayButton = createButton(replayTexture, centerX - 40, centerY - 220);
        Image nextButton = createButton(nextTexture, centerX + 80, centerY - 220);

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
//        float buttonWidth = 100;
//        float buttonHeight = 100;
//        button.setSize(buttonWidth, buttonHeight);
        button.setPosition(x, y);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                handleButtonClick(texture);
            }
        });
        return button;
    }
    private void saveLevel(int levelNum) {
        if(level.create) {
            level.saveLevel(levelNum);
        }
        else {
        // moving data from isUsed to birds list
        for (Bird bird: level.birdsUsed){
            System.out.println(bird.getImgPath());
            level.levelCache.getBirds().add(bird);
        }
        FileHandle fileHandle;


            fileHandle = Gdx.files.local("assets/savedGame/" + levelNum + ".json");

            // Create a Json instance
            Json json = new Json();
            json.setOutputType(JsonWriter.OutputType.json);
            json.setSerializer(LevelCache.class, new LevelCacheSerializer());

            // Convert the LevelCache object to JSON and write it to the file
            String jsonString = json.toJson(level.levelCache);
            jsonString = level.formatJson(jsonString);
            fileHandle.writeString(jsonString, false);
            for (Bird bird: level.birdsUsed){
                level.levelCache.getBirds().remove(bird);
            }
        }

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
            saveLevel(level.levelNum);
            System.out.println("Saved");
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
            if (level.levelNum == level.game.getAssets().levels-1) {
                level.game.getState().switchScreen(new LevelSelector(level.game));
            } else {
                level.game.getState().switchScreen(new GamePlay(level.game, level.levelNum + 1,false));
            }
        } else if (texture == replayTexture) {
            if(level.create)level.game.getState().switchScreen(new GamePlay(level.game, level.levelNum,true));
            else level.game.getState().switchScreen(new GamePlay(level.game, level.levelNum,false));
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

    }

    @Override
    public void dispose() {
        level.game.getMuliplexer().removeProcessor(stage);
        if(batch!=null){
            batch.dispose();
            batch = null;
        }
        if(stage!=null){
            stage.dispose();
            stage = null;
        }
    }
}
