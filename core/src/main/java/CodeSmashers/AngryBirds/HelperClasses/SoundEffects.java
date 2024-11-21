package CodeSmashers.AngryBirds.HelperClasses;

import CodeSmashers.AngryBirds.AudioPlayer;
import CodeSmashers.AngryBirds.GameAssetManager;

public class SoundEffects {
    private static final String BASEDIR = "GamePlay/";
    private static AudioPlayer flyingBird;
    private static AudioPlayer birdSelection;
    private static AudioPlayer levelCompleted;
    private static AudioPlayer slingshotStretched;
    private static AudioPlayer birdCollision;

//    private boolean slingshotStretchedIsplaying = true;
    // Private constructor to prevent instantiation
    private SoundEffects() {}

    public static void initialize(GameAssetManager gameAssetManager) {
        // Load all sounds
        flyingBird = new AudioPlayer(BASEDIR + "flyingbird.wav", gameAssetManager, true);
        birdSelection = new AudioPlayer(BASEDIR + "birdSelect.wav", gameAssetManager, true);
        levelCompleted = new AudioPlayer(BASEDIR + "levelCompleted.wav", gameAssetManager, true);
        slingshotStretched = new AudioPlayer(BASEDIR + "slingshotStretched.wav", gameAssetManager, true);
        birdCollision = new AudioPlayer(BASEDIR + "birdCollision.wav", gameAssetManager, true);
    }

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
}
