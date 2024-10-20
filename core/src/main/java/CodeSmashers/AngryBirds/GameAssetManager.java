package CodeSmashers.AngryBirds;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GameAssetManager implements Disposable {
    private final AssetManager assetManager;

    public GameAssetManager() {
        this.assetManager = new AssetManager();
    }

    public void loadAssetsFromFile() {
        FileHandle fileHandle = Gdx.files.internal("assets.txt");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(fileHandle.read()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                queueAsset(line.trim());
            }
        } catch (IOException e) {
            Gdx.app.error("GameAssetManager", "Error reading assets.txt: " + e.getMessage());
        }
    }

    private void queueAsset(String assetPath) {
        if (assetPath.endsWith(".jpg")) {
            assetManager.load(assetPath, Texture.class);
        } else if (assetPath.endsWith(".wav")) {
            assetManager.load(assetPath, Sound.class);
        }
    }

    public boolean update() {
        return assetManager.update();
    }

    public float getProgress() {
        return assetManager.getProgress();
    }

    public Texture getTexture(String texturePath) {
        return assetManager.get(texturePath, Texture.class);
    }

    public Sound getSound(String soundPath) {
        return assetManager.get(soundPath, Sound.class);
    }

    public void unload(String assetPath) {
        if (assetManager.isLoaded(assetPath)) {
            assetManager.unload(assetPath);
        }
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }
}
