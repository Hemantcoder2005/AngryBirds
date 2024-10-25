package CodeSmashers.AngryBirds.HelperClasses;

import CodeSmashers.AngryBirds.Main;
import com.badlogic.gdx.graphics.Texture;

public class Bird {
    private String imgPath;
    private float x;
    private float y;
    private float Vx;
    private float Vy;
    private Texture bird;

    public Bird() {
        // You can initialize default values if necessary
        this.imgPath = ""; // or some default image path
        this.x = 0;
        this.y = 0;
    }
    public Bird(String imgPath, float x, float y, float Vx, float Vy) {
        this.imgPath = imgPath;
        this.x = x;
        this.y = y;
        this.Vx = Vx;
        this.Vy = Vy;
    }

    // Getters
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

    // Setters
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

}
