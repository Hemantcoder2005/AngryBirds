package CodeSmashers.AngryBirds.Screens;

import CodeSmashers.AngryBirds.AudioManager;
import CodeSmashers.AngryBirds.HelperClasses.Level;
import CodeSmashers.AngryBirds.HelperClasses.LevelCache;
import CodeSmashers.AngryBirds.HelperClasses.SoundEffects;
import CodeSmashers.AngryBirds.Main;
import CodeSmashers.AngryBirds.Serializer.LevelCacheSerializer;
import CodeSmashers.AngryBirds.Serializer.LevelSerializer;
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

public class WinScreen implements Screen {
    private final GamePlay level;
    private final Stage stage;
    private final Batch batch;
    private Texture star1;
    private Texture star2;
    private Texture star3;
    private Texture nextTexture;
    private Texture replayTexture;
    private Texture blackScreen;
    private boolean isStageAdded;
    private boolean isSaved  = false;
    public WinScreen(GamePlay level) {
        this.level = level;
        this.batch = new SpriteBatch();
        this.stage = new Stage(new ScreenViewport());
        loadAssets();
        createButtons();
    }

    private void loadAssets() {
        star1 = level.game.getAssets().getTexture("GamePlay/Levels/1Star.png");
        star2 = level.game.getAssets().getTexture("GamePlay/Levels/2Star.png");
        star3 = level.game.getAssets().getTexture("GamePlay/Levels/3Star.png");
        nextTexture = level.game.getAssets().getTexture("GamePlay/Levels/next.png");
        replayTexture = level.game.getAssets().getTexture("GamePlay/Levels/replay.png");
    }

    private void createButtons() {
        float centerX = Gdx.graphics.getWidth() / 2f;
        float centerY = Gdx.graphics.getHeight() / 2f;

        Image backButton = createButton(replayTexture, centerX - 120, centerY - 150);
        Image replayButton = createButton(nextTexture, centerX  + 20, centerY - 150);

        stage.addActor(backButton);
        stage.addActor(replayButton);
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

    private void handleButtonClick(Texture texture) {
        level.mouseClick.playSoundEffect();

        if (texture == nextTexture) {
            if (level.levelNum == level.game.getAssets().levels) {
                level.game.getState().switchScreen(new LevelSelector(level.game));
            } else {
                level.game.getState().switchScreen(new GamePlay(level.game, level.levelNum + 1,false));
            }
        }
         else if (texture == replayTexture) {
            level.game.getState().switchScreen(new GamePlay(level.game, level.levelNum,false));
        }
    }

    @Override
    public void show() {
        level.game.getMuliplexer().addProcessor(stage);
        System.out.println("added..........");
    }

    @Override
    public void render(float delta) {
        if(!isSaved){
            SoundEffects.playLevelCompleted();
            saveLevels();
            isSaved = true;
        }
        if(!isStageAdded){
            level.game.getMuliplexer().addProcessor(stage);
            isStageAdded = true;
        }
        if(level.stars == 1)blackScreen = star1;
        else if(level.stars == 2)blackScreen = star2;
        else if(level.stars == 3)blackScreen =star3;
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
        isStageAdded = false;
    }

    @Override
    public void dispose() {
        level.game.getMuliplexer().removeProcessor(stage);
        batch.dispose();
        stage.dispose();
    }
    private void saveLevels() {
        Level levelT = level.game.getAssets().LevelChart.get("level" + level.levelNum);
        levelT.locked = false;
        levelT.stars = level.stars;
        level.game.getAssets().saveUserLevels();
    }


}
