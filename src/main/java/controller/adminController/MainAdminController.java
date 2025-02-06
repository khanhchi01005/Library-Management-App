package controller.adminController;

import app.MainApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.List;

/**
 * Controller for managing the main admin interface.
 * Handles navigation between different sections of the admin panel.
 */
public class MainAdminController {

    @FXML
    private AnchorPane pagePane;

    @FXML
    private Button menuButton, homeButton, bookButton, borrowButton, returnButton, accountButton, googleButton, logoutButton;

    @FXML
    private ImageView menuIcon, homeIcon, bookIcon, borrowIcon, returnIcon, accountIcon, logoutIcon;

    private List<Button> buttons;

    /**
     * Initializes the controller and sets up the default view.
     */
    @FXML
    public void initialize() {
        buttons = List.of(menuButton, homeButton, bookButton, borrowButton, returnButton, accountButton, googleButton, logoutButton);
        updateButtonOpacity(homeButton); // Highlight the "Home" button by default.
        loadPage("/adminController/home"); // Load the default "Home" page.
    }

    /**
     * Handles the menu button click event.
     */
    @FXML
    void handleMenuClick() {
        updateButtonOpacity(menuButton);
        System.out.println("Menu button clicked!");
        // Implement menu navigation logic here.
    }

    /**
     * Handles the home button click event and navigates to the home page.
     */
    @FXML
    void handleHomeClick() {
        updateButtonOpacity(homeButton);
        loadPage("/adminController/home");
    }

    /**
     * Handles the book button click event and navigates to the library management page.
     */
    @FXML
    void handleBookClick() {
        updateButtonOpacity(bookButton);
        loadPage("/adminController/libraryAdmin");
    }

    /**
     * Handles the borrow button click event and navigates to the borrow management page.
     */
    @FXML
    void handleBorrowClick() {
        updateButtonOpacity(borrowButton);
        loadPage("/adminController/borrowBookAdmin");
    }

    /**
     * Handles the return button click event and navigates to the return management page.
     */
    @FXML
    void handleReturnClick() {
        updateButtonOpacity(returnButton);
        loadPage("/adminController/returnBookAdmin");
    }

    /**
     * Handles the account button click event and navigates to the account management page.
     */
    @FXML
    void handleAccountClick() {
        updateButtonOpacity(accountButton);
        loadPage("/adminController/account");
    }

    /**
     * Handles the Google button click event and navigates to the Google search page.
     */
    @FXML
    void handleGoogleClick() {
        updateButtonOpacity(googleButton);
        loadPage("/adminController/googleSearch");
    }

    /**
     * Handles the logout button click event and switches to the login page.
     */
    @FXML
    void handleLogoutClick() {
        updateButtonOpacity(logoutButton);
        MainApp.switchScene("/adminController/login.fxml");
        showAlert("Success", "You have logged out successfully.");
    }

    /**
     * Updates the opacity of the navigation buttons to indicate the active button.
     *
     * @param activeButton The button currently selected by the user.
     */
    private void updateButtonOpacity(Button activeButton) {
        for (Button button : buttons) {
            if (button.getId().equals(activeButton.getId())) {
                button.setOpacity(1.0); // Fully visible for the active button.
            } else {
                button.setOpacity(0.6); // Dimmed for inactive buttons.
            }
        }
    }

    /**
     * Loads the specified page into the main content pane.
     *
     * @param page The path to the FXML file of the page to load.
     */
    public void loadPage(String page) {
        try {
            Parent root = FXMLLoader.load(MainAdminController.class.getResource(page + ".fxml"));
            pagePane.getChildren().setAll(root);
        } catch (IOException ex) {
            ex.printStackTrace();
            showAlert("Error", "Failed to load the page. Please try again.");
        }
    }

    /**
     * Displays an alert dialog to the user.
     *
     * @param title   The title of the alert dialog.
     * @param message The content of the alert dialog.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
}
