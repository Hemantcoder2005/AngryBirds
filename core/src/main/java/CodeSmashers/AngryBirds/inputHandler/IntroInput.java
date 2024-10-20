package CodeSmashers.AngryBirds.inputHandler;

import CodeSmashers.AngryBirds.Main;
import CodeSmashers.AngryBirds.Screens.Intro;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;



public class IntroInput implements InputProcessor {
    private final Intro introScreen;
    public IntroInput(Intro introScreen){
        this.introScreen =introScreen;
    }
    @Override
    public boolean keyDown(int keycode) {
       if(keycode == Keys.ENTER){
           System.out.println("Enter is printed");
           introScreen.skip = true;
           return true;
       }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
