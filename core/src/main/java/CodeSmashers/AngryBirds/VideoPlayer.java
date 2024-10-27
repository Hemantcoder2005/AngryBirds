package CodeSmashers.AngryBirds;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.video.VideoPlayerCreator;

public class VideoPlayer extends ApplicationAdapter {
    SpriteBatch batch;
    private double muted ;
    private com.badlogic.gdx.video.VideoPlayer videoPlayer;
    String path;
    private boolean isVideoCompleted;

    public VideoPlayer(String path) {

        this.path = path;
        this.isVideoCompleted = false;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        videoPlayer = VideoPlayerCreator.createVideoPlayer();

        videoPlayer.setOnCompletionListener(fileHandle -> {
            Gdx.app.log("gdx-video", "Video playback completed: " + fileHandle.path());
            isVideoCompleted = true;
        });

        // Load the video file
        FileHandle fileHandle = Gdx.files.internal(path);
        if (!fileHandle.exists()) {
            Gdx.app.error("gdx-video", "Video file not found: " + fileHandle.path());
            return;
        }

        try {
            videoPlayer.play(fileHandle);
            videoPlayer.setVolume(0);
        } catch (Exception e) {
            Gdx.app.error("gdx-video", "Failed to play video: " + e.getMessage());
        }
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0, 1);
        videoPlayer.update();
        videoPlayer.setVolume((float) this.muted);
        batch.begin();
        Texture frame = videoPlayer.getTexture();
        if (frame != null) {
            batch.draw(frame, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        videoPlayer.dispose();
    }

    public void mute(boolean muted) {
        if (muted) this.muted = 0.0;
        else this.muted = 1.0;
    }

    // New method to check if the video is completed
    public boolean isVideoCompleted() {
        return isVideoCompleted;
    }
}
