package sample.controller.utilizationController;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class AudioController {
    private static MediaPlayer mainMusic;
    private static MediaPlayer alert;
    private static MediaPlayer click;
    private static MediaPlayer heartbeat;
    private static boolean mute;
    static {
        Media menuMedia = new Media(AudioController.class.getResource("/Audios/Menu.mp3").toExternalForm());
        Media alertMedia = new Media(AudioController.class.getResource("/Audios/Alert.mp3").toExternalForm());
        Media clickMedia = new Media(AudioController.class.getResource("/Audios/Click.mp3").toExternalForm());
        Media heartbeatMedia = new Media(AudioController.class.getResource("/Audios/Heartbeat.mp3").toExternalForm());
        mainMusic = new MediaPlayer(menuMedia);
        alert = new MediaPlayer(alertMedia);
        click = new MediaPlayer(clickMedia);
        heartbeat = new MediaPlayer(heartbeatMedia);
    }

    public static void playAlert() {
        if (mute) return;
        alert.play();
        alert.setOnEndOfMedia(alert::stop);
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
