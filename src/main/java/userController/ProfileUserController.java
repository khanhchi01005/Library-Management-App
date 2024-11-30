package userController;

import app.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

public class ProfileUserController {

    @FXML
    private ImageView userImageView;

    @FXML
    private Label IDLabel;
    @FXML
    private Label fullnameLabel;
    @FXML
    private Label roleLabel;
    @FXML
    private Label phonenumberLabel;
    @FXML
    private Label emailLabel;

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

    @FXML
    public void initialize() {
//        // Cấu hình các cột
//        sttColumn.setCellValueFactory(new PropertyValueFactory<>("stt"));
//        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
//        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
//
//        // Khởi tạo danh sách sách
//        bookList = FXCollections.observableArrayList(
//                new Book(1, "Sách A", "Tác giả A"),
//                new Book(2, "Sách B", "Tác giả B"),
//                new Book(3, "Sách C", "Tác giả C")
//        );
//
//        filteredList = FXCollections.observableArrayList(bookList); // Ban đầu hiển thị toàn bộ danh sách
//        bookTable.setItems(filteredList);
//
//        // Lắng nghe sự kiện khi chọn dòng
//        bookTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
//            displaySelectedBook(newValue);
//        });
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
}
