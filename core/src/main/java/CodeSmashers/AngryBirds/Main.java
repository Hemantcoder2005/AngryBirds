package CodeSmashers.AngryBirds;

import CodeSmashers.AngryBirds.Screens.Intro;
import CodeSmashers.AngryBirds.inputHandler.GlobalInputHandler;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    private SpriteBatch batch;
    private Texture image;
    private static GlobalInputHandler globalInputHandler;
    private final InputMultiplexer multiplexer = new InputMultiplexer();
    private StateManager state;
    private GameAssetManager assets;
    private AudioManager audio;
    @Override
    public void create() {
        globalInputHandler = new GlobalInputHandler(this);
        multiplexer.addProcessor(globalInputHandler);
        batch = new SpriteBatch();
        state = new StateManager(this);
        audio = new AudioManager();
        state.create();
        Gdx.input.setInputProcessor(multiplexer);
        System.out.println("StateManager initialized: " + (state != null));
    }

    @Override
    public void render() {
        if(state!=null) state.render();
        else Gdx.app.exit();
        if(AudioManager.getCurrentMusic() != null) AudioManager.getCurrentMusic().toggleMuteBackgroundMusic(this.getGlobalInputHandler().isMuted());
    }

    @Override
    public void dispose() {
        if (batch != null) {
            batch.dispose();
            batch = null;
        }

        if (image != null) {
            image.dispose();
            image = null;
        }

        if (state != null) {
            state.dispose();
            state = null;
        }

        if (assets != null) {
            assets.dispose();
            assets = null;
        }

        multiplexer.clear();
    }


    public GlobalInputHandler getGlobalInputHandler() {
        return globalInputHandler;
    }


    public GameAssetManager getAssets() {
        return assets;
    }
    public AudioManager getAudioManager() {
        return audio;
    }

    public void setAssets(GameAssetManager assets) {
        this.assets = assets;
    }
    public InputMultiplexer getMuliplexer(){
        return multiplexer;
    }
    public StateManager getState(){
        return state;
    }
}
