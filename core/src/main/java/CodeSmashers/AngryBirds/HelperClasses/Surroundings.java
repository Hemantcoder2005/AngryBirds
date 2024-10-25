package CodeSmashers.AngryBirds.HelperClasses;

public class Surroundings {
    private String imgPath;
    private float x;
    private float y;
    private String type;
    private int durability;


    public Surroundings() {}

    public Surroundings(String imgPath, float x, float y, String type, int durability) {
        this.imgPath = imgPath;
        this.x = x;
        this.y = y;
        this.type = type;
        this.durability = durability;
    }


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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDurability() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

    // Method to check if the surroundings element is still intact
    public boolean isIntact() {
        return durability > 0;
    }

    // Method to reduce durability when it takes damage
    public void takeDamage(int damage) {
        durability = Math.max(durability - damage, 0);
    }
}
