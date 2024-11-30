package app;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import model.user.Account;
import model.SessionManager;

public class LoginController {

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

    // Sự kiện khi nhấn nút Submit
    @FXML
    public void handleSubmit(ActionEvent event) {
        // Lấy thông tin từ TextField
        String username = usernameField.getText();
        String password = passwordField.getText();
        account.login(username, password);
        if (SessionManager.getisLogin()) {
            // Đóng Stage 2
            Stage stage2 = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage2.close();

            // Chuyển cảnh về Stage 1 và hiển thị homeAdmin.fxml
            try {
                if(SessionManager.getIdentificationId().substring(0, 2).equals("AD"))
                    MainApp.switchScene("/adminController/homeAdmin.fxml");
                else {
                    MainApp.switchScene("/userController/homeUser.fxml");
                    SessionManager.setUsername(username);
                    System.out.println(SessionManager.getUsername());
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

