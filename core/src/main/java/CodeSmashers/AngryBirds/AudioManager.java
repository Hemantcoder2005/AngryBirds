package CodeSmashers.AngryBirds;

public class AudioManager {
    private static AudioPlayer currentMusic;

    public static AudioPlayer getCurrentMusic() {
        return currentMusic;
    }

    public static void setCurrentMusic(AudioPlayer currentMusic) {
        AudioManager.currentMusic = currentMusic;
    }

    public static void playBackgroundMusic(String file, GameAssetManager assets) {
        stopBackgroundMusic();
        currentMusic = new AudioPlayer(file, assets);
        currentMusic.playBackgroundMusic();
    }

    public static void stopBackgroundMusic() {
        if (currentMusic != null) {
            currentMusic.stopBackgroundMusic();
        }
    }

}
