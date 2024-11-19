package CodeSmashers.AngryBirds.Serializer;

import CodeSmashers.AngryBirds.HelperClasses.Surroundings;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

import static CodeSmashers.AngryBirds.Screens.GamePlay.PPM;

public class SurroundingsSerializer implements Json.Serializer<Surroundings> {

    @Override
    public void write(Json json, Surroundings surroundings, Class knownType) {
        json.writeObjectStart();
        json.writeValue("imgPath", surroundings.getImgPath());
        json.writeValue("x", surroundings.getBody().getPosition().x * PPM);
        json.writeValue("y", surroundings.getBody().getPosition().y * PPM);
        json.writeValue("type", surroundings.getType());
        json.writeValue("durability", surroundings.getDurability());
        json.writeValue("width", surroundings.getWidth());
        json.writeValue("height", surroundings.getHeight());
        json.writeValue("angle", surroundings.getAngle());
        json.writeValue("density", surroundings.getDensity());
        json.writeValue("friction", surroundings.getFriction());
        json.writeValue("restitution", surroundings.getRestitution());
        json.writeValue("scaleFactor", surroundings.getScaleFactor());
        json.writeValue("shape", surroundings.getShape());
        json.writeObjectEnd();
    }

    @Override
    public Surroundings read(Json json, JsonValue jsonData, Class type) {
        String imgPath = jsonData.getString("imgPath");
        float x = jsonData.getFloat("x");
        float y = jsonData.getFloat("y");
        String surroundingsType = jsonData.getString("type");
        int durability = jsonData.getInt("durability");
        float width = jsonData.getFloat("width");
        float height = jsonData.getFloat("height");
        float angle = jsonData.getFloat("angle");
        float density = jsonData.getFloat("density");
        float friction = jsonData.getFloat("friction");
        float restitution = jsonData.getFloat("restitution");
        float scaleFactor = jsonData.getFloat("scaleFactor");
        String shape = jsonData.getString("shape");
        return new Surroundings(imgPath, x, y, surroundingsType, durability, width, height, angle, density, friction, restitution,scaleFactor,shape);
    }
}
