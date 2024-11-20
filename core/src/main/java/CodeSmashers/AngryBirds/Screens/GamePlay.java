package CodeSmashers.AngryBirds.Screens;

import CodeSmashers.AngryBirds.AudioPlayer;
import CodeSmashers.AngryBirds.GameAssetManager;
import CodeSmashers.AngryBirds.HelperClasses.Bird;
import CodeSmashers.AngryBirds.HelperClasses.LevelCache;
import CodeSmashers.AngryBirds.HelperClasses.Pig;
import CodeSmashers.AngryBirds.HelperClasses.Surroundings;
import CodeSmashers.AngryBirds.Main;
import CodeSmashers.AngryBirds.Serializer.LevelCacheSerializer;
import CodeSmashers.AngryBirds.inputHandler.MyContactListener;
import com.badlogic.gdx.*;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class GamePlay implements Screen {
    public int levelNum ;
    public LevelCache levelCache;
    private Texture background;
    private GameAssetManager assetManager;
    public SpriteBatch batch;
    private HashMap<String, Texture> textures;
    public Main game;
    public float gravity = -120.6f;
    public static final float PPM = 7f;
    private static World world;
    private Box2DDebugRenderer debugRenderer;
    private float windSpeed;
    private PauseScreen pause;
    public Boolean isPaused;
    private String BASE_DIR = "Gameplay/";
    private Texture pauseTexture;
    private ImageButton pauseButton;
    public AudioPlayer mouseClick;
    public Stage stage;
    private BitmapFont font;
    private GlyphLayout layout;
    private Boolean won;
    private Boolean loose;
    private Texture slingShotTexture;
    private Texture wonTexture;
    private Texture lostTexture;
    private float slingShotX;
    private boolean isBirdOnSlingShot;
    private int birdPlayed;
    private Bird currentBird;
    long currentTimeMillis;
    private boolean isDraggingBird = false;
    private static final float SPRING_CONSTANT_K = 10.0f; // Adjust as needed
    private Vector2 slingshotAnchor;
    public InputAdapter gamePlayInput;
    public boolean isEditing;
    private Surroundings editableSurrounding;
    private Pig editablePig;
    private float angle;
    private boolean isrenderTrajectory = true;
    private List<Vector2> trajectory;
    private ShapeRenderer shapeRenderer;
    private float currentBirdMass;
    private MyContactListener myContactListener;
    private int PigAvailable;
    private WinScreen winScreen;
    private LooseScreen looseScreen;
    public int stars;
    public GamePlay(Main game, int levelNumber) {
        this.levelNum = levelNumber;
        shapeRenderer = new ShapeRenderer();
        this.game = game;
        this.assetManager = game.getAssets();
        this.levelCache = new LevelCache();
        this.batch = new SpriteBatch();
        this.textures = new HashMap<>();
        this.windSpeed = 0.0f;
        this.world = new World(new Vector2(windSpeed, gravity), true);
        this.myContactListener = new MyContactListener();
        this.debugRenderer = new Box2DDebugRenderer();
        loadLevel(levelNumber);
        this.background = assetManager.getTexture(levelCache.getBackground());
        this.isPaused = false;
        this.pause = new PauseScreen(this);
        this.mouseClick = new AudioPlayer("mouseClicked.wav", game.getAssets(), true);
        this.stage = new Stage(new ScreenViewport());
        this.font = new BitmapFont();  // Or you can use a custom font file
        this.layout = new GlyphLayout();
        this.won = false;
        this.loose  = false;
        this.isBirdOnSlingShot = false;
        loadAllTextures();
        createBirdBodies();
        createFloor();
        createWall();
        createPauseButton();
        this.slingshotAnchor = new Vector2((slingShotX +30) ,levelCache.getFloorY());
        gamePlayInput = initializeInputProcessor();
        this.isEditing = false;
        this.isrenderTrajectory = false;
        world.setContactListener(myContactListener);
        this.PigAvailable = levelCache.getPigs().size();
        System.out.println("Pigs = "+PigAvailable);
        this.winScreen = new WinScreen(this);
        this.looseScreen = new LooseScreen(this);
    }
    private int calculateStars() {
        // Calculate the Pig-to-Bird ratio
        float pigToBirdRatio = (float) levelCache.getPigs().size() / levelCache.getBirds().size();

        // Calculate Bird Efficiency: the ratio of remaining birds to total birds
        float birdEfficiency = (float) (levelCache.getBirds().size() - birdPlayed) / levelCache.getBirds().size();

        // Total Score: A combination of Pig-to-Bird ratio and Bird Efficiency
        float totalScore = pigToBirdRatio * birdEfficiency;

        // Mapping the total score to stars
        if (totalScore >= 0.9f) {
            return 3; // High efficiency, most pigs eliminated with fewer birds
        } else if (totalScore >= 0.7f) {
            return 2; // Good efficiency
        } else {
            return 1; // Low efficiency
        }
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
        slingShotTexture = assetManager.getTexture("GamePlay/Levels/slingshot.png");

    }

    private void loadTexture(String imgPath, Object item) {
        Texture texture = assetManager.getTexture(imgPath);
        if (item instanceof Bird) {
            Bird bird = (Bird) item;
            bird.setSprite(new Sprite(texture));
            bird.getSprite().setSize(bird.getWidth() * bird.getScaleFactor() , bird.getHeight() * bird.getScaleFactor());
            bird.getSprite().setOriginCenter();
            if(slingShotX < bird.getX()) slingShotX = bird.getX();
        } else if (item instanceof Pig) {
            Pig pig = (Pig) item;
            pig.setSprite(new Sprite(texture));
            pig.getSprite().setSize(pig.getWidth() * pig.getScaleFactor() , pig.getHeight() * pig.getScaleFactor());
            pig.getSprite().setOriginCenter();
        } else if (item instanceof Surroundings) {
            Surroundings surroundings = (Surroundings) item;
            surroundings.setSprite(new Sprite(texture));
            surroundings.getSprite().setSize(surroundings.getWidth() * surroundings.getScaleFactor(), surroundings.getHeight() * surroundings.getScaleFactor());
            surroundings.getSprite().setOriginCenter();
        }
    }

    private void loadLevel(int levelNum) {
        FileHandle fileHandle = Gdx.files.internal("cache/" + levelNum + ".json");
        Json json = new Json();
        json.setSerializer(LevelCache.class, new LevelCacheSerializer());
        levelCache = json.fromJson(LevelCache.class, fileHandle);
    }
    private void saveLevel(int levelNum) {
        // Define the file location
        FileHandle fileHandle = Gdx.files.local("cache/" + levelNum + ".json");
        // Create a Json instance
        Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        json.setSerializer(LevelCache.class, new LevelCacheSerializer());

        // Convert the LevelCache object to JSON and write it to the file
        String jsonString = json.toJson(levelCache);
        jsonString = formatJson(jsonString);
        fileHandle.writeString(jsonString, false); // false to overwrite the file
    }
    private String formatJson(String jsonString) {
        return jsonString.replaceAll("\\{", "{\n\t") // Open curly brace
            .replaceAll(",", ",\n\t")  // Commas between key-value pairs
            .replaceAll("\\}", "\n\t}"); // Close curly brace
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
        bodyDef.position.set(bird.getX()/PPM , bird.getY()/PPM);
        Body body = world.createBody(bodyDef);
        System.out.println(bird.getVx());
        body.setLinearVelocity(bird.getVx(), bird.getVy());
        createFixture(body, bird.getWidth(), bird.getHeight(), bird.getDensity(), bird.getFriction(), bird.getRestitution(),bird.getShape(),bird.getScaleFactor()).setUserData(bird);
        bird.setBody(body);
    }

    private void createBodyForPig(Pig pig) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(pig.getX()/PPM , pig.getY()/PPM );
        Body body = world.createBody(bodyDef);
        body.setTransform(new Vector2(body.getPosition().x, body.getPosition().y),body.getAngle());
        createFixture(body, pig.getWidth(), pig.getHeight(), pig.getDensity(), pig.getFriction(), pig.getRestitution(),pig.getShape(),pig.getScaleFactor()).setUserData(pig);
        pig.setBody(body);
    }

    private void createBodyForSurroundings(Surroundings surroundings) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(surroundings.getX()/PPM , surroundings.getY()/PPM );
        Body body = world.createBody(bodyDef);
        createFixture(body, surroundings.getWidth(), surroundings.getHeight(), surroundings.getDensity(), surroundings.getFriction(), surroundings.getRestitution(),surroundings.getShape(),surroundings.getScaleFactor()).setUserData(surroundings);
        surroundings.setBody(body);
    }

    private Fixture createFixture(Body body, float width, float height, float density, float friction, float restitution, String shapeType, float scaleFactor) {
        FixtureDef fixtureDef = new FixtureDef();


        float scaledWidth = width*scaleFactor/PPM;
        float scaledHeight = height*scaleFactor/PPM;

        if ("circle".equals(shapeType)) {
            CircleShape shape = new CircleShape();
            shape.setRadius((scaledWidth / 2) ); // Use scaled width for circle radius
            fixtureDef.shape = shape;
        } else if ("rectangle".equals(shapeType)) {
            PolygonShape shape = new PolygonShape();
            shape.setAsBox((scaledWidth / 2) , (scaledHeight / 2) ); // Use scaled dimensions
            fixtureDef.shape = shape;
        } else if ("triangle".equals(shapeType)) {
            PolygonShape shape = new PolygonShape();
            Vector2[] vertices = new Vector2[3];

            // Scale and set vertices for triangle
            vertices[0] = new Vector2(-scaledWidth / 2 , -scaledHeight / 2 );
            vertices[1] = new Vector2(scaledWidth / 2 , -scaledHeight / 2 );
            vertices[2] = new Vector2(0, scaledHeight / 2);

            shape.set(vertices);
            fixtureDef.shape = shape;
        } else {
            throw new IllegalArgumentException("Unsupported shape type: " + shapeType);
        }

        fixtureDef.density = density;
        fixtureDef.friction = friction;
        fixtureDef.restitution = restitution;
        body.setLinearDamping(0);
       return body.createFixture(fixtureDef);
    }
    private void createPauseButton() {
        pauseTexture = game.getAssets().getTexture("GamePlay/Levels/pause.png");
        Drawable backDrawable = new TextureRegionDrawable(pauseTexture);
        pauseButton = new ImageButton(backDrawable);
        pauseButton.setSize(100, 100);
        pauseButton.setPosition(20, (Gdx.graphics.getHeight() - 120));
        stage.addActor(pauseButton);

        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mouseClick.playSoundEffect();
                System.out.println("Game paused = " + isPaused);
                isPaused = true;
                pause.show();
                game.getMuliplexer().removeProcessor(stage);
                game.getMuliplexer().removeProcessor(gamePlayInput);
            }
        });
    }

//    }

    private void createFloor() {
        BodyDef floorDef = new BodyDef();
        floorDef.type = BodyDef.BodyType.StaticBody;
        floorDef.position.set(0, 0);

        Body floorBody = world.createBody(floorDef);
        EdgeShape floorShape = new EdgeShape();
        floorShape.set(new Vector2(0, levelCache.getFloorY()/PPM ), new Vector2(Gdx.graphics.getWidth() /PPM, levelCache.getFloorY()/PPM));

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = floorShape;
        fixtureDef.friction = 0.5f;

        floorBody.createFixture(fixtureDef);
        floorShape.dispose();

    }
    private void createWall() {
        BodyDef floorDef = new BodyDef();
        floorDef.type = BodyDef.BodyType.StaticBody;
        floorDef.position.set(0, 0);

        Body floorBody = world.createBody(floorDef);
        EdgeShape floorShape = new EdgeShape();
        floorShape.set(new Vector2(Gdx.graphics.getWidth()/PPM, levelCache.getFloorY()/PPM ), new Vector2(Gdx.graphics.getWidth() /PPM, Gdx.graphics.getWidth()/PPM));

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = floorShape;
        fixtureDef.friction = 0.5f;

        floorBody.createFixture(fixtureDef);
        floorShape.dispose();

    }

    @Override
    public void show() {
        game.getMuliplexer().addProcessor(stage);
        game.getMuliplexer().addProcessor(gamePlayInput);

    }

    @Override
    public void render(float delta) {

        if (!isPaused && !won && !loose) {
            // Clear the screen
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            world.step(1 / 60f, 8, 3);

            batch.begin();
            batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            batch.draw(slingShotTexture, slingshotAnchor.x -30,levelCache.getFloorY());
            renderSurroundings();
            renderPigs();
            renderBirds();
            batch.draw(slingShotTexture, 1000,-1000);
            if(isrenderTrajectory && trajectory!= null)renderTrajectory();
            BitmapFont scaledFont = new BitmapFont();
            scaledFont.getData().setScale(2.0f);

            String levelText = "Level: " + levelNum;
            layout.setText(scaledFont, levelText);

            float textX = (Gdx.graphics.getWidth() - layout.width) / 2;
            float textY = Gdx.graphics.getHeight() - layout.height - 50;

            scaledFont.draw(batch, levelText, textX, textY);
            batch.end();
            stage.act(delta);
            stage.draw();

            // Uncomment to enable debug rendering
            debugRenderer.render(world, batch.getProjectionMatrix().cpy().scale(PPM, PPM, 0));
        } else {

            batch.begin();

            batch.setColor(1, 1, 1, 1); // Full opacity for background
            batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

            batch.setColor(0, 0, 0, 0.6f);
            batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            batch.setColor(1, 1, 1, 1);
            batch.draw(slingShotTexture, slingshotAnchor.x - 30, levelCache.getFloorY());
            renderSurroundings();
            renderPigs();
            renderBirds();

            // This is dummy render
            batch.draw(slingShotTexture, 1000,-1000);

            System.out.println("win = "+won);
            System.out.println("Lost = "+loose);
            if(won){
                game.getMuliplexer().removeProcessor(stage);
                game.getMuliplexer().removeProcessor(gamePlayInput);
//                System.out.println("Won");
                if(stars == 0)stars = calculateStars();
                System.out.println(stars);
                winScreen.render(delta);
            }else if (loose){
//                System.out.println("Lost");
                game.getMuliplexer().removeProcessor(stage);
                game.getMuliplexer().removeProcessor(gamePlayInput);
                looseScreen.render(delta);
            }
            else pause.render(delta);
            batch.end();
        }
    }


    private void renderBirds() {
        if (!isBirdOnSlingShot && System.currentTimeMillis() - currentTimeMillis > 2000) { // 2 seconds delay
            if (birdPlayed >= levelCache.getBirds().size()) {
                if(PigAvailable == 0)won = true;
                else loose = true;
            } else {
                System.out.println("Placing bird on slingshot");

                currentBird = levelCache.getBirds().get(levelCache.getBirds().size() - 1 - birdPlayed);
                currentBird.getBody().setTransform((slingshotAnchor.x)/PPM ,
                    (levelCache.getFloorY() + (float) slingShotTexture.getHeight())/PPM , 0);
                currentBirdMass = currentBird.getBody().getMass()/PPM;
                currentBird.getBody().setType(BodyDef.BodyType.KinematicBody);
                currentBird.getBody().setLinearVelocity(0,0);
                currentBird.getBody().setAngularVelocity(0);
                isBirdOnSlingShot = true;
                birdPlayed++;
                currentTimeMillis = System.currentTimeMillis();
            }
        }

        for (Bird bird : levelCache.getBirds()) {
            Sprite sprite = bird.getSprite();

            float x = bird.getBody().getPosition().x * PPM - sprite.getWidth() / 2;
            float y = bird.getBody().getPosition().y * PPM - sprite.getHeight() / 2;

            sprite.setPosition(x, y);
            sprite.setRotation((float) Math.toDegrees(bird.getBody().getAngle()));
            sprite.setSize(bird.getWidth(),bird.getHeight());
            sprite.draw(batch);
        }
    }



    private void renderPigs() {
        Iterator<Pig> pigIterator = levelCache.getPigs().iterator();

        while (pigIterator.hasNext()) {
            Pig pig = pigIterator.next();

            if (pig.getHealth() > 0) {
                // Render alive pigs
                Sprite sprite = pig.getSprite();
                sprite.setPosition(
                    pig.getBody().getPosition().x * PPM - sprite.getWidth() / 2,
                    pig.getBody().getPosition().y * PPM - sprite.getHeight() / 2
                );

                if (isEditing && isDraggingBird && editablePig == pig) {
                    pig.getBody().setTransform(pig.getBody().getPosition(), angle);
                }

                sprite.setRotation((float) Math.toDegrees(pig.getBody().getAngle()));
                sprite.draw(batch);

            } else {
                // Handle dead pig with shrinking effect
                float shrinkRate = 50f; // Shrinking rate in pixels per second
                Sprite sprite = pig.getSprite();

                float newSize = sprite.getWidth() - shrinkRate * Gdx.graphics.getDeltaTime();
                if (newSize > 0) {
                    // Shrink the sprite smoothly
                    sprite.setSize(newSize, newSize);
                    sprite.setPosition(
                        pig.getBody().getPosition().x * PPM - newSize / 2,
                        pig.getBody().getPosition().y * PPM - newSize / 2
                    );
                    sprite.draw(batch);
                } else {
                    // Remove the pig from the world when fully shrunk
                    world.destroyBody(pig.getBody());
                    pigIterator.remove(); // Safely remove pig using iterator
                    pig.setBody(null);

//                    System.out.println("Pigs"+PigAvailable);
                    PigAvailable--;
                    System.out.println("Pigs killed= "+PigAvailable);
                    if(PigAvailable == 0)won = true;
                }
            }
        }
    }



    private void renderSurroundings() {
        for (Surroundings surroundings : levelCache.getComponents()) {
            Sprite sprite = surroundings.getSprite();
            sprite.setPosition(surroundings.getBody().getPosition().x * PPM - sprite.getWidth() / 2,
                surroundings.getBody().getPosition().y * PPM - sprite.getHeight() / 2);
            if(isEditing && isDraggingBird && editableSurrounding == surroundings){
                surroundings.getBody().setTransform(surroundings.getBody().getPosition(),angle);
            }
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
        pause.dispose();
        System.out.println("Disposing Gameplay");
        game.getMuliplexer().removeProcessor(stage);
        batch.dispose();
//        world.dispose();
        debugRenderer.dispose();
        stage.dispose();

    }
    private InputAdapter initializeInputProcessor() {
        return new InputAdapter() {
            public boolean keyDown(int keycode){
                if (keycode == Input.Keys.E){
                    isEditing = !isEditing;
                    System.out.println("Now you can edit!");
                    return true;
                }
                if(keycode == Input.Keys.C && isEditing){
                    saveLevel(levelNum);
                    System.out.println("Level Saved!");
                    return true;
                }
                return false;
            }
            public boolean keyUp(int keycode){
                if(keycode == Input.Keys.UP && isEditing && isDraggingBird){
                    angle += 0.1F;
                    return true;
                }
                if(keycode == Input.Keys.DOWN && isEditing && isDraggingBird){
                    angle -= 0.1F;
                    return true;
                }
                return false;
            }
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                // Flip the Y-coordinate to align with world coordinates
                float adjustedY = Gdx.graphics.getHeight() - screenY;
                Vector2 worldTouch = new Vector2(screenX, adjustedY).scl(1/PPM);

                // Debugging the touch location
                System.out.println("Mouse clicked at position (Gameplay): X = " + screenX + ", Y = " + adjustedY);
                System.out.println("Touch Down at World Coordinates: (" + worldTouch.x + "," + worldTouch.y + ")");

                if (isEditing) {
                    System.out.println("Touch Down in editing");
                    for (Surroundings surroundings : levelCache.getComponents()) {
                        if (isWithinBounds(worldTouch, surroundings)) {
                            isDraggingBird = true;
                            System.out.println("Clicked Surrounding: " + surroundings.getImgPath());
                            editableSurrounding = surroundings;
                            editableSurrounding.getBody().setType(BodyDef.BodyType.KinematicBody);
                            editableSurrounding.getBody().setLinearVelocity(0,0);
                            editableSurrounding.getBody().setAngularVelocity(0);
                            return true;
                        }
                    }
                    editableSurrounding = null;
                    for (Pig pig : levelCache.getPigs()) {
                        if (isWithinBoundsPigs(worldTouch, pig)) {
                            editablePig = pig;
                            editablePig.getBody().setType(BodyDef.BodyType.KinematicBody);
                            pig.getBody().setLinearVelocity(0,0);
                            pig.getBody().setAngularVelocity(0);
                            isDraggingBird = true;
                            return true;
                        }
                    }
                    editablePig = null;
                } else if (isBirdOnSlingShot) {
                    currentBird = levelCache.getBirds().get(levelCache.getBirds().size() - 1 - birdPlayed + 1);
                    Vector2 birdPosition = currentBird.getBody().getPosition();
                    if (birdPosition.dst(worldTouch) < 0.5f) {
                        isDraggingBird = true;
                        System.out.println("Now we can drag");
                    }
                    currentBird.getSprite().setRotation(0);
                    isDraggingBird = true;
                    isrenderTrajectory = true;
                }
                return true;
            }





            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                Vector2 worldTouch = new Vector2(screenX, Gdx.graphics.getHeight() - screenY).scl(1/PPM);
                if(isEditing){
                    System.out.println(editablePig);
                    System.out.println(editableSurrounding);
                    System.out.println("Dragging.....");
                    if(isDraggingBird) {
                        if (editablePig != null) {

                            editablePig.getBody().setTransform(worldTouch, angle);
                        } else if (editableSurrounding != null) {
                            editableSurrounding.getBody().setTransform(worldTouch, angle);
                        }
                    }
                }
                else if (isDraggingBird) {

                    currentBird.getBody().setTransform(worldTouch, 0);
                    ArrayList<Float> data = updateVelocity(new Vector2(screenX,screenY));
                    trajectory = calculateTrajectory(data.get(0),data.get(1),worldTouch.x*PPM,worldTouch.y*PPM);


                    currentBird.getSprite().setRotation(0);
                }
                return true;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                int adjustedY = Gdx.graphics.getHeight() - screenY;
                Vector2 worldTouch = new Vector2(screenX, adjustedY).scl(1/PPM);
                if(isEditing){
                    isDraggingBird = false;
                    if(editableSurrounding !=null){
//                        editableSurrounding.setX(screenX);
//                        editableSurrounding.setY(Gdx.graphics.getHeight() - screenY);
                        editableSurrounding.getBody().setType(BodyDef.BodyType.DynamicBody);
                        editableSurrounding.getBody().setTransform(worldTouch,angle);
                        editableSurrounding.setX(screenX);
                        editableSurrounding.setY(adjustedY);
                        editableSurrounding.setAngle(angle);
                        editableSurrounding = null;

                    }
                    if(editablePig !=null){
                        editablePig.getBody().setType(BodyDef.BodyType.DynamicBody);
                        editablePig.getBody().setTransform(worldTouch,angle);
                        editablePig.setAngle(angle);
                        editablePig = null;
                    }
                    angle = 0;
                }
                else if (isDraggingBird) {
                    isDraggingBird = false;
                    isrenderTrajectory = false;
                    currentBird.getBody().setType(BodyDef.BodyType.DynamicBody);
                    ArrayList<Float> data = updateVelocity(new Vector2(screenX,screenY));
                    currentBird.getBody().setLinearVelocity( data.get(2),data.get(3));
                    isBirdOnSlingShot = false;
                    currentBird.getBody().setLinearDamping(0);
                    currentBird.getSprite().setRotation(0);
                    currentTimeMillis = System.currentTimeMillis();
                    trajectory = null;
                }
                game.getMuliplexer().addProcessor(stage);
                createPauseButton();
                return true;
            }
        };

    }
    private ArrayList<Float> updateVelocity(Vector2 finalPos) {
        ArrayList<Float> data = new ArrayList<>();
        float deltaY = (float) ((levelCache.getFloorY() + 0.75 * slingShotTexture.getHeight()) - (Gdx.graphics.getHeight() - finalPos.y));
        float deltaX = slingshotAnchor.x - finalPos.x;

        System.out.println("deltaX = " + deltaX);
        System.out.println("deltaY = " + deltaY);

        // Slope Calculation
        float slope = (slingshotAnchor.x != finalPos.x) ? deltaY / deltaX : Float.POSITIVE_INFINITY;
        System.out.println("Slope = " + slope);

        // Angle Calculation
        double theta = Math.atan2(deltaY, deltaX);
        System.out.println("Angle (radians) = " + theta);
        data.add((float) theta);

        // Energy Calculations
        double birdMass = currentBirdMass;
        double netPotentialEnergy = birdMass * Math.abs(gravity) * deltaY;
        double elasticPotentialEnergy = 0.5 * SPRING_CONSTANT_K * (Math.pow(deltaX, 2) + Math.pow(deltaY, 2));

        System.out.println("Net Potential Energy = " + netPotentialEnergy);
        System.out.println("Elastic Potential Energy = " + elasticPotentialEnergy);

        double velocity = Math.sqrt((2 / birdMass) * (netPotentialEnergy + elasticPotentialEnergy));
        System.out.println("Total Velocity = " + velocity);

        // Velocity Components
        double vx = velocity * Math.cos(theta);
        double vy = velocity * Math.sin(theta);
        System.out.println("Velocity X = " + vx);
        System.out.println("Velocity Y = " + vy);

        data.add((float) velocity);
        data.add((float) vx);
        data.add((float) vy);

        return data;
    }



    private boolean isWithinBounds(Vector2 worldCoords, Surroundings surroundings) {
        surroundings.setX(surroundings.getBody().getPosition().x);
        surroundings.setY(surroundings.getBody().getPosition().y);
        System.out.println("Surrounding X = "+ surroundings.getX()+" Y = "+ surroundings.getY());
        System.out.println("Surrounding width = "+ surroundings.getWidth()+" height = "+ surroundings.getHeight());
        float maxY = surroundings.getY();
        float minY = maxY - (surroundings.getHeight()/PPM);
        float minX = surroundings.getX();
        float maxX = surroundings.getX() + (surroundings.getWidth()/PPM);
        if(worldCoords.y >= minY && worldCoords.y<=maxY && worldCoords.x >=minX && worldCoords.x <=maxX) return true;
        return false;
    }
    private boolean isWithinBoundsPigs(Vector2 worldCoords, Pig pigs) {
        pigs.setX(pigs.getBody().getPosition().x);
        pigs.setY(pigs.getBody().getPosition().y);
        System.out.println("Pig X = "+ pigs.getX()+" Y = "+ pigs.getY());
        System.out.println("pigs width = "+ pigs.getWidth()+" height = "+ pigs.getHeight());
        float maxY = pigs.getY();
        float minY = maxY - (pigs.getHeight()/PPM);
        float minX = pigs.getX();
        float maxX = pigs.getX() + (pigs.getWidth()/PPM);
        System.out.println("MaxY = "+maxY + "MinY "+minY);
        System.out.println(worldCoords.x +" "+worldCoords.y);
        System.out.println(minX +" "+maxX+" "+ minY +" "+maxY);
        if(worldCoords.y >= minY && worldCoords.y<=maxY && worldCoords.x >=minX && worldCoords.x <=maxX) return true;
        return false;
    }

    private List<Vector2> calculateTrajectory(float radians, float velocity, float x0, float y0) {
        System.out.println("Calculating Trajectory...");

        float time = 0f;
        float timeStep = 0.01f;
        List<Vector2> trajectory = new ArrayList<>();
//        radians = (float) Math.toRadians(radians);
        System.out.println("Traj theta = "+ radians);
        System.out.println("Traj velocity = "+ velocity);
        System.out.println("Traj velocityX = "+  velocity * Math.cos(radians) );
        System.out.println("Traj velocityY = "+ velocity * Math.sin(radians));
        while (true) {
            // Calculate the x and y positions
            double x = (x0 + velocity * Math.cos(radians) * time) ;
            double y = (y0+ velocity * Math.sin(radians) * time + 0.5f * gravity * time * time) ;

            // Skip invalid points
            if (Double.isNaN(x) || Double.isNaN(y)) {
                System.err.println("Invalid point detected (NaN). Skipping...");
                break;
            }

            // Stop if the projectile goes out of bounds
            if (x < 0 || x > Gdx.graphics.getWidth()|| y <= levelCache.getFloorY() || y > Gdx.graphics.getHeight()) {
                break;
            }

            // Add valid points to the trajectory
            trajectory.add(new Vector2((float) x, (float) y));

            // Increment time
            time += timeStep;
        }

        System.out.println("Trajectory calculation complete.");
        return trajectory;
    }




    public void renderTrajectory() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);

        float thickness = 0.5f; // Adjust thickness of the line

        for (int i = 0; i < trajectory.size() - 1; i++) {
            Vector2 point1 = trajectory.get(i);
            Vector2 point2 = trajectory.get(i + 1);

            // Get the direction vector from point1 to point2
            Vector2 direction = point2.cpy().sub(point1).nor();

            // Get perpendicular direction to make the rectangle
            Vector2 perpendicular = new Vector2(direction.x,-direction.y).scl(thickness / 2);

            // Define the four corners of the rectangle
            float x1 = point1.x + perpendicular.x;
            float y1 = point1.y + perpendicular.y;
            float x2 = point1.x - perpendicular.x;
            float y2 = point1.y - perpendicular.y;
            float x3 = point2.x - perpendicular.x;
            float y3 = point2.y - perpendicular.y;
            float x4 = point2.x + perpendicular.x;
            float y4 = point2.y + perpendicular.y;

            // Draw the rectangle (a thick line between points)
            shapeRenderer.rect(x2, y2, x3 - x2, y3 - y2); // You can choose to tweak this depending on preference.
        }

        shapeRenderer.end();
    }





}
