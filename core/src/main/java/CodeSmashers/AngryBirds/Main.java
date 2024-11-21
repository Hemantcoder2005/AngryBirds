package CodeSmashers.AngryBirds;

import CodeSmashers.AngryBirds.HelperClasses.SoundEffects;
import CodeSmashers.AngryBirds.Screens.Intro;
import CodeSmashers.AngryBirds.inputHandler.GlobalInputHandler;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    private SpriteBatch batch;
    private Texture image;
    private static GlobalInputHandler globalInputHandler;
    private final InputMultiplexer multiplexer = new InputMultiplexer();
    private StateManager state;
    private GameAssetManager assets;
    private AudioManager audio;
    private boolean isSoundEffectIntialized = false;
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
//        printInputProcessors(this.getMuliplexer());
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
    public void printInputProcessors(InputMultiplexer multiplexer) {
        System.out.println("Input Processors in Multiplexer:");
        if (multiplexer == null) {
            System.out.println("Multiplexer is null.");
            return;
        }
        Array<InputProcessor> processors = multiplexer.getProcessors();
        if (processors.size == 0) {
            System.out.println("No input processors added to the multiplexer.");
        } else {
            for (InputProcessor processor : processors) {
                if (processor != null) {
                    System.out.println(processor.getClass().getName());
                } else {
                    System.out.println("Null input processor found.");
                }
            }
        }
        System.out.println("\n\n");
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
