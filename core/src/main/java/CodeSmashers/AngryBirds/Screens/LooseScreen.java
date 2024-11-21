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

public class LooseScreen implements Screen {
    private final GamePlay level;
    private final Stage stage;
    private final Batch batch;
    private Texture blackScreen;
    private Texture backTexture;
    private Texture replayTexture;
    private boolean isStageAdded = false;
    public LooseScreen(GamePlay level) {
        this.level = level;
        this.batch = new SpriteBatch();
        this.stage = new Stage(new ScreenViewport());
        loadAssets();
        createButtons();
    }

    private void loadAssets() {
        blackScreen = level.game.getAssets().getTexture("GamePlay/Levels/lost.png");
        backTexture = level.game.getAssets().getTexture("GamePlay/Levels/back.png");
        replayTexture = level.game.getAssets().getTexture("GamePlay/Levels/replay.png");
    }

    private void createButtons() {
        float centerX = Gdx.graphics.getWidth() / 2f;
        float centerY = Gdx.graphics.getHeight() / 2f;

        Image backButton = createButton(backTexture, centerX -150, centerY - 200);
        Image replayButton = createButton(replayTexture, centerX +20 , centerY - 200);

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

        if (texture == backTexture) {
            level.game.getState().switchScreen(new LevelSelector(level.game));
        }
        else if (texture == replayTexture) {
            level.game.getState().switchScreen(new GamePlay(level.game, level.levelNum));
        }
    }

    @Override
    public void show() {
        System.out.println("added..........");
    }

    @Override
    public void render(float delta) {
        if(!isStageAdded){
            level.game.getMuliplexer().addProcessor(stage);
            isStageAdded = true;
        }
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
}
