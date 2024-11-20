package CodeSmashers.AngryBirds.inputHandler;

import CodeSmashers.AngryBirds.Main;
import CodeSmashers.AngryBirds.Screens.GamePlay;
import CodeSmashers.AngryBirds.Screens.LevelSelector;
import CodeSmashers.AngryBirds.Screens.MainMenuScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

public class GlobalInputHandler implements InputProcessor {
    protected boolean isMuted = false;
    protected boolean MuteSoundEffect = false;
    private Main game;
    public GlobalInputHandler(Main game){
        this.game = game;
    }
    public boolean isMuted() {
        return isMuted;
    }
    public boolean isMuteSoundEffect() {
        return MuteSoundEffect;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Keys.M) {
            isMuted = !isMuted;
            System.out.println("Muted: " + isMuted);
            return true;
        }
        if(keycode == Keys.Q){
            game.dispose();
            Gdx.app.exit();
        }
        if(keycode == Keys.S){
            MuteSoundEffect = !MuteSoundEffect;
            System.out.println("Sound Effect Muted : "+ !MuteSoundEffect);
            return true;
        }
        if(keycode == Keys.W){
            game.getState().switchScreen(new MainMenuScreen(game));
            return true;
        }
        if(keycode == Keys.L){
            game.getState().switchScreen(new LevelSelector(game));
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
        System.out.println("Mouse clicked at position: X = " + screenX + ", Y = " + (Gdx.graphics.getHeight() -  screenY));
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
