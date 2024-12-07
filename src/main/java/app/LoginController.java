package app;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.user.Account;
import model.SessionManager;
import utils.Animation.EffectUtils;

/**
 * Controller for handling login functionality in the application.
 */
public class LoginController {

    @FXML
    private AnchorPane background;

    @FXML
    private AnchorPane loginPane;

    @FXML
    private Label introLabel;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button submitButton;

    private Account account = new Account();

    /**
     * Displays the login stage (Stage 2).
     *
     * @throws Exception if the stage fails to load.
     */
    public static void showStage2() throws Exception {
        FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("/adminController/login.fxml"));
        Parent root2 = loader.load();
        Stage stage2 = new Stage();
        stage2.setScene(new Scene(root2));
        stage2.setTitle("Library Login");
        stage2.setResizable(false);
        stage2.show();
    }

    /**
     * Initializes the login scene with animations and transitions.
     */
    @FXML
    public void initialize() {
        loginPane.setVisible(false);

        EffectUtils.applyTemporaryAppearEffect(introLabel, 0.75, 2);

        PauseTransition waitForIntroEffect = new PauseTransition(Duration.seconds(3.5));
        waitForIntroEffect.setOnFinished(event -> {
            introLabel.setVisible(false);
            loginPane.setVisible(true);
            loginPane.setOpacity(0);
            EffectUtils.applyFadeTransition(loginPane, 0, 1, 1, 1, false);
        });
        waitForIntroEffect.play();
    }

    /**
     * Handles the submit button click event.
     *
     * @param event the action event triggered by clicking the submit button.
     */
    @FXML
    public void handleSubmit(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        account.login(username, password);

        if (SessionManager.getisLogin()) {
            handleSceneTransition(username);
        } else {
            showLoginError();
        }
    }

    /**
     * Handles scene transitions based on user roles.
     *
     * @param username the username of the logged-in user.
     */
    private void handleSceneTransition(String username) {
        EffectUtils.applyFadeTransition(background, 1, 0, 0.75, 1, false);
        PauseTransition waitForIntroEffect = new PauseTransition(Duration.seconds(0.75));
        waitForIntroEffect.setOnFinished(event -> {
            SessionManager.setUsername(username);

            if (SessionManager.getIdentificationId().startsWith("AD")) {
                MainApp.switchScene("/adminController/mainAdmin.fxml");
            } else {
                MainApp.switchScene("/userController/mainUser.fxml");
            }
        });
        waitForIntroEffect.play();
    }

    /**
     * Displays an error alert for invalid login credentials.
     */
    private void showLoginError() {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Wrong username or password!");
        alert.show();
    }
}
