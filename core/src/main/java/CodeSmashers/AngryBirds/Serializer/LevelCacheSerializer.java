package CodeSmashers.AngryBirds.Serializer;

import CodeSmashers.AngryBirds.HelperClasses.LevelCache;
import CodeSmashers.AngryBirds.HelperClasses.Bird;
import CodeSmashers.AngryBirds.HelperClasses.Pig;
import CodeSmashers.AngryBirds.HelperClasses.Surroundings;
import CodeSmashers.AngryBirds.Main;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;

public class LevelCacheSerializer implements Json.Serializer<LevelCache> {

    @Override
    public void write(Json json, LevelCache levelCache, Class knownType) {
        // Set JSON output type to pretty-printed
        json.setOutputType(JsonWriter.OutputType.json);

        // Register custom serializers
        json.setSerializer(Bird.class, new BirdSerializer());
        json.setSerializer(Pig.class, new PigSerializer());
        json.setSerializer(Surroundings.class, new SurroundingsSerializer());

        // Start writing JSON
        json.writeObjectStart();

        // Serialize background and floorY
        json.writeValue("background", levelCache.getBackground());
        json.writeValue("floorY", levelCache.getFloorY());

        // Serialize birds
        json.writeArrayStart("birds");
        for (Bird bird : levelCache.getBirds()) {
            json.writeValue(bird); // Uses BirdSerializer
        }
        json.writeArrayEnd();

        // Serialize pigs
        json.writeArrayStart("pigs");
        for (Pig pig : levelCache.getPigs()) {
            json.writeValue(pig); // Uses PigSerializer
        }
        json.writeArrayEnd();

        // Serialize components
        json.writeArrayStart("components");
        for (Surroundings component : levelCache.getComponents()) {
            json.writeValue(component); // Uses SurroundingsSerializer
        }
        json.writeArrayEnd();

        json.writeObjectEnd();
    }


    @Override
    public LevelCache read(Json json, JsonValue jsonData, Class type) {
        LevelCache levelCache = new LevelCache();

        String background = jsonData.getString("background", "");
        levelCache.setBackground(background);

        float floorY = jsonData.getFloat("floorY");
        levelCache.setFloorY(floorY);
        // Deserialize birds
        JsonValue birdsArray = jsonData.get("birds");
        for (JsonValue birdData : birdsArray) {
            Bird bird = json.readValue(Bird.class, birdData);
            levelCache.addBird(bird);
        }

        // Deserialize pigs
        JsonValue pigsArray = jsonData.get("pigs");
        for (JsonValue pigData : pigsArray) {
            Pig pig = json.readValue(Pig.class, pigData);
            levelCache.addPig(pig);
        }

        // Deserialize components
        JsonValue componentsArray = jsonData.get("components");
        for (JsonValue componentData : componentsArray) {
            Surroundings component = json.readValue(Surroundings.class, componentData);
            levelCache.addComponent(component);
        }

        return levelCache;
    }
}
