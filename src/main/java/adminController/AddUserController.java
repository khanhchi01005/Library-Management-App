package adminController;

import app.MainApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.user.Credentials;

public class AddUserController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField identificationIdField;

    @FXML
    private TextField fullNameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private void initialize() {
        // This method is called automatically after FXML is loaded.
        System.out.println("AddBookController initialized.");
    }

    public static void show() throws Exception {
        FXMLLoader loader = new FXMLLoader(AddUserController.class.getResource("/adminController/addUser.fxml"));
        Parent root2 = loader.load();
        Stage stage2 = new Stage();
        stage2.setScene(new Scene(root2));
        stage2.setTitle("Add user");
        stage2.setResizable(false);
        stage2.show();
    }

    @FXML
    private void handleAddButton(ActionEvent event) {
        try {
            Credentials credentials = new Credentials();
            String username = usernameField.getText();
            String password = passwordField.getText();
            String identificationId = identificationIdField.getText();
            String fullName = fullNameField.getText();
            String email = emailField.getText();
            String phoneNumber = phoneNumberField.getText();

            if (username.isEmpty() || password.isEmpty() || identificationId.isEmpty() || fullName.isEmpty() || email.isEmpty() || phoneNumber.isEmpty()) {
                System.out.println("Please fill in all fields.");
            } else {
                credentials.register(username, password, identificationId, fullName, email, phoneNumber);
                System.out.println("User added successfully.");

                Stage stage2 = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage2.close();

                MainApp.switchScene("/adminController/account.fxml");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleClose(ActionEvent event) {
        // Handle "Quick Add" button click event
        Stage stage2 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage2.close();
    }
}

