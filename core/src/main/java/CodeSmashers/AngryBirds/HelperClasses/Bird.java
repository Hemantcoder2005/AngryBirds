package CodeSmashers.AngryBirds.HelperClasses;

import CodeSmashers.AngryBirds.Main;
import com.badlogic.gdx.graphics.Texture;

public class Bird {
    private String imgPath;
    private float x;
    private float y;
    private float Vx;
    private float Vy;
    private float width;
    private float height;
    private float angle;
    private Texture bird;

    public Bird() {
        this.imgPath = "";
        this.x = 0;
        this.y = 0;
        this.width = 50;
        this.height = 50;
        this.angle = 0;
    }

    public Bird(String imgPath, float x, float y, float width, float height) {
        this.imgPath = imgPath;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public String getImgPath() {
        return imgPath;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getVx() {
        return Vx;
    }

    public float getVy() {
        return Vy;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getAngle() {
        return angle;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setVx(float vx) {
        Vx = vx;
    }

    public void setVy(float vy) {
        Vy = vy;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }
}
