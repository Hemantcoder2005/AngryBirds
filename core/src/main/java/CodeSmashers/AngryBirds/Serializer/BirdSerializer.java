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
        json.writeValue("angle", bird.getAngle());
        json.writeValue("scaleFactor", bird.getScaleFactor());
        json.writeValue("density", bird.getDensity());
        json.writeValue("friction", bird.getFriction());
        json.writeValue("restitution", bird.getRestitution());
        json.writeValue("shape", bird.getShape());
        json.writeObjectEnd();
    }

    @Override
    public Bird read(Json json, JsonValue jsonData, Class type) {
        String imgPath = jsonData.getString("imgPath");
        float x = jsonData.getFloat("x");
        float y = jsonData.getFloat("y");
        float angle = jsonData.getFloat("angle");
        float width = jsonData.getFloat("width");
        float height = jsonData.getFloat("height");
        float scaleFactor = jsonData.getFloat("scaleFactor");
        float density = jsonData.getFloat("density");
        float friction = jsonData.getFloat("friction");
        float restitution = jsonData.getFloat("restitution");
        String shape = jsonData.getString("shape");
        return new Bird(imgPath, x, y, angle, width, height, scaleFactor, density, friction, restitution, shape);
    }
}
