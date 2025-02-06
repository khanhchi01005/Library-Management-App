package app;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import utils.Sound.SoundUtils;

/**
 * Main application class for launching the library management system.
 */
public class MainApp extends Application {

    private static Stage primaryStage;

    /**
     * Entry point for the JavaFX application. Initializes and displays the primary stage.
     *
     * @param primaryStage the main stage of the application.
     * @throws Exception if an error occurs during loading the FXML file.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        MainApp.primaryStage = primaryStage;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/adminController/login.fxml"));
        Parent root = loader.load();

        SoundUtils.playBackgroundMusic("src/main/resources/Sound/bgm.m4a", true);

        Scene scene = new Scene(root, 1366, 768);
        primaryStage.setScene(scene);
        primaryStage.setTitle("PageTurners Library");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Switches the current scene to a new scene specified by the FXML file.
     *
     * @param fxmlFile the path to the FXML file for the new scene.
     */
    public static void switchScene(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource(fxmlFile));
            Parent root = loader.load();
            Scene scene = new Scene(root, 1366, 768);

            primaryStage.setScene(scene);
            primaryStage.setResizable(false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Main method that launches the JavaFX application.
     *
     * @param args command-line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
}