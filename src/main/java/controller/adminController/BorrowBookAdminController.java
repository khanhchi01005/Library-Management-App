package controller.adminController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.transaction.Transaction;
import services.transaction.TransactionService;

public class BorrowBookAdminController {

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
    private TextField searchField;

    private ObservableList<Transaction> transactionList; // Danh sách gốc

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        bookNameColumn.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        borrowDateColumn.setCellValueFactory(new PropertyValueFactory<>("borrowedDate"));
        returnDateColumn.setCellValueFactory(new PropertyValueFactory<>("returnedDate"));

        TransactionService transactionService = new TransactionService();
        transactionList = FXCollections.observableArrayList(transactionService.getAdminTransactions());
        transactionTable.setItems(transactionList);
//
    }

    public void refreshTransactionList() {
        transactionList.clear();
        transactionTable.refresh();
        TransactionService transactionService = new TransactionService();
        transactionList = FXCollections.observableArrayList(transactionService.getAdminTransactions());
        transactionTable.setItems(transactionList);
    }

    // chap nhan don muon sach
    @FXML
    public void handleAccept() {
        Transaction selectedTransaction = transactionTable.getSelectionModel().getSelectedItem();
        if (selectedTransaction != null) {
            TransactionService transactionService = new TransactionService();
            transactionService.acceptTransaction(selectedTransaction.getId());
            transactionList.remove(selectedTransaction);
        } else {
            showAlert("Error", "Please select a transaction to accept.");
        }
    }

    // Xóa sách
    @FXML
    public void handleReject() {
        Transaction selectedTransaction = transactionTable.getSelectionModel().getSelectedItem();
        if (selectedTransaction != null) {
            TransactionService transactionService = new TransactionService();
            transactionService.deleteTransaction(selectedTransaction.getId());
            transactionList.remove(selectedTransaction);
        } else {
            showAlert("Error", "Please select a transaction to reject.");
        }
    }

    // Tìm kiếm sách
    @FXML
    public void handleSearch() {
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
}