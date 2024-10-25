package CodeSmashers.AngryBirds;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class AudioPlayer {

    private final Music backgroundMusic;
    private final Sound soundEffect;
    private final boolean isSoundEffect;
    private boolean muteBackgroundMusic;
    private boolean muteSoundEffect;

    // Constructor for background music
    public AudioPlayer(String path, GameAssetManager assets) {
        this.backgroundMusic = assets.getMusic("Audio/" + path); // Use getMusic for background music
        this.soundEffect = null;
        this.isSoundEffect = false;
        this.muteBackgroundMusic = false;
        this.muteSoundEffect = false;
    }

    // Constructor for sound effects
    public AudioPlayer(String path, GameAssetManager assets, boolean isSoundEffect) {
        this.isSoundEffect = isSoundEffect;
        if (isSoundEffect) {
            this.soundEffect = assets.getSound("Audio/" + path); // Use getSound for sound effects
            this.backgroundMusic = null;
        } else {
            this.backgroundMusic = assets.getMusic("Audio/" + path); // Use getMusic for background music
            this.soundEffect = null;
        }
        this.muteBackgroundMusic = false;
        this.muteSoundEffect = false;
    }

    // Play background music
    public void playBackgroundMusic() {
        System.out.println(backgroundMusic);
        System.out.println("Sound Effect = " + isSoundEffect + " back = "+ backgroundMusic);
        if (!isSoundEffect && backgroundMusic != null) {
            System.out.println("Background Music Started");
            backgroundMusic.setLooping(true);
            backgroundMusic.setVolume(getBackgroundMusicVolume());
            backgroundMusic.play();
        }
    }
    public void stopBackgroundMusic() {
        if (!isSoundEffect && backgroundMusic != null) {
            backgroundMusic.stop();
        }
    }
    // Play sound effect
    public void playSoundEffect() {
        if (isSoundEffect && soundEffect != null) {
            soundEffect.play(getSoundEffectVolume());
        }
    }
    public void stopSoundEffect() {
        if (isSoundEffect && soundEffect != null) {
            soundEffect.stop();
        }
    }

    // Mute/unmute background music
    public void toggleMuteBackgroundMusic(boolean globalMute) {
        this.muteBackgroundMusic = globalMute;
        if (backgroundMusic != null) {
            backgroundMusic.setVolume(getBackgroundMusicVolume());
        }
    }

    // Mute/unmute sound effects
    public void toggleMuteSoundEffect(boolean globalMute) {
        this.muteSoundEffect = globalMute;
    }

    // Dispose resources (not necessary with AssetManager, this is handled globally)
    public void dispose() {
        // No need to call dispose() for assets loaded via AssetManager, as they are managed globally.
    }

    // Get volume level for background music
    private float getBackgroundMusicVolume() {
        return muteBackgroundMusic ? 1.0f : 0.0f;
    }

    // Get volume level for sound effect
    private float getSoundEffectVolume() {
        return muteSoundEffect ? 0.0f : 1.0f;
    }
}
