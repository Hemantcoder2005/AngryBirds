package CodeSmashers.AngryBirds.inputHandler;

import CodeSmashers.AngryBirds.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

public class GlobalInputHandler implements InputProcessor {
    protected boolean isMuted = true;
    private Main game;
    public GlobalInputHandler(Main game){
        this.game = game;
    }
    public boolean isMuted() {
        return isMuted;
    }

    @Override
    public boolean keyDown(int keycode) {

        if (keycode == Keys.M) {
            isMuted = !isMuted;
            System.out.println("Muted: " + isMuted);
            return true;
        }
        if(keycode == Keys.Q){
            System.out.println("aloo1");
            game.dispose();
            Gdx.app.exit();
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
