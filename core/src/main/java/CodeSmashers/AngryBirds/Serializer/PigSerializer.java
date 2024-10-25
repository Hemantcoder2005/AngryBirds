package CodeSmashers.AngryBirds.Serializer;

import CodeSmashers.AngryBirds.HelperClasses.Pig;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class PigSerializer implements Json.Serializer<Pig> {

    @Override
    public void write(Json json, Pig pig, Class knownType) {
        json.writeObjectStart();
        json.writeValue("imgPath", pig.getImgPath());
        json.writeValue("x", pig.getX());
        json.writeValue("y", pig.getY());
        json.writeValue("health", pig.getHealth());
        json.writeObjectEnd();
    }

    @Override
    public Pig read(Json json, JsonValue jsonData, Class type) {
        String imgPath = jsonData.getString("imgPath");
        float x = jsonData.getFloat("x");
        float y = jsonData.getFloat("y");
        int health = jsonData.getInt("health");
        return new Pig(imgPath, x, y, health);
    }
}
