package CodeSmashers.AngryBirds.Serializer;

import CodeSmashers.AngryBirds.HelperClasses.Bird;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class BirdSerializer implements Json.Serializer<Bird> {

    @Override
    public void write(Json json, Bird bird, Class knownType) {
        json.writeObjectStart();
        json.writeValue("imgPath", bird.getImgPath());
        json.writeValue("x", bird.getX());
        json.writeValue("y", bird.getY());
        json.writeValue("width", bird.getWidth());
        json.writeValue("height", bird.getHeight());
        json.writeObjectEnd();
    }

    @Override
    public Bird read(Json json, JsonValue jsonData, Class type) {
        String imgPath = jsonData.getString("imgPath");
        float x = jsonData.getFloat("x");
        float y = jsonData.getFloat("y");
        float width = jsonData.getFloat("width");
        float height = jsonData.getFloat("height");
        return new Bird(imgPath, x, y, width, height);
    }
}
