package CodeSmashers.AngryBirds.HelperClasses;

import CodeSmashers.AngryBirds.HelperClasses.Bird;
import CodeSmashers.AngryBirds.HelperClasses.Pig;
import CodeSmashers.AngryBirds.HelperClasses.Surroundings;
import CodeSmashers.AngryBirds.Main;
import com.badlogic.gdx.graphics.Texture;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class LevelCache {
    private String background;
    private ArrayList<Bird> birds;
    private ArrayList<Pig> pigs;
    private ArrayList<Surroundings> components;

    public LevelCache() {
        this.birds = new ArrayList<>();
        this.pigs = new ArrayList<>();
        this.components = new ArrayList<>();
    }
    public String getBackground(){
        return background;
    }
    public void setBackground(String background){
        this.background = background;
    }
    public ArrayList<Bird> getBirds() {
        return birds;
    }

    public ArrayList<Pig> getPigs() {
        return pigs;
    }

    public ArrayList<Surroundings> getComponents() {
        return components;
    }

    public void addBird(Bird bird) {
        birds.add(bird);
    }

    public void addPig(Pig pig) {
        pigs.add(pig);
    }

    public void addComponent(Surroundings component) {
        components.add(component);
    }
}
