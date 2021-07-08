package sample.controller.utilizationController;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class AudioController {
    private static MediaPlayer mainMusic;
    private static boolean mute;
    static {
        Media menuMedia = new Media(AudioController.class.getResource("/Audios/Menu.mp3").toExternalForm());
        mainMusic = new MediaPlayer(menuMedia);
    }

    public static void playMenu() {
        if (mute) return;
        mainMusic.stop();
        Media menuMedia = new Media(AudioController.class.getResource("/Audios/Menu.mp3").toExternalForm());
        mainMusic = new MediaPlayer(menuMedia);
        mainMusic.play();
        mainMusic.setCycleCount(MediaPlayer.INDEFINITE);
    }

    public static void playGame() {
        if (mute) return;
        mainMusic.stop();
        Media gamePlayMedia = new Media(AudioController.class.getResource("/Audios/GamePlay.mp3").toExternalForm());
        mainMusic = new MediaPlayer(gamePlayMedia);
        mainMusic.play();
        mainMusic.setCycleCount(MediaPlayer.INDEFINITE);
    }
}
