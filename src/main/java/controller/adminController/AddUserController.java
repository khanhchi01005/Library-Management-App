package controller.adminController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.user.Credentials;

public class AddUserController {

    private AccountController parentController;

    public void setParentController(AccountController parentController) {
        this.parentController = parentController;
    }

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
        System.out.println("AddUserController initialized.");
    }

    public static void show(AccountController parentController) throws Exception {
        FXMLLoader loader = new FXMLLoader(AddUserController.class.getResource("/adminController/addUser.fxml"));
        Parent root2 = loader.load();
        AddUserController controller = loader.getController();
        controller.setParentController(parentController);
        Stage stage2 = new Stage();
        stage2.setScene(new Scene(root2));
        stage2.setTitle("Add user");
        stage2.setResizable(false);
        stage2.show();
    }

    /**
     * Handles the add button action. Validates the input fields, attempts to add the user,
     * and shows an alert if the addition fails.
     */
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
                showAlert("Input Error", "Please fill in all fields.");
            } else {
                boolean success = credentials.register(username, password, identificationId, fullName, email, phoneNumber);
                if (success) {
                    System.out.println("User added successfully.");

                    // Close the add user window
                    Stage stage2 = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage2.close();

                    // Refresh the account list in the parent controller
                    parentController.refreshAccountList();
                } else {
                    showAlert("Registration Error", "Failed to add user. Please check the input and try again.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "An unexpected error occurred. Please try again.");
        }
    }

    /**
     * Handles the close button action, closing the add user window.
     */
    @FXML
    private void handleClose(ActionEvent event) {
        Stage stage2 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage2.close();
    }

    /**
     * Displays an alert dialog with the specified title and message.
     *
     * @param title   The title of the alert dialog.
     * @param message The message to display in the alert dialog.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
}
