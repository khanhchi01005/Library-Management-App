/**
 * Main controller for the user interface of the application.
 * Handles navigation between different pages and updates the UI accordingly.
 */
package controller.userController;

import app.MainApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.List;

public class MainUserController {

    @FXML
    private AnchorPane pagePane;

    @FXML
    private Button menuButton, homeButton, bookButton, borrowButton, returnButton, rulesButton, logoutButton;

    @FXML
    private ImageView menuIcon, homeIcon, bookIcon, borrowIcon, returnIcon, accountIcon, logoutIcon;

    private List<Button> buttons;

    /**
     * Initializes the controller and sets up the default state.
     */
    @FXML
    public void initialize() {
        buttons = List.of(menuButton, homeButton, bookButton, borrowButton, returnButton, rulesButton, logoutButton);
        updateButtonOpacity(homeButton);
        loadPage("/adminController/home");
    }

    /**
     * Handles clicks on the "Menu" button.
     */
    @FXML
    void handleMenuClick() {
        updateButtonOpacity(menuButton);
        System.out.println("Menu button clicked!");
    }

    /**
     * Handles clicks on the "Home" button and navigates to the home page.
     */
    @FXML
    void handleHomeClick() {
        updateButtonOpacity(homeButton);
        loadPage("/adminController/home");
    }

    /**
     * Handles clicks on the "Books" button and navigates to the library page.
     */
    @FXML
    void handleBookClick() {
        updateButtonOpacity(bookButton);
        loadPage("/userController/libraryUser");
    }

    /**
     * Handles clicks on the "Borrow" button and navigates to the borrow books page.
     */
    @FXML
    void handleBorrowClick() {
        updateButtonOpacity(borrowButton);
        loadPage("/userController/borrowBookUser");
    }

    /**
     * Handles clicks on the "Return" button and navigates to the return books page.
     */
    @FXML
    void handleReturnClick() {
        updateButtonOpacity(returnButton);
        loadPage("/userController/returnBookUser");
    }

    /**
     * Handles clicks on the "Account" button and navigates to the rules page.
     */
    @FXML
    void handleAccountClick() {
        updateButtonOpacity(rulesButton);
        loadPage("/userController/rules");
    }

    /**
     * Handles clicks on the "Logout" button and switches to the login scene.
     */
    @FXML
    void handleLogoutClick() {
        updateButtonOpacity(logoutButton);
        MainApp.switchScene("/adminController/login.fxml");
    }

    /**
     * Updates the opacity of the buttons to indicate the active button.
     *
     * @param activeButton The button to set as active.
     */
    private void updateButtonOpacity(Button activeButton) {
        for (Button button : buttons) {
            if (button.getId().equals(activeButton.getId())) {
                button.setOpacity(1.0); // Fully visible for the active button
            } else {
                button.setOpacity(0.6); // Slightly transparent for inactive buttons
            }
        }
    }

    /**
     * Loads a new page into the main view.
     *
     * @param page The path to the FXML file to load.
     */
    public void loadPage(String page) {
        try {
            Parent root = FXMLLoader.load(MainUserController.class.getResource(page + ".fxml"));
            pagePane.getChildren().setAll(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
