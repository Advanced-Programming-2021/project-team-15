package sample.controller.utilizationController;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import sample.controller.gamePlayController.GamePlayController;
import sample.model.Game;
import sample.view.DuelMenu;

public class AudioController {
    private static MediaPlayer mainMusic;
    private static final MediaPlayer alert;
    private static final MediaPlayer click;
    private static final MediaPlayer heartbeat;
    private static final MediaPlayer swamp;
    private static final MediaPlayer set;
    private static final MediaPlayer summoned;
    private static final MediaPlayer activated;
    private static final MediaPlayer posChanged;
    private static final MediaPlayer flipSummoned;
    private static final MediaPlayer effectDone;
    private static final MediaPlayer  flipSound;
    private static boolean mute;
    static {
        Media setMedia  = new Media(AudioController.class.getResource("/Audios/set.mp3").toExternalForm());
        Media summonedMedia  = new Media(AudioController.class.getResource("/Audios/summoned.mp3").toExternalForm());
        Media activatedMedia = new Media(AudioController.class.getResource("/Audios/activated.mp3").toExternalForm());
        Media menuMedia = new Media(AudioController.class.getResource("/Audios/Menu.mp3").toExternalForm());
        Media alertMedia = new Media(AudioController.class.getResource("/Audios/Alert.mp3").toExternalForm());
        Media clickMedia = new Media(AudioController.class.getResource("/Audios/Click.mp3").toExternalForm());
        Media heartbeatMedia = new Media(AudioController.class.getResource("/Audios/Heartbeat.mp3").toExternalForm());
        Media swampMedia = new Media(AudioController.class.getResource("/Audios/Swamp.mp3").toExternalForm());
        Media posChangedMedia = new  Media(AudioController.class.getResource("/Audios/posChanged.mp3").toExternalForm());
        Media flipSummonedMedia = new Media(AudioController.class.getResource("/Audios/flipSummoned.mp3").toExternalForm());
        Media effectDoneMedia = new Media(AudioController.class.getResource("/Audios/effectDone.mp3").toExternalForm());
        Media flipSoundMedia = new Media(AudioController.class.getResource("/Audios/effectDone.mp3").toExternalForm());
        flipSound = new MediaPlayer(flipSoundMedia);
        effectDone = new MediaPlayer(effectDoneMedia);
        set = new MediaPlayer(setMedia);
        summoned = new MediaPlayer(summonedMedia);
        activated = new MediaPlayer(activatedMedia);
        mainMusic = new MediaPlayer(menuMedia);
        alert = new MediaPlayer(alertMedia);
        click = new MediaPlayer(clickMedia);
        heartbeat = new MediaPlayer(heartbeatMedia);
        swamp = new MediaPlayer(swampMedia);
        posChanged = new MediaPlayer(posChangedMedia);
        flipSummoned = new MediaPlayer(flipSummonedMedia);
    }

    public static void playFlipSound(){
        if (mute) return;
        flipSound.play();
        flipSound.setOnEndOfMedia(flipSound::stop);
    }

    public static void playSwamp() {
        if (mute) return;
        swamp.play();
        swamp.setOnEndOfMedia(swamp::stop);
    }
    public static void playSet() {
        if (mute) return;
        set.play();
       set.setOnEndOfMedia(set::stop);
    }
    public static void playEffectDone() {
        if (mute) return;
        effectDone.play();
       effectDone.setOnEndOfMedia(effectDone::stop);
    }
    public static void playSummoned() {
        if (mute) return;
        summoned.play();
        summoned.setOnEndOfMedia(summoned::stop);
    }
    public static void playFlipSummoned() {
        if (mute) return;
        flipSummoned.play();
        flipSummoned.setOnEndOfMedia(flipSummoned::stop);
    }
    public static void playPosChanged()
    {  if(mute) return;
        posChanged.play();
        posChanged.setOnEndOfMedia(posChanged::stop);
    }

    public static void playActivated()
    { if(mute) return;
        activated.play();
        activated.setOnEndOfMedia(activated::stop);
    }


    public static void playAlert() {
        if (mute) return;
        alert.play();
        alert.setOnEndOfMedia(alert::stop);
    }

    public static void playClick() {
        if (mute) return;
        click.play();
        click.setOnEndOfMedia(click::stop);
    }

    public static void playHeartbeat() {
        if (mute) return;
        heartbeat.play();
        heartbeat.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                if (GamePlayController.getInstance().getCurrentPlayer().getLifePoint()<2000)
                    heartbeat.play();
                else heartbeat.stop();
            }
        });
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
