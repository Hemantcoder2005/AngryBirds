package CodeSmashers.AngryBirds;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class AudioPlayer {

    private final Music backgroundMusic;
    private final Sound soundEffect;
    private final boolean isSoundEffect;
    private boolean muteBackgroundMusic;
    private boolean muteSoundEffect;

    public AudioPlayer(String path, GameAssetManager assets) {
        this.backgroundMusic = assets.getMusic("Audio/" + path);
        this.soundEffect = null;
        this.isSoundEffect = false;
        this.muteBackgroundMusic = false;
        this.muteSoundEffect = false;
    }

    public AudioPlayer(String path, GameAssetManager assets, boolean isSoundEffect) {
        this.isSoundEffect = isSoundEffect;
        if (isSoundEffect) {
            this.soundEffect = assets.getSound("Audio/" + path);
            this.backgroundMusic = null;
        } else {
            this.backgroundMusic = assets.getMusic("Audio/" + path);
            this.soundEffect = null;
        }
        this.muteBackgroundMusic = false;
        this.muteSoundEffect = false;
    }

    public void playBackgroundMusic() {
        if (!isSoundEffect && backgroundMusic != null) {
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

    public void toggleMuteBackgroundMusic(boolean globalMute) {
        this.muteBackgroundMusic = globalMute;
        if (backgroundMusic != null) {
            backgroundMusic.setVolume(getBackgroundMusicVolume());
        }
    }

    public void toggleMuteSoundEffect(boolean globalMute) {
        this.muteSoundEffect = globalMute;
    }

    public void dispose() {}

    private float getBackgroundMusicVolume() {
        return muteBackgroundMusic ? 0.0f : 1.0f;
    }

    private float getSoundEffectVolume() {
        return muteSoundEffect ? 0.0f : 1.0f;
    }
}
