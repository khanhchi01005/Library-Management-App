package utils.Sound;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.nio.file.Paths;

public class SoundUtils {

    private static MediaPlayer mediaPlayer;

    // Phát âm thanh ngắn với AudioClip
    public static void playSoundEffect(String filePath) {
        AudioClip audioClip = new AudioClip(Paths.get(filePath).toUri().toString());
        audioClip.play();
    }

    // Phát nhạc nền hoặc âm thanh dài với MediaPlayer
    public static void playBackgroundMusic(String filePath, boolean loop) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        Media media = new Media(Paths.get(filePath).toUri().toString());
        mediaPlayer = new MediaPlayer(media);

        if (loop) {
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Lặp lại vô hạn
        }
        mediaPlayer.play();
    }

    // Dừng nhạc nền
    public static void stopBackgroundMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    // Điều chỉnh âm lượng
    public static void setVolume(double volume) {
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(volume); // Giá trị từ 0.0 (tắt) đến 1.0 (max)
        }
    }
}