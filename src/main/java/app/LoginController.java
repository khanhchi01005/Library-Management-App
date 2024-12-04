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

    // Hàm để hiển thị Stage 2
    public static void showStage2() throws Exception {
        FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("/adminController/login.fxml"));
        Parent root2 = loader.load();
        Stage stage2 = new Stage();
        stage2.setScene(new Scene(root2));
        stage2.setTitle("Library Login");
        stage2.setResizable(false);
        stage2.show();
    }

    @FXML
    public void initialize() {
        loginPane.setVisible(false);

        // Gọi hiệu ứng xuất hiện tạm thời cho introLabel
        EffectUtils.applyTemporaryAppearEffect(introLabel, 0.75, 2);

        // Tạo một PauseTransition để đợi cho đến khi introLabel hoàn tất
        PauseTransition waitForIntroEffect = new PauseTransition(Duration.seconds(3.5)); // Tổng thời gian hiệu ứng
        waitForIntroEffect.setOnFinished(event -> {
            introLabel.setVisible(false); // Ẩn introLabel
            loginPane.setVisible(true);
            loginPane.setOpacity(0);
            EffectUtils.applyFadeTransition(loginPane, 0, 1, 1, 1, false);
        });
        waitForIntroEffect.play();
    }

    // Sự kiện khi nhấn nút Submit
    @FXML
    public void handleSubmit(ActionEvent event) {
        // Lấy thông tin từ TextField
        String username = usernameField.getText();
        String password = passwordField.getText();
        account.login(username, password);
        if (SessionManager.getisLogin()) {

            // Chuyển cảnh về Stage 1 và hiển thị libraryAdmin.fxml
            try {
                if(SessionManager.getIdentificationId().substring(0, 2).equals("AD")) {
                    EffectUtils.applyFadeTransition(background, 1, 0, 0.75, 1, false);
                    PauseTransition waitForIntroEffect = new PauseTransition(Duration.seconds(0.75)); // Tổng thời gian hiệu ứng
                    waitForIntroEffect.setOnFinished(eventLoginAdmin -> {
                        SessionManager.setUsername(username);
                        MainApp.switchScene("/adminController/mainAdmin.fxml");
                    });
                    waitForIntroEffect.play();
                }
                else {
                    EffectUtils.applyFadeTransition(background, 1, 0, 0.75, 1, false);
                    PauseTransition waitForIntroEffect = new PauseTransition(Duration.seconds(0.75)); // Tổng thời gian hiệu ứng
                    waitForIntroEffect.setOnFinished(eventLoginUser -> {
                        SessionManager.setUsername(username);
                        MainApp.switchScene("/userController/mainUser.fxml");
                    });
                    waitForIntroEffect.play();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Tên đăng nhập hoặc mật khẩu không đúng!");
            alert.show();
        }
    }
}

