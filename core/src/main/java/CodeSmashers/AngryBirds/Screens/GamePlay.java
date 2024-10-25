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
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Scale factor for birds, pigs, and surroundings
        float birdScaleFactor = 0.9f; // Adjust this value as needed
        float pigScaleFactor = 0.2f; // Adjust this value to change pig size
        float surrScaleFactor = 3.0f; // Adjust this value for surroundings

        for (Bird bird : levelCache.getBirds()) {
            Texture birdTexture = Textures.get(bird.getImgPath());
            float originalWidth = birdTexture.getWidth();
            float originalHeight = birdTexture.getHeight();
            float aspectRatio = originalWidth / originalHeight;

            float desiredHeight = bird.getHeight() * birdScaleFactor;
            float birdWidth = desiredHeight * aspectRatio;

            float originX = birdWidth / 2;
            float originY = desiredHeight / 2;

            batch.draw(
                birdTexture,
                bird.getX(), bird.getY(),
                originX, originY,
                birdWidth,
                desiredHeight,
                1, 1,
                bird.getAngle(),
                0, 0,
                (int)originalWidth, (int)originalHeight,
                false, false
            );

            bird.setAngle(bird.getAngle() + 1);
        }

        for (Pig pig : levelCache.getPigs()) {
            Texture pigTexture = Textures.get(pig.getImgPath());
            float originalWidth = pigTexture.getWidth();
            float originalHeight = pigTexture.getHeight();
            float aspectRatio = originalWidth / originalHeight;

            float pigWidth = originalWidth * pigScaleFactor; // Scale width
            float pigHeight = pigWidth / aspectRatio; // Maintain aspect ratio

            float originX = pigWidth / 2;
            float originY = pigHeight / 2;

            batch.draw(
                pigTexture,
                pig.getX(), pig.getY(),
                originX, originY,
                pigWidth,
                pigHeight,
                1, 1,
                pig.getAngle(),
                0, 0,
                (int)originalWidth,(int) originalHeight,
                false, false
            );

            pig.setAngle(pig.getAngle() + 5);
        }

        for (Surroundings surr : levelCache.getComponents()) {
            Texture surrTexture = Textures.get(surr.getImgPath());
            float originalWidth = surrTexture.getWidth();
            float originalHeight = surrTexture.getHeight();
            float aspectRatio = originalWidth / originalHeight;

            float surrWidth = originalWidth * surrScaleFactor; // Scale width
            float surrHeight = surrWidth / aspectRatio; // Maintain aspect ratio

            float originX = surrWidth / 2;
            float originY = surrHeight / 2;

            batch.draw(
                surrTexture,
                surr.getX(), surr.getY(),
                originX, originY,
                surrWidth,
                surrHeight,
                1, 1,
                0,
                0, 0,
                (int)originalWidth, (int)originalHeight,
                false, false
            );
        }

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
