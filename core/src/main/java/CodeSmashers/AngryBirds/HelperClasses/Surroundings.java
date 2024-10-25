package CodeSmashers.AngryBirds.HelperClasses;

public class Surroundings {
    private String imgPath;
    private float x;
    private float y;
    private String type;
    private int durability;
    private float width;
    private float height;
    private float angle;

    public Surroundings() {}

    public Surroundings(String imgPath, float x, float y, String type, int durability, float width, float height, float angle) {
        this.imgPath = imgPath;
        this.x = x;
        this.y = y;
        this.type = type;
        this.durability = durability;
        this.width = width;
        this.height = height;
        this.angle = angle;
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

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public boolean isIntact() {
        return durability > 0;
    }

    public void takeDamage(int damage) {
        durability = Math.max(durability - damage, 0);
    }
}
