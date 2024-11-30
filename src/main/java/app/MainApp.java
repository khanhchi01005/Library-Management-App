package app;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainApp extends Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Tải file FXML cho Stage 1 và set controller
        MainApp.primaryStage = primaryStage;
        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/adminController/main.fxml"));
        Parent root = loader1.load();
        Scene scene1 = new Scene(root, 1224, 768);
        primaryStage.setScene(scene1);
        primaryStage.setTitle("PageTurners Library");
        primaryStage.setResizable(false);

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1.5), root);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();

        primaryStage.show();
    }

    public static void switchScene(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource(fxmlFile));
            Parent root = loader.load();
            Scene scene = new Scene(root, 1224, 768);
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

