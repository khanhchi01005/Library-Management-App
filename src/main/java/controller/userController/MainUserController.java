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

    public List<Button> buttons;

    @FXML
    public void initialize() {
        buttons = List.of(menuButton, homeButton, bookButton, borrowButton, returnButton, rulesButton, logoutButton);
        updateButtonOpacity(homeButton);
        loadPage("/adminController/home");
    }

    @FXML
    void handleMenuClick() {
        updateButtonOpacity(menuButton);
        System.out.println("Menu button clicked!");
    }

    @FXML
    void handleHomeClick() {
        updateButtonOpacity(homeButton);
        loadPage("/adminController/home");
    }

    @FXML
    void handleBookClick() {
        updateButtonOpacity(bookButton);
        loadPage("/userController/libraryUser");
    }

    @FXML
    void handleBorrowClick() {
        updateButtonOpacity(borrowButton);
        loadPage("/userController/borrowBookUser");
    }

    @FXML
    void handleReturnClick() {
        updateButtonOpacity(returnButton);
        loadPage("/userController/returnBookUser");
    }

    @FXML
    void handleAccountClick() {
        updateButtonOpacity(rulesButton);
        loadPage("/userController/rules");
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
            Parent root = FXMLLoader.load(MainUserController.class.getResource(page + ".fxml"));
            pagePane.getChildren().setAll(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
