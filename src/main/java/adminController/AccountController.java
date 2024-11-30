package adminController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.user.Account;
import app.MainApp;
import services.user.AdminService;

public class AccountController {

    @FXML
    private TableView<Account> accountTable;

    @FXML
    private TableColumn<Account, Integer> idColumn;

    @FXML
    private TableColumn<Account, String> usernameColumn;

    @FXML
    private TableColumn<Account, String> passwordColumn;

    @FXML
    private TableColumn<Account, String> identificationIdColumn;

    @FXML
    private TableColumn<Account, String> fullnameColumn;

    @FXML
    private TableColumn<Account, String> phonenumberColumn;

    @FXML
    private TableColumn<Account, String> emailColumn;

    @FXML
    private TextField searchField;

    private AdminService adminService;

    private ObservableList<Account> accountList;

//    private ObservableList<Book> filteredList; // Danh sách được lọc

    @FXML
    public void handleSwitchPage1() {
        MainApp.switchScene("/adminController/homeAdmin.fxml");
    }

    @FXML
    public void handleSwitchPage2() {
        MainApp.switchScene("/adminController/borrowBookAdmin.fxml");
    }

    @FXML
    public void handleSwitchPage3() {
        MainApp.switchScene("/adminController/returnBookAdmin.fxml");
    }

    @FXML
    public void handleSwitchPage4() {
        MainApp.switchScene("/adminController/account.fxml");
    }

    @FXML
    public void handleSwitchPage5() {
        MainApp.switchScene("/adminController/profileAdmin.fxml");
    }

    @FXML
    public void handleLogout() {
        MainApp.switchScene("/adminController/main.fxml");
    }

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        identificationIdColumn.setCellValueFactory(new PropertyValueFactory<>("identificationId"));
        fullnameColumn.setCellValueFactory(new PropertyValueFactory<>("fullname"));
        phonenumberColumn.setCellValueFactory(new PropertyValueFactory<>("phonenumber"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        AdminService adminService = new AdminService();
        accountList = FXCollections.observableArrayList(adminService.viewAccount());
        accountTable.setItems(accountList);
    }

    public void refreshAccountList() {
        accountList.clear();
        accountList.addAll(adminService.viewAccount());
        accountTable.refresh();
    }

    // Thêm sách
    @FXML
    public void handleAdd() {
        try {
            // Mở Stage 2 khi nhấn Login
            AddUserController.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Xóa sách
    @FXML
    public void handleDelete() {
        AdminService adminService = new AdminService();
        if (adminService == null) {
            showAlert("Error", "Book service is not initialized!");
            return;
        }
        Account selectedAccount = accountTable.getSelectionModel().getSelectedItem();
        if (selectedAccount != null) {
            // Remove the book from the database
            adminService.deleteUser(selectedAccount.getId());

            // Remove the book from the TableView
            accountList.remove(selectedAccount);
            accountTable.refresh();
        } else {
            showAlert("Error", "Please select a book to delete!");
        }
    }


    // Tìm kiếm sách
    @FXML
    public void handleSearch() {
        String keyword = searchField.getText().toLowerCase();
        ObservableList<Account> filteredList = FXCollections.observableArrayList();

        for (Account account : accountList) {
            if (account.getUsername().toLowerCase().contains(keyword) ||
                    account.getPassword().toLowerCase().contains(keyword) ||
                    account.getIdentificationId().toLowerCase().contains(keyword) ||
                    account.getFullname().toLowerCase().contains(keyword) ||
                    account.getEmail().toLowerCase().contains(keyword) ||
                    account.getPhonenumber().toLowerCase().contains(keyword)) {
                filteredList.add(account);
            }
        }

        accountTable.setItems(filteredList);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
}
