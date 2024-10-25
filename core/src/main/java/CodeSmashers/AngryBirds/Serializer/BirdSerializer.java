package CodeSmashers.AngryBirds.Serializer;

import CodeSmashers.AngryBirds.HelperClasses.Bird;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.sun.tools.jconsole.JConsoleContext;

public class BirdSerializer implements Json.Serializer<Bird> {

    @Override
    public void write(Json json, Bird bird, Class knownType) {
        json.writeObjectStart();
        json.writeValue("imgPath", bird.getImgPath());
        json.writeValue("x", bird.getX());
        json.writeValue("y", bird.getY());
        json.writeValue("Vx", bird.getVx());
        json.writeValue("Vy", bird.getVy());
        json.writeObjectEnd();
    }

    @Override
    public Bird read(Json json, JsonValue jsonData, Class type) {
        String imgPath = jsonData.getString("imgPath");
        System.out.println(imgPath);
        float x = jsonData.getFloat("x");
        float y = jsonData.getFloat("y");
        float Vx = jsonData.getFloat("Vx");
        float Vy = jsonData.getFloat("Vy");
        return new Bird(imgPath, x, y, Vx, Vy);
    }
}
