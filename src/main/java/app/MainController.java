package app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainController {

    @FXML
    private Button loginButton;

    // Sự kiện khi nhấn nút Login
    @FXML
    public void handleLogin(ActionEvent event) {
        try {
            // Mở Stage 2 khi nhấn Login
            LoginController.showStage2();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

