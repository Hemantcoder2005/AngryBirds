package CodeSmashers.AngryBirds;

import CodeSmashers.AngryBirds.HelperClasses.Level;
import CodeSmashers.AngryBirds.Serializer.LevelSerializer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class GameAssetManager implements Disposable {
    private final AssetManager assetManager;
    private final HashMap<String,HashMap<String,Object>> jsonFiles;
    public int levels = 0;
    public Map<String,Level> LevelChart;
    Json json = new Json();

    public GameAssetManager() {
        this.assetManager = new AssetManager();
        jsonFiles = new HashMap<>();
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
        if (assetPath.endsWith(".jpg") || assetPath.endsWith(".png")) {
            assetManager.load(assetPath, Texture.class);
        } else if (assetPath.endsWith(".wav") || assetPath.endsWith(".mp3")) {
            System.out.println(assetPath);
            if (assetPath.endsWith(".mp3")) {
                // Load as Music for longer audio files (e.g., background music)
                assetManager.load(assetPath, Music.class);
            } else {
                // Load as Sound for short sound effects (e.g., click sounds, explosions)
                assetManager.load(assetPath, Sound.class);
            }
        } else if (assetPath.endsWith(".json")) {
            System.out.println(assetPath);
            if(!assetPath.equals("user.json")) {
                System.out.println(assetPath);
                levels++;
            }
        }
    }

    public boolean update() {
        // Here We will call function to Write Operation to Json File
        if(assetManager.getProgress() > 0.8f && LevelChart == null){
            FileHandle fileHandle = Gdx.files.local( "user.json");
            if (fileHandle.exists()) {
                System.out.println("LevelChart is loading...");
                json.setSerializer(Level.class, new LevelSerializer());
                this.LevelChart = loadJson("user",Level.class);
            }else{
                System.out.println("Creating LevelChart.....");
                initializeUserLevels();
            }
        }
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
    public HashMap<String,Object> getJson(String jsonPath){
        return jsonFiles.get(jsonPath);
    }
    public Music getMusic(String musicPath) {
        return assetManager.get(musicPath, Music.class);
    }

    public void unload(String assetPath) {
        if (assetManager.isLoaded(assetPath)) {
            assetManager.unload(assetPath);
        }
    }

    public <T> Map<String, T> loadJson(String path,Class<T> clazz){
        FileHandle fileHandle = Gdx.files.local( path+".json");
        if (!fileHandle.exists()) {
            System.out.println(path + " not found!");
            return null;
        }
        String jsonString = fileHandle.readString();
        System.out.println("Loaded JSON: " + jsonString);
        return  json.fromJson(HashMap.class, clazz, jsonString);


    }
    private void initializeUserLevels() {
            System.out.println("Building user.json");
            LevelChart = new HashMap<>();
            for (int i = 1; i <= levels; i++) {
                Level level = new Level(false, 0); // All levels locked initially
                LevelChart.put("level" + i, level);
            }
            json.setSerializer(Level.class, new LevelSerializer());
            SaveJson("user",LevelChart);
    }
    private <T> void SaveJson(String path,T Data) {
        json.setTypeName(null);
        String jsonString = json.toJson(Data);
        FileHandle fileHandle = Gdx.files.local(path+".json");
        fileHandle.writeString(jsonString, false);
        System.out.println(path + "Saved ....");
    }
    @Override
    public void dispose() {
        assetManager.dispose();
    }
}

