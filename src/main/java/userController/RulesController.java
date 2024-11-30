package userController;

import app.MainApp;
import javafx.fxml.FXML;

public class RulesController {

    @FXML
    public void handleSwitchPage1() {
        MainApp.switchScene("/userController/homeUser.fxml");
    }

    @FXML
    public void handleSwitchPage2() {
        MainApp.switchScene("/userController/borrowBookUser.fxml");
    }

    @FXML
    public void handleSwitchPage3() {
        MainApp.switchScene("/userController/returnBookUser.fxml");
    }

    @FXML
    public void handleSwitchPage4() {
        MainApp.switchScene("/userController/rules.fxml");
    }

    @FXML
    public void handleSwitchPage5() {
        MainApp.switchScene("/userController/profileUser.fxml");
    }

    @FXML
    public void handleLogout() {
        MainApp.switchScene("/adminController/main.fxml");
    }
}
