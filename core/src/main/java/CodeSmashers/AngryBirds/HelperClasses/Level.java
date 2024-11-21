package CodeSmashers.AngryBirds.HelperClasses;

import java.util.ArrayList;

public class Level {
    public boolean locked;
    public int stars;

    public Level() {
    }

    // Constructor
    public Level(boolean locked, int stars) {
        this.locked = locked;
        this.stars = stars;
    }

    public int getStars() {
        return stars;
    }

    public boolean isCompleted() {
        return !locked;
    }


    // Getters and setters can be added here if needed

}
