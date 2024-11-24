package CodeSmashers.AngryBirds.Serializer;

import CodeSmashers.AngryBirds.HelperClasses.Bird;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class BirdSerializer implements Json.Serializer<Bird> {

    @Override
    public void write(Json json, Bird bird, Class knownType) {
        json.writeObjectStart();

        // General attributes
        json.writeValue("imgPath", bird.getImgPath());
        json.writeValue("x", bird.getX());
        json.writeValue("y", bird.getY());
        json.writeValue("width", bird.getWidth());
        json.writeValue("height", bird.getHeight());
        json.writeValue("angle", bird.getAngle());

        // Correctly writing vx and vy
        json.writeValue("vx", bird.getVx());
        json.writeValue("vy", bird.getVy());

        // Physics properties
        json.writeValue("scaleFactor", bird.getScaleFactor());
        json.writeValue("density", bird.getDensity());
        json.writeValue("friction", bird.getFriction());
        json.writeValue("restitution", bird.getRestitution());

        // Shape details
        json.writeValue("shape", bird.getShape());
        json.writeValue("isOnSlingShot", bird.getIsOnSlingShot());
        json.writeValue("isBirdUsed", bird.getIsBirdUsed());


        json.writeObjectEnd();
    }

    @Override
    public Bird read(Json json, JsonValue jsonData, Class type) {
        String imgPath = jsonData.getString("imgPath");
        String damageImg = jsonData.getString("damageImg");
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
        boolean isOnSlingShot = jsonData.getBoolean("isOnSlingShot", false);
        boolean isBirdUsed = jsonData.getBoolean("isBirdUsed", false);
        float vx = jsonData.getFloat("vx", 0);
        float vy = jsonData.getFloat("vy", 0);

        return new Bird(imgPath, x, y, angle, width, height, scaleFactor, density, friction, restitution, shape, isOnSlingShot, isBirdUsed, vx, vy);
    }
}
