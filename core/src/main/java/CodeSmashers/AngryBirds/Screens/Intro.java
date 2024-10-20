package CodeSmashers.AngryBirds.Screens;

import CodeSmashers.AngryBirds.Main;
import CodeSmashers.AngryBirds.GameAssetManager;
import CodeSmashers.AngryBirds.VideoPlayer;
import CodeSmashers.AngryBirds.inputHandler.GlobalInputHandler;
import CodeSmashers.AngryBirds.inputHandler.IntroInput;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Intro implements Screen {
    private final VideoPlayer intro;
    private final Main game;
    private final GameAssetManager assetManager;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch spriteBatch;
    private BitmapFont font;
    private float progress;
    private float loadingDelay = 9.0f;
    private float elapsedTime = 0.0f;
    private boolean loadingStarted = false;

    private float flickerDelay = 0.5f;
    private float flickerElapsedTime = 0;
    private boolean showFlickerText = false;
    private IntroInput input = new IntroInput(this);
    public boolean skip = false;
    public Intro(Main game) {
        this.game = game;
        this.assetManager = new GameAssetManager();
        this.intro = new VideoPlayer("videos/intro.webm");
        game.getMuliplexer().addProcessor(input);
        game.setAssets(assetManager);
    }

    @Override
    public void show() {
        intro.create();
        shapeRenderer = new ShapeRenderer();
        spriteBatch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().setScale(2.0f);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        intro.mute(game.getGlobalInputHandler().isMuted());
        intro.render();
        elapsedTime += delta;
        drawControlsMessage();
        if (elapsedTime < loadingDelay) {
            return;
        }

        if (!loadingStarted) {
            assetManager.loadAssetsFromFile();
            loadingStarted = true;
        }

        progress = assetManager.getProgress();
        if (assetManager.update()){
            if(intro.isVideoCompleted() || skip){
                game.getState().switchScreen(new MainMenuScreen());
                return;
            }
        }
        if (progress < 1) {
            drawLoadingBar(progress);
        } else {
            progress = 1;


            flickerElapsedTime += delta;
            if (flickerElapsedTime >= flickerDelay) {
                showFlickerText = !showFlickerText;
                flickerElapsedTime = 0;
            }
            if (showFlickerText) {
                drawLoadingMessage("Press 'Enter' to enter the game.");
            }

        }
    }

    private void drawLoadingMessage(String message) {
        spriteBatch.begin();
        font.setColor(Color.RED);

        // Use GlyphLayout to measure the width of the text
        GlyphLayout layout = new GlyphLayout(font, message);
        float textWidth = layout.width; // Get the actual width of the text
        float textX = (Gdx.graphics.getWidth() - textWidth) / 2; // Center the text horizontally
        float textY = ((float) Gdx.graphics.getHeight() / 2) -270; // Adjust the vertical position

        // Draw the message
        font.draw(spriteBatch, layout, textX, textY);
        spriteBatch.end();
    }
    private void drawControlsMessage() {
        spriteBatch.begin();

        // Set the color and font size
        font.setColor(Color.WHITE); // You can change this to any color you prefer
        font.getData().setScale(1.5f); // Adjust the font size

        // Create the control message
        String message = "M is for Mute   |   Q is for Quit";

        // Calculate the width and position to center the message
        GlyphLayout layout = new GlyphLayout(font, message);
        float textWidth = layout.width;
        float textX = (Gdx.graphics.getWidth() - textWidth) / 2;
        float textY = 50; // Adjust this to change vertical position

        // Draw the message
        font.draw(spriteBatch, layout, textX, textY);

        spriteBatch.end();
    }
    private void drawLoadingBar(float progress) {
        float barWidth = 600;
        float barHeight = 30;
        float barX = (Gdx.graphics.getWidth() - barWidth) / 2;
        float barY = (float) Gdx.graphics.getHeight() / 4;

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.DARK_GRAY);
        shapeRenderer.rect(barX, barY, barWidth, barHeight);
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(barX, barY, barWidth * progress, barHeight);
        shapeRenderer.end();

        spriteBatch.begin();
        font.setColor(Color.WHITE);
        String percentageText = String.format("Loading: %.0f%%", progress * 100);
        float textWidth = font.getRegion().getTexture().getWidth() * percentageText.length() / 2f;
        float textX = barX + barWidth/2 - 100;
        float textY = barY + barHeight / 2 + font.getCapHeight() / 2;
        font.draw(spriteBatch, percentageText, textX, textY);
        spriteBatch.end();
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
        game.getMuliplexer().removeProcessor(input);

        if (intro != null) {
            intro.dispose();
        }

        if (spriteBatch != null) {
            spriteBatch.dispose();
            spriteBatch = null;
        }

        if (shapeRenderer != null) {
            shapeRenderer.dispose();
            shapeRenderer = null;
        }

        if (font != null) {
            font.dispose();
            font = null;
        }
    }

}
