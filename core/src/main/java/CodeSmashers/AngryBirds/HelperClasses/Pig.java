package CodeSmashers.AngryBirds.HelperClasses;

public class Pig {
    private String imgPath;
    private float x;
    private float y;
    private int health;

    // Default constructor
    public Pig() {}

    // Constructor with parameters
    public Pig(String imgPath, float x, float y, int health) {
        this.imgPath = imgPath;
        this.x = x;
        this.y = y;
        this.health = health;
    }

    // Getters and Setters
    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean isAlive() {
        return health > 0;
    }
}
