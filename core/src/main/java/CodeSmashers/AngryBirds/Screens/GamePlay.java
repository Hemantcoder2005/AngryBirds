package CodeSmashers.AngryBirds.Screens;

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
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Json;

import java.util.HashMap;

public class GamePlay implements Screen {
    private LevelCache levelCache;
    private Texture background;
    private GameAssetManager assetManager;
    private SpriteBatch batch;
    private HashMap<String, Texture> textures;
    private Main game;
    public float gravity = -100.6f;
    public static final float PPM = 1f;
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private float windSpeed;

    public GamePlay(Main game, int levelNumber) {
        this.game = game;
        this.assetManager = game.getAssets();
        this.levelCache = new LevelCache();
        this.batch = new SpriteBatch();
        this.textures = new HashMap<>();
        this.windSpeed = 0.0f;
        this.world = new World(new Vector2(windSpeed, gravity), true);
        this.debugRenderer = new Box2DDebugRenderer();
        loadLevel(levelNumber);
        this.background = assetManager.getTexture(levelCache.getBackground());
        loadAllTextures();
        createBirdBodies();
        createFloor();
    }

    private void loadAllTextures() {
        for (Bird bird : levelCache.getBirds()) {
            loadTexture(bird.getImgPath(), bird);
        }
        for (Pig pig : levelCache.getPigs()) {
            loadTexture(pig.getImgPath(), pig);
        }
        for (Surroundings surroundings : levelCache.getComponents()) {
            System.out.println(surroundings.getImgPath());
            loadTexture(surroundings.getImgPath(), surroundings);
        }
    }

    private void loadTexture(String imgPath, Object item) {
        Texture texture = assetManager.getTexture(imgPath);
        if (item instanceof Bird) {
            Bird bird = (Bird) item;
            bird.setSprite(new Sprite(texture));
            bird.getSprite().setSize(bird.getWidth() * bird.getScaleFactor() / PPM, bird.getHeight() * bird.getScaleFactor() / PPM);
            bird.getSprite().setOriginCenter();
        } else if (item instanceof Pig) {
            Pig pig = (Pig) item;
            pig.setSprite(new Sprite(texture));
            pig.getSprite().setSize(pig.getWidth() * pig.getScaleFactor() / PPM, pig.getHeight() * pig.getScaleFactor() / PPM);
            pig.getSprite().setOriginCenter();
        } else if (item instanceof Surroundings) {
            Surroundings surroundings = (Surroundings) item;
            surroundings.setSprite(new Sprite(texture));
            surroundings.getSprite().setSize(surroundings.getWidth() * surroundings.getScaleFactor() / PPM, surroundings.getHeight() * surroundings.getScaleFactor() / PPM);
            surroundings.getSprite().setOriginCenter();
        }
    }

    private void loadLevel(int levelNum) {
        FileHandle fileHandle = Gdx.files.local("cache/" + levelNum + ".json");
        Json json = new Json();
        json.setSerializer(LevelCache.class, new LevelCacheSerializer());
        levelCache = json.fromJson(LevelCache.class, fileHandle);
    }

    private void createBirdBodies() {
        for (Bird bird : levelCache.getBirds()) {
            createBodyForBird(bird);
        }
        for (Pig pig : levelCache.getPigs()) {
            createBodyForPig(pig);
        }
        for (Surroundings surroundings : levelCache.getComponents()) {
            createBodyForSurroundings(surroundings);
        }
    }

    private void createBodyForBird(Bird bird) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(bird.getX() / PPM, bird.getY() / PPM);
        Body body = world.createBody(bodyDef);
        createFixture(body, bird.getWidth(), bird.getHeight(), bird.getDensity(), bird.getFriction(), bird.getRestitution(),bird.getShape(),bird.getScaleFactor());
        bird.setBody(body);
    }

    private void createBodyForPig(Pig pig) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(pig.getX() / PPM, pig.getY() / PPM);
        Body body = world.createBody(bodyDef);
        createFixture(body, pig.getWidth(), pig.getHeight(), pig.getDensity(), pig.getFriction(), pig.getRestitution(),pig.getShape(),pig.getScaleFactor());
        pig.setBody(body);
    }

    private void createBodyForSurroundings(Surroundings surroundings) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(surroundings.getX() / PPM, surroundings.getY() / PPM);
        Body body = world.createBody(bodyDef);
        createFixture(body, surroundings.getWidth(), surroundings.getHeight(), surroundings.getDensity(), surroundings.getFriction(), surroundings.getRestitution(),surroundings.getShape(),surroundings.getScaleFactor());
        surroundings.setBody(body);
    }

    private void createFixture(Body body, float width, float height, float density, float friction, float restitution, String shapeType, float scaleFactor) {
        FixtureDef fixtureDef = new FixtureDef();

        // Scale dimensions before applying to shape
        float scaledWidth = width*scaleFactor;
        float scaledHeight = height*scaleFactor;

        if ("circle".equals(shapeType)) {
            CircleShape shape = new CircleShape();
            shape.setRadius((scaledWidth / 2) / PPM); // Use scaled width for circle radius
            fixtureDef.shape = shape;
        } else if ("rectangle".equals(shapeType)) {
            PolygonShape shape = new PolygonShape();
            shape.setAsBox((scaledWidth / 2) / PPM, (scaledHeight / 2) / PPM); // Use scaled dimensions
            fixtureDef.shape = shape;
        } else if ("triangle".equals(shapeType)) {
            PolygonShape shape = new PolygonShape();
            Vector2[] vertices = new Vector2[3];

            // Scale and set vertices for triangle
            vertices[0] = new Vector2(-scaledWidth / 2 / PPM, -scaledHeight / 2 / PPM);
            vertices[1] = new Vector2(scaledWidth / 2 / PPM, -scaledHeight / 2 / PPM);
            vertices[2] = new Vector2(0, scaledHeight / 2 / PPM);

            shape.set(vertices);
            fixtureDef.shape = shape;
        } else {
            throw new IllegalArgumentException("Unsupported shape type: " + shapeType);
        }

        fixtureDef.density = density;
        fixtureDef.friction = friction;
        fixtureDef.restitution = restitution;
        body.createFixture(fixtureDef);
    }


    private void createFloor() {
        BodyDef floorDef = new BodyDef();
        floorDef.type = BodyDef.BodyType.StaticBody;
        floorDef.position.set(0, 0);

        Body floorBody = world.createBody(floorDef);
        EdgeShape floorShape = new EdgeShape();
        floorShape.set(new Vector2(0, levelCache.getFloorY() / PPM), new Vector2(Gdx.graphics.getWidth() / PPM, levelCache.getFloorY() / PPM));

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = floorShape;
        fixtureDef.friction = 0.5f;

        floorBody.createFixture(fixtureDef);
        floorShape.dispose();
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        world.step(1 / 60f, 6, 2);

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        renderBirds();
        renderPigs();
        renderSurroundings();
        batch.end();

        // Uncomment to enable debug rendering
        debugRenderer.render(world, batch.getProjectionMatrix().cpy().scale(PPM, PPM, 0));
    }

    private void renderBirds() {
        for (Bird bird : levelCache.getBirds()) {
            Sprite sprite = bird.getSprite();
            sprite.setPosition(bird.getBody().getPosition().x * PPM - sprite.getWidth() / 2,
                bird.getBody().getPosition().y * PPM - sprite.getHeight() / 2);
            sprite.setRotation((float) Math.toDegrees(bird.getBody().getAngle()));
            sprite.draw(batch);
        }
    }

    private void renderPigs() {
        for (Pig pig : levelCache.getPigs()) {
            Sprite sprite = pig.getSprite();
            sprite.setPosition(pig.getBody().getPosition().x * PPM - sprite.getWidth() / 2,
                pig.getBody().getPosition().y * PPM - sprite.getHeight() / 2);
            sprite.setRotation((float) Math.toDegrees(pig.getBody().getAngle()));
            sprite.draw(batch);
        }
    }

    private void renderSurroundings() {
        for (Surroundings surroundings : levelCache.getComponents()) {
            Sprite sprite = surroundings.getSprite();
            sprite.setPosition(surroundings.getBody().getPosition().x * PPM - sprite.getWidth() / 2,
                surroundings.getBody().getPosition().y * PPM - sprite.getHeight() / 2);
            sprite.setRotation((float) Math.toDegrees(surroundings.getBody().getAngle()));
            sprite.draw(batch);
        }
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
        background.dispose();
        world.dispose();
        debugRenderer.dispose();
        for (Texture texture : textures.values()) {
            texture.dispose();
        }
    }
}
