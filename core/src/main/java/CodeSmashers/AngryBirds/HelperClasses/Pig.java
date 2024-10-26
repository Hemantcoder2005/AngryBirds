package CodeSmashers.AngryBirds.HelperClasses;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

public class Pig {
    private String imgPath;
    private float x;
    private float y;
    private float width;
    private float height;
    private float angle;
    private Texture pigTexture;
    private Sprite sprite;
    private Body body;
    private int health;
    private float density;
    private float friction;
    private float restitution;
    private float scaleFactor;
    private String shape;

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public Pig(String imgPath, float x, float y, float angle, float width, float height, int health, float scaleFactor, float density, float friction, float restitution,String shape) {
        this.imgPath = imgPath;
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.width = width;
        this.height = height;
        this.health = health;
        this.scaleFactor = scaleFactor;
        this.density = density;
        this.friction = friction;
        this.restitution = restitution;
        this.shape = shape;
    }

    public Pig() {
        this.imgPath = "";
        this.x = 0;
        this.y = 0;
        this.width = 50;
        this.height = 50;
        this.angle = 0;
        this.health = 100;
        this.scaleFactor = 1.0f;
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

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean isAlive() {
        return health > 0;
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

    public float getScaleFactor() {
        return scaleFactor;
    }

    public void setScaleFactor(float scaleFactor) {
        this.scaleFactor = scaleFactor;
    }

    public Texture getPigTexture() {
        return pigTexture;
    }

    public void setPigTexture(Texture pigTexture) {
        this.pigTexture = pigTexture;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }
}
