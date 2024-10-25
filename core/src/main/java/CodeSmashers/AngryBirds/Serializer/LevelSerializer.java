package CodeSmashers.AngryBirds.Serializer;

import CodeSmashers.AngryBirds.HelperClasses.Level;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class LevelSerializer implements Json.Serializer<Level> {
    @Override
    public void write(Json json, Level level, Class knownType) {
        json.writeObjectStart();
        json.writeValue("locked", level.isCompleted());
        json.writeValue("stars", level.getStars());
        json.writeObjectEnd();
    }

    @Override
    public Level read(Json json, JsonValue jsonData, Class type) {
        boolean locked = jsonData.getBoolean("locked");
        int stars = jsonData.getInt("stars");
        return new Level(locked, stars);
    }
}
