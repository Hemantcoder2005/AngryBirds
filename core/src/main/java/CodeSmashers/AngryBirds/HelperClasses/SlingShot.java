package CodeSmashers.AngryBirds.HelperClasses;

import CodeSmashers.AngryBirds.Main;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SlingShot implements Screen {
    Texture slingShotTexture;
    Sprite slingShotSprite;
    Main game;
    float yPos;
    float xPos;
    SpriteBatch batch;  // SpriteBatch declared here

    public SlingShot(Main game, float xPos, float yPos) {
        this.game = game;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    @Override
    public void show() {
        slingShotTexture = game.getAssets().getTexture("GamePlay/Levels/slingshot.png");
        slingShotSprite = new Sprite(slingShotTexture);
        slingShotSprite.setPosition(xPos, yPos);

        batch = new SpriteBatch();  // Initialize SpriteBatch here
    }

    @Override
    public void render(float delta) {
        batch.begin();  // Begin drawing with batch
        slingShotSprite.draw(batch);  // Draw the slingshot sprite
        batch.end();  // End drawing
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();  // Dispose of the batch when the screen is disposed
    }
}
