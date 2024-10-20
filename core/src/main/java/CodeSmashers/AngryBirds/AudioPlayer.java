package CodeSmashers.AngryBirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class AudioPlayer {

    private final Music backgroundMusic;
    private final Sound soundEffect;
    private final boolean isSoundEffect;

    // Constructor for background music
    public AudioPlayer(String path, GameAssetManager assets) {
        this.backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("Audio/" + path));
        this.soundEffect = null;
        this.isSoundEffect = false;
    }

    // Constructor for sound effects
    public AudioPlayer(String path, GameAssetManager assets, boolean isSoundEffect) {
        this.isSoundEffect = isSoundEffect;
        if (isSoundEffect) {
            this.soundEffect = Gdx.audio.newSound(Gdx.files.internal("Audio/" + path));
            this.backgroundMusic = null;
        } else {
            this.backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("Audio/" + path));
            this.soundEffect = null;
        }
    }

    // Play background music
    public void playBackgroundMusic() {
        if (!isSoundEffect && backgroundMusic != null) {
            backgroundMusic.setLooping(true);
            backgroundMusic.play();
        }
    }

    // Play sound effect
    public void playSoundEffect() {
        if (isSoundEffect && soundEffect != null) {
            soundEffect.play();
        }
    }

    // Dispose resources
    public void dispose() {
        if (backgroundMusic != null) {
            backgroundMusic.dispose();
        }
        if (soundEffect != null) {
            soundEffect.dispose();
        }
    }
}
