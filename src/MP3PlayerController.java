import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MP3PlayerController {

    private final MP3PlayerModel model;

    private final MP3PlayerView view;

    private void updateViewToMatchModel() {
        Media currentMedia = this.model.mediaPlayer().getMedia();

        StringBuilder text = new StringBuilder();

        try {
            String fileName = URLDecoder.decode(currentMedia.getSource(),
                    "utf-8");
            String[] fileNameSplit = fileName.split("/");
            int numOfSplits = fileNameSplit.length;
            String title = fileNameSplit[numOfSplits - 1];
            title = title.substring(0, title.indexOf(".mp3"));
            text.append(title);
            text.append(" - ");
            text.append(fileNameSplit[numOfSplits - 2]);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        this.view.updateNameDisplay(text.toString());
        this.view.updatePlayButton(this.model.isPlaying());
        this.view.updatePreviousButton(!this.model.previousFiles().isEmpty());

        this.model.mediaPlayer().setOnEndOfMedia(new playNextMP3(this));
    }

    public MP3PlayerController(MP3PlayerModel model, MP3PlayerView view) {
        this.model = model;
        this.view = view;
    }

    public void processPlayEvent() {
        boolean isPlaying = this.model.isPlaying();
        if (isPlaying) {
            this.model.mediaPlayer().pause();
        } else {
            this.model.mediaPlayer().play();
        }

        this.model.setIsPlaying(!isPlaying);

        this.updateViewToMatchModel();
    }

    public void processSkipEvent() {
        this.model.mediaPlayer().stop();

        this.model.previousFiles().push(this.model.mediaPlayer().getMedia());

        Media newMedia;

        if (this.model.nextFiles().size() > 0) {
            newMedia = this.model.nextFiles().pop();
        } else {
            File newFile = this.model.picker().pickRandom();
            newMedia = new Media(newFile.toURI().toASCIIString());
        }

        this.model.setMediaPlayer(new MediaPlayer(newMedia));
        this.model.mediaPlayer().play();
        this.model.setIsPlaying(true);

        this.updateViewToMatchModel();
    }

    public void processPreviousEvent() {
        this.model.mediaPlayer().stop();

        this.model.nextFiles().push(this.model.mediaPlayer().getMedia());

        Media lastMedia = this.model.previousFiles().pop();
        this.model.setMediaPlayer(new MediaPlayer(lastMedia));
        this.model.mediaPlayer().play();
        this.model.setIsPlaying(true);

        this.updateViewToMatchModel();
    }

    static public class playNextMP3 implements Runnable {
        MP3PlayerController controller;

        public playNextMP3(MP3PlayerController controller) {
            this.controller = controller;
        }

        @Override
        public void run() {
            this.controller.processSkipEvent();
        }
    }

}
