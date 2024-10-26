package CodeSmashers.AngryBirds.HelperClasses;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

public class Surroundings {
    private String imgPath;
    private float x;
    private float y;
    private String type;
    private int durability;
    private float width;
    private float height;
    private float angle;
    private float density;
    private Body body;
    private String shape;

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public float getScaleFactor() {
        return scaleFactor;
    }

    public void setScaleFactor(float scaleFactor) {
        this.scaleFactor = scaleFactor;
    }

    private float friction;
    private float restitution;
    private Sprite sprite;
    private float scaleFactor;
    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public Surroundings() {}

    public Surroundings(String imgPath, float x, float y, String type, int durability, float width, float height, float angle, float density, float friction, float restitution,float scaleFactor,String shape) {
        this.imgPath = imgPath;
        this.x = x;
        this.y = y;
        this.type = type;
        this.durability = durability;
        this.width = width;
        this.height = height;
        this.angle = angle;
        this.density = density;
        this.friction = friction;
        this.restitution = restitution;
        this.scaleFactor = scaleFactor;
        this.shape = shape;
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

    public float getDensity() {
        return density;
    }

    public void setDensity(float density) {
        this.density = density;
    }

    public float getFriction() {
        return friction;
    }

    public void setFriction(float friction) {
        this.friction = friction;
    }

    public float getRestitution() {
        return restitution;
    }

    public void setRestitution(float restitution) {
        this.restitution = restitution;
    }

    public boolean isIntact() {
        return durability > 0;
    }

    public void takeDamage(int damage) {
        durability = Math.max(durability - damage, 0);
    }
}
