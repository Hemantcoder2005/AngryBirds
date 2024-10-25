package CodeSmashers.AngryBirds.Screens;

import CodeSmashers.AngryBirds.AudioManager;
import CodeSmashers.AngryBirds.AudioPlayer;
import CodeSmashers.AngryBirds.GameAssetManager;
import CodeSmashers.AngryBirds.HelperClasses.Bird;
import CodeSmashers.AngryBirds.HelperClasses.LevelCache;
import CodeSmashers.AngryBirds.HelperClasses.Pig;
import CodeSmashers.AngryBirds.HelperClasses.Surroundings;
import CodeSmashers.AngryBirds.Main;
import CodeSmashers.AngryBirds.Serializer.LevelCacheSerializer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Json;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class GamePlay implements Screen {
    private LevelCache levelCache;
    private Texture background;
    private GameAssetManager assetManager;
    private SpriteBatch batch;
    private HashMap<String,Texture> Textures;
    private Main game;

    public GamePlay(Main game,int LevelNumber){
        this.game = game;
        this.assetManager = game.getAssets();
        this.levelCache = new LevelCache();
        this.batch = new SpriteBatch();
        this.Textures = new HashMap<>();
        loadLevel(LevelNumber);
        System.out.println(levelCache.getBackground());
        this.background = game.getAssets().getTexture(levelCache.getBackground());
        loadAllTextures();
    }
    private void loadAllTextures() {
        for (Bird bird : levelCache.getBirds()) {
            Textures.put(bird.getImgPath(), game.getAssets().getTexture(bird.getImgPath()));

        }

        for (Pig pig : levelCache.getPigs()) {
            Textures.put(pig.getImgPath(), game.getAssets().getTexture(pig.getImgPath()));
        }

        for (Surroundings surr : levelCache.getComponents()) {
            Textures.put(surr.getImgPath(), game.getAssets().getTexture(surr.getImgPath()));
        }

        System.out.println("All Textures Loaded for level");
    }

    private void loadLevel(int levelNum){
        FileHandle fileHandle = Gdx.files.local( "cache/"+levelNum+".json");
        Json json = new Json();
        json.setSerializer(LevelCache.class,new LevelCacheSerializer());
        System.out.println("I am data form game play");
        levelCache = json.fromJson(LevelCache.class, fileHandle);

        System.out.println("Loaded Level: " + levelNum);
        System.out.println("Background: " + levelCache.getBackground());
        System.out.println("Number of Birds: " + levelCache.getBirds().size());
        System.out.println("Number of Pigs: " + levelCache.getPigs().size());
        System.out.println("Number of Components: " + levelCache.getComponents().size());
    }

    @Override
    public void show() {

    }


    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Begin the batch for drawing
        batch.begin();

        // Draw the background
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        float scaleFactor = 0.2f;

        // Draw birds
        for (Bird bird : levelCache.getBirds()) {
            Texture birdTexture = Textures.get(bird.getImgPath());
            float birdWidth = birdTexture.getWidth() * scaleFactor;
            float birdHeight = birdTexture.getHeight() * scaleFactor;

            // Calculate the center for rotation
            float originX = birdWidth / 2; // Center X based on width
            float originY = birdHeight / 2; // Center Y based on height

            batch.draw(
                birdTexture,
                bird.getX(), bird.getY(), // Position to draw the bird
                originX, originY, // Set the origin for rotation to the center of the bird
                birdWidth,
                birdHeight,
                1, 1,
                bird.getAngle(), // Use the bird's angle for rotation
                0, 0,
                birdTexture.getWidth(), birdTexture.getHeight(),
                false, false
            );

            // Optional: Update bird's angle if needed
            bird.setAngle(bird.getAngle() + 1);
        }

        // Draw pigs
        for (Pig pig : levelCache.getPigs()) {
            Texture pigTexture = Textures.get(pig.getImgPath());
            float pigWidth = pigTexture.getWidth() * scaleFactor;
            float pigHeight = pigTexture.getHeight() * scaleFactor;

            // Calculate the center for rotation
            float originX = pigWidth / 2; // Center X based on width
            float originY = pigHeight / 2; // Center Y based on height

            batch.draw(
                pigTexture,
                pig.getX(), pig.getY(), // Position to draw the pig
                originX, originY, // Set the origin for rotation to the center of the pig
                pigWidth,
                pigHeight,
                1, 1,
                pig.getAngle(), // Use the pig's angle for rotation
                0, 0,
                pigTexture.getWidth(), pigTexture.getHeight(),
                false, false
            );

            // Optional: Update pig's angle if needed
            pig.setAngle(pig.getAngle() + 5);
        }

        // Draw surroundings
        for (Surroundings surr : levelCache.getComponents()) {
            Texture surrTexture = Textures.get(surr.getImgPath());
            float surrWidth = surrTexture.getWidth() * scaleFactor;
            float surrHeight = surrTexture.getHeight() * scaleFactor;

            // Calculate the center for rotation
            float originX = surrWidth / 2; // Center X based on width
            float originY = surrHeight / 2; // Center Y based on height

            batch.draw(
                surrTexture,
                surr.getX(), surr.getY(), // Position to draw the surroundings
                originX, originY, // Set the origin for rotation to the center of the surroundings
                surrWidth,
                surrHeight,
                1, 1,
                0, // Assuming surroundings don't rotate
                0, 0,
                surrTexture.getWidth(), surrTexture.getHeight(),
                false, false
            );
        }

        // End the batch after all drawing is done
        batch.end();
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
        for (Texture texture : Textures.values()) {
            texture.dispose();
        }
    }
}
