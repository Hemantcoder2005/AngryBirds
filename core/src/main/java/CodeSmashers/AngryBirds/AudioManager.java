package CodeSmashers.AngryBirds;

public class AudioManager {
    private static AudioPlayer currentMusic;

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
