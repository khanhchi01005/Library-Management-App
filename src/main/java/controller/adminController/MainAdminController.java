package controller.adminController;

import app.MainApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.List;

public class MainAdminController {

    @FXML
    private AnchorPane pagePane;

    @FXML
    private Button menuButton, homeButton, bookButton, borrowButton, returnButton, accountButton, logoutButton;

    @FXML
    private ImageView menuIcon, homeIcon, bookIcon, borrowIcon, returnIcon, accountIcon, logoutIcon;

    public List<Button> buttons;

    @FXML
    public void initialize() {
        buttons = List.of(menuButton, homeButton, bookButton, borrowButton, returnButton, accountButton, logoutButton);
        updateButtonOpacity(homeButton);
        loadPage("/adminController/home");
    }

    @FXML
    void handleMenuClick() {
        updateButtonOpacity(menuButton);
        System.out.println("Menu button clicked!");
        // Logic cho menu navigation
    }

    @FXML
    void handleHomeClick() {
        updateButtonOpacity(homeButton);
        loadPage("/adminController/home");
    }

    @FXML
    void handleBookClick() {
        updateButtonOpacity(bookButton);
        loadPage("/adminController/libraryAdmin");
    }

    @FXML
    void handleBorrowClick() {
        updateButtonOpacity(borrowButton);
        loadPage("/adminController/borrowBookAdmin");
    }

    @FXML
    void handleReturnClick() {
        updateButtonOpacity(returnButton);
        loadPage("/adminController/returnBookAdmin");
    }

    @FXML
    void handleAccountClick() {
        updateButtonOpacity(accountButton);
        loadPage("/adminController/account");
    }

    @FXML
    void handleLogoutClick() {
        updateButtonOpacity(logoutButton);
        MainApp.switchScene("/adminController/login.fxml");
    }

    private void updateButtonOpacity(Button activeButton) {
        for (Button button : buttons) {
            if (button.getId().equals(activeButton.getId())) {
                button.setOpacity(1.0);
            } else {
                button.setOpacity(0);    // Giảm độ mờ nhưng không hoàn toàn ẩn
            }
        }
    }

    public void loadPage(String page) {
        try {
            Parent root = FXMLLoader.load(MainAdminController.class.getResource(page + ".fxml"));
            pagePane.getChildren().setAll(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
