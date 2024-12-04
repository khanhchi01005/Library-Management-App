package utils.Animation;

import javafx.animation.*;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.util.Duration;

public class EffectUtils {

    // Hiệu ứng mờ dần
    public static void applyFadeTransition(Node node, double fromValue, double toValue, double durationInSeconds, int cycleCount, boolean autoReverse) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(durationInSeconds), node);
        fadeTransition.setFromValue(fromValue);
        fadeTransition.setToValue(toValue);
        fadeTransition.setCycleCount(cycleCount);
        fadeTransition.setAutoReverse(autoReverse);
        fadeTransition.play();
    }

    public static void applyFadeToBlackEffect(Node node, double durationInSeconds) {
        // Tạo một vùng có màu đen
        Region blackOverlay = new Region();
        blackOverlay.setStyle("-fx-background-color: black;");
        blackOverlay.setOpacity(0);

        // Thêm vùng đen vào cùng bố cục với node hiện tại
        node.getParent().getChildrenUnmodifiable().add(blackOverlay);

        // Đặt vị trí vùng đen phủ toàn bộ màn hình
        blackOverlay.setPrefWidth(node.getScene().getWidth());
        blackOverlay.setPrefHeight(node.getScene().getHeight());

        // Tạo hiệu ứng mờ dần cho vùng đen
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(durationInSeconds), blackOverlay);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);

        fadeTransition.setOnFinished(event -> {
            // Sau khi hiệu ứng hoàn tất, ta có thể xóa vùng đen nếu cần
            node.getParent().getChildrenUnmodifiable().remove(blackOverlay);
        });

        fadeTransition.play();
    }

    // Hiệu ứng xuất hiện, dừng lại và biến mất
    public static void applyTemporaryAppearEffect(Node node, double durationInSeconds, double pauseDurationInSeconds) {
        // Hiệu ứng mờ dần vào
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(durationInSeconds), node);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        // Tạm dừng
        PauseTransition pause = new PauseTransition(Duration.seconds(pauseDurationInSeconds));

        // Hiệu ứng mờ dần ra
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(durationInSeconds), node);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);

        // Kết hợp các hiệu ứng theo trình tự: xuất hiện -> tạm dừng -> biến mất
        SequentialTransition sequentialTransition = new SequentialTransition(fadeIn, pause, fadeOut);
        sequentialTransition.play();
    }

    // Hiệu ứng phóng to/thu nhỏ
    public static void applyScaleTransition(Node node, double fromX, double toX, double fromY, double toY, double durationInSeconds, int cycleCount, boolean autoReverse) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(durationInSeconds), node);
        scaleTransition.setFromX(fromX);
        scaleTransition.setToX(toX);
        scaleTransition.setFromY(fromY);
        scaleTransition.setToY(toY);
        scaleTransition.setCycleCount(cycleCount);
        scaleTransition.setAutoReverse(autoReverse);
        scaleTransition.play();
    }

    // Hiệu ứng di chuyển
    public static void applyTranslateTransition(Node node, double fromX, double toX, double fromY, double toY, double durationInSeconds, int cycleCount, boolean autoReverse) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(durationInSeconds), node);
        translateTransition.setFromX(fromX);
        translateTransition.setToX(toX);
        translateTransition.setFromY(fromY);
        translateTransition.setToY(toY);
        translateTransition.setCycleCount(cycleCount);
        translateTransition.setAutoReverse(autoReverse);
        translateTransition.play();
    }

    // Hiệu ứng kết hợp (mờ dần + di chuyển)
    // Hiệu ứng kết hợp mờ dần và trượt vào
    public static void applyCombinedEffectOnce(Node node, double fadeFrom, double fadeTo, double moveFromX, double moveToX, double durationInSeconds) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(durationInSeconds), node);
        fadeTransition.setFromValue(fadeFrom);
        fadeTransition.setToValue(fadeTo);

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(durationInSeconds), node);
        translateTransition.setFromX(moveFromX);
        translateTransition.setToX(moveToX);

        ParallelTransition parallelTransition = new ParallelTransition(fadeTransition, translateTransition);
        parallelTransition.setCycleCount(1); // Chạy một lần
        parallelTransition.play();
    }
}
