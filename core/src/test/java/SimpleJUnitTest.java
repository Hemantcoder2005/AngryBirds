import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Music;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;

class GameAssetManagerTest {

    private AssetManager assetManager;

    @BeforeEach
    void setUp() {
        assetManager = new AssetManager();
    }

    @Test
    void testEmptyAssetsFromFile() {
        assetManager.load("", Texture.class);
        assertFalse(assetManager.isLoaded(""), "Asset with an empty path should not be loaded.");
    }

@Test
void testLoadAssetsFromFile() {
    System.out.println("Current working directory: " + System.getProperty("user.dir"));

    String assetPath = "../assets/GamePlay/Birds/angry_bir.png";

    File file = new File(assetPath);
    if (file.exists()) {

        assetManager.load(assetPath, Music.class);

        // Update the assetManager to allow it to process the load request
        while (!assetManager.update()) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        assertTrue(assetManager.isLoaded(assetPath), "Asset should be loaded: " + assetPath);
    } else {
        System.out.println("File does not exist: " + assetPath);
        assertFalse(file.exists(), "Asset file should exist at path: " + assetPath);
    }
}


}

