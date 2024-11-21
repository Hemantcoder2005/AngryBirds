package CodeSmashers.AngryBirds.HelperClasses;

import CodeSmashers.AngryBirds.AudioPlayer;
import CodeSmashers.AngryBirds.GameAssetManager;
import com.badlogic.gdx.Audio;

public class SoundEffects {
    private static final String BASEDIR = "GamePlay/";

    // Sound effect players
    private static AudioPlayer flyingBird;
    private static AudioPlayer birdSelection;
    private static AudioPlayer levelCompleted;
    private static AudioPlayer slingshotStretched;
    private static AudioPlayer birdCollision;
    private static AudioPlayer pigCollide;
    private static AudioPlayer pigDestroy;
    private static AudioPlayer startCollected;
    private static AudioPlayer woodCollision;
    private static AudioPlayer woodDestroy;
    // Private constructor to prevent instantiation
    private SoundEffects() {}

    /**
     * Initialize the sound effects using the game asset manager.
     * @param gameAssetManager the asset manager to load sounds
     */
    public static void initialize(GameAssetManager gameAssetManager) {
        flyingBird = new AudioPlayer(BASEDIR + "flyingbird.wav", gameAssetManager, true);
        birdSelection = new AudioPlayer(BASEDIR + "birdSelect.wav", gameAssetManager, true);
        levelCompleted = new AudioPlayer(BASEDIR + "levelCompleted.wav", gameAssetManager, true);
        slingshotStretched = new AudioPlayer(BASEDIR + "slingshotStretched.wav", gameAssetManager, true);
        birdCollision = new AudioPlayer(BASEDIR + "birdCollision.wav", gameAssetManager, true);
        pigCollide = new AudioPlayer(BASEDIR + "pigCollide.wav", gameAssetManager, true);
        pigDestroy = new AudioPlayer(BASEDIR + "pigDestroy.wav", gameAssetManager, true);
        startCollected = new AudioPlayer(BASEDIR + "starCollected.wav", gameAssetManager, true);
        woodCollision = new AudioPlayer(BASEDIR + "woodCollision.wav", gameAssetManager, true);
        woodDestroy = new AudioPlayer(BASEDIR + "blockDestroy.wav", gameAssetManager, true);
    }

    // Methods to play each sound effect
    public static void playFlyingBird() {
        if (flyingBird != null) {
            flyingBird.playSoundEffect();
        }
    }

    public static void playBirdSelection() {
        if (birdSelection != null) {
            birdSelection.playSoundEffect();
        }
    }

    public static void playLevelCompleted() {
        if (levelCompleted != null) {
            levelCompleted.playSoundEffect();
        }
    }

    public static void playSlingshotStretched() {
        if (slingshotStretched != null) {
            slingshotStretched.stopSoundEffect();
            slingshotStretched.playSoundEffect();
        }
    }

    public static void playBirdCollision() {
        if (birdCollision != null) {
            birdCollision.playSoundEffect();
        }
    }

    public static void playPigCollide() {
        if (pigCollide != null) {
            pigCollide.playSoundEffect();
        }
    }

    public static void playPigDestroy() {
        if (pigDestroy != null) {
            pigDestroy.playSoundEffect();
        }
    }

    public static void playStartCollected() {
        if (startCollected != null) {
            startCollected.playSoundEffect();
        }
    }

    public static void playWoodCollision() {
        if (woodCollision != null) {
            woodCollision.playSoundEffect();
        }
    }
    public static void playWoodDestroy() {
        if (woodDestroy != null) {
            woodDestroy.playSoundEffect();
        }
    }

    // Utility methods for stopping all sounds
    public static void stopAllSounds() {
        if (flyingBird != null) flyingBird.stopSoundEffect();
        if (birdSelection != null) birdSelection.stopSoundEffect();
        if (levelCompleted != null) levelCompleted.stopSoundEffect();
        if (slingshotStretched != null) slingshotStretched.stopSoundEffect();
        if (birdCollision != null) birdCollision.stopSoundEffect();
        if (pigCollide != null) pigCollide.stopSoundEffect();
        if (pigDestroy != null) pigDestroy.stopSoundEffect();
        if (startCollected != null) startCollected.stopSoundEffect();
        if (woodCollision != null) woodCollision.stopSoundEffect();
        if (woodDestroy != null) woodDestroy.stopSoundEffect();
    }
}
