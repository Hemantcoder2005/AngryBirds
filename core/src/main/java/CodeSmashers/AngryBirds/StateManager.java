package CodeSmashers.AngryBirds;

import CodeSmashers.AngryBirds.Screens.Intro;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

public class StateManager extends Game {
    Main game;
    private Screen currentScreen;

    public StateManager(Main game) {
        this.game = game;
        this.currentScreen = new Intro(game);
    }

    @Override
    public void create() {
        setScreen(currentScreen);
    }

    public void switchScreen(Screen newScreen) {
        if (currentScreen != null) {
            System.out.println(currentScreen);
            currentScreen.dispose();
            System.out.println("Disposing....");
        }

        currentScreen = newScreen;
        setScreen(currentScreen);
    }

    @Override
    public void dispose() {
        if (currentScreen != null) {
            currentScreen.dispose();
        }
    }
}
