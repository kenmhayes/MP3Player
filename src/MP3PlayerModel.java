import java.io.File;
import java.util.Stack;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MP3PlayerModel {

    private final FilePicker picker;
    private MediaPlayer mediaPlayer;
    private final Stack<Media> previousFiles;
    private final Stack<Media> nextFiles;
    private boolean isPlaying;

    public MP3PlayerModel() {
        new JFXPanel();
        this.picker = new FilePicker(new File("C:\\Users\\Ken\\Music"));
        File initialFile = this.picker.pickRandom();
        Media newMedia = new Media(initialFile.toURI().toASCIIString());
        this.mediaPlayer = new MediaPlayer(newMedia);

        this.previousFiles = new Stack<Media>();
        this.nextFiles = new Stack<Media>();

        this.isPlaying = false;
    }

    public FilePicker picker() {
        return this.picker;
    }

    public MediaPlayer mediaPlayer() {
        return this.mediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    public Stack<Media> previousFiles() {
        return this.previousFiles;
    }

    public Stack<Media> nextFiles() {
        return this.nextFiles;
    }

    public boolean isPlaying() {
        return this.isPlaying;
    }

    public void setIsPlaying(boolean b) {
        this.isPlaying = b;
    }
}
