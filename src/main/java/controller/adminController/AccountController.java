package controller.adminController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.user.Account;
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

    @FXML
    private Label userIDField, usernameField, passwordField, roleIDField, fullnameField, phoneNumberField, emailField;

    private AdminService adminService;

    private ObservableList<Account> accountList;

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

        accountTable.setOnMouseClicked(event -> {
            Account selectedAccount = accountTable.getSelectionModel().getSelectedItem();
            if (selectedAccount != null) {
                userIDField.setText(String.valueOf(selectedAccount.getId()));
                usernameField.setText(selectedAccount.getUsername());
                passwordField.setText(selectedAccount.getPassword());
                roleIDField.setText(selectedAccount.getIdentificationId());
                fullnameField.setText(selectedAccount.getFullname());
                phoneNumberField.setText(selectedAccount.getPhonenumber());
                emailField.setText(selectedAccount.getEmail());
            }
        });
    }

    public void refreshAccountList() {
        accountList.clear();
        accountTable.refresh();
        AdminService adminService = new AdminService();
        accountList = FXCollections.observableArrayList(adminService.viewAccount());
        accountTable.setItems(accountList);
    }

    // Thêm sách
    @FXML
    public void handleAdd() {
        try {
            AddUserController.show(this);
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
