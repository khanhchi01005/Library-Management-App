package adminController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import app.MainApp;
import model.transaction.Transaction;
import services.transaction.TransactionService;

public class ReturnBookAdminController {

    @FXML
    private TableView<Transaction> transactionTable;

    @FXML
    private TableColumn<Transaction, Integer> idColumn;

    @FXML
    private TableColumn<Transaction, String> usernameColumn;

    @FXML
    private TableColumn<Transaction, Integer> bookIdColumn;

    @FXML
    private TableColumn<Transaction, String> bookNameColumn;

    @FXML
    private TableColumn<Transaction, String> borrowDateColumn;

    @FXML
    private TableColumn<Transaction, String> returnDateColumn;

    @FXML
    private TableColumn<Transaction, String> stateColumn;

    @FXML
    private TextField searchField;

    private ObservableList<Transaction> transactionList;


//    private ObservableList<Book> bookList; // Danh sách gốc
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
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        bookNameColumn.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        borrowDateColumn.setCellValueFactory(new PropertyValueFactory<>("borrowedDate"));
        returnDateColumn.setCellValueFactory(new PropertyValueFactory<>("returnedDate"));
        stateColumn.setCellValueFactory(new PropertyValueFactory<>("state"));


        TransactionService transactionService = new TransactionService();
        transactionList = FXCollections.observableArrayList(transactionService.getReturnTransactions());
        transactionTable.setItems(transactionList);

    }

    // Xóa sách
    @FXML
    public void handleConfirm() {
        Transaction selectedTransaction = transactionTable.getSelectionModel().getSelectedItem();
        if (selectedTransaction != null) {
            TransactionService transactionService = new TransactionService();
            transactionService.confirmTransaction(selectedTransaction.getId());

            MainApp.switchScene("/adminController/returnBookAdmin.fxml");

        } else {
            showAlert("Error", "Please select a transaction to accept.");
        }
    }

    // Tìm kiếm sách
    @FXML
    public void handleSearch() {
//        String keyword = searchField.getText().toLowerCase();
//        filteredList.clear();
//
//        for (Book book : bookList) {
//            if (book.getName().toLowerCase().contains(keyword) || book.getAuthor().toLowerCase().contains(keyword)) {
//                filteredList.add(book);
//            }
//        }
//
//        bookTable.setItems(filteredList);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
}