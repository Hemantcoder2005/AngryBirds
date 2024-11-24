package CodeSmashers.AngryBirds.Serializer;

import CodeSmashers.AngryBirds.HelperClasses.Pig;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

import static CodeSmashers.AngryBirds.Screens.GamePlay.PPM;

public class PigSerializer implements Json.Serializer<Pig> {

    @Override
    public void write(Json json, Pig pig, Class knownType) {
        json.writeObjectStart();
        json.writeValue("imgPath", pig.getImgPath());
        json.writeValue("x", pig.getBody().getPosition().x * PPM);
        json.writeValue("y", pig.getBody().getPosition().y * PPM);
        json.writeValue("width", pig.getWidth());
        json.writeValue("height", pig.getHeight());
        json.writeValue("health", pig.getHealth());
        json.writeValue("angle", pig.getAngle());
        json.writeValue("scaleFactor", pig.getScaleFactor());
        json.writeValue("density", pig.getDensity());
        json.writeValue("friction", pig.getFriction());
        json.writeValue("restitution", pig.getRestitution());
        json.writeValue("shape", pig.getShape());
        json.writeValue("damageImg",pig.getDamageImg());
        json.writeObjectEnd();
    }

    @Override
    public Pig read(Json json, JsonValue jsonData, Class type) {
        String imgPath = jsonData.getString("imgPath");
        String damageImg = jsonData.getString("damageImg");
        float x = jsonData.getFloat("x");
        float y = jsonData.getFloat("y");
        float width = jsonData.getFloat("width");
        float height = jsonData.getFloat("height");
        int health = jsonData.getInt("health");
        float angle = jsonData.getFloat("angle");
        float scaleFactor = jsonData.getFloat("scaleFactor");
        float density = jsonData.getFloat("density");
        float friction = jsonData.getFloat("friction");
        float restitution = jsonData.getFloat("restitution");
        String shape = jsonData.getString("shape");
        return new Pig(imgPath,damageImg, x, y, angle, width, height, health, scaleFactor, density, friction, restitution,shape);
    }
}
