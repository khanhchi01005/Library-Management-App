package controller.adminController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.transaction.Transaction;
import services.transaction.TransactionService;

/**
 * Controller for managing book borrowing transactions.
 */
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

    private ObservableList<Transaction> transactionList; // Original list of transactions

    /**
     * Initializes the controller and populates the table with transactions.
     */
    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        bookNameColumn.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        borrowDateColumn.setCellValueFactory(new PropertyValueFactory<>("borrowedDate"));
        returnDateColumn.setCellValueFactory(new PropertyValueFactory<>("returnedDate"));

        loadTransactions();
    }

    /**
     * Loads transactions from the service and updates the table.
     */
    private void loadTransactions() {
        TransactionService transactionService = new TransactionService();
        transactionList = FXCollections.observableArrayList(transactionService.getAdminTransactions());
        transactionTable.setItems(transactionList);
    }

    /**
     * Refreshes the transaction list.
     */
    public void refreshTransactionList() {
        loadTransactions();
        transactionTable.refresh();
    }

    /**
     * Handles accepting a selected transaction.
     */
    @FXML
    public void handleAccept() {
        Transaction selectedTransaction = transactionTable.getSelectionModel().getSelectedItem();
        if (selectedTransaction != null) {
            TransactionService transactionService = new TransactionService();
            transactionService.acceptTransaction(selectedTransaction.getId());
            transactionList.remove(selectedTransaction);
            showAlert("Success", "Transaction accepted successfully.");
        } else {
            showAlert("Error", "Please select a transaction to accept.");
        }
    }

    /**
     * Handles rejecting a selected transaction.
     */
    @FXML
    public void handleReject() {
        Transaction selectedTransaction = transactionTable.getSelectionModel().getSelectedItem();
        if (selectedTransaction != null) {
            TransactionService transactionService = new TransactionService();
            transactionService.deleteTransaction(selectedTransaction.getId());
            transactionList.remove(selectedTransaction);
            showAlert("Success", "Transaction rejected successfully.");
        } else {
            showAlert("Error", "Please select a transaction to reject.");
        }
    }

    /**
     * Handles searching for transactions by keyword.
     */
    @FXML
    public void handleSearch() {
        String keyword = searchField.getText().toLowerCase();
        if (keyword.isEmpty()) {
            transactionTable.setItems(transactionList);
        } else {
            ObservableList<Transaction> filteredList = FXCollections.observableArrayList();
            for (Transaction transaction : transactionList) {
                if (transaction.getUserName().toLowerCase().contains(keyword) ||
                        transaction.getBookTitle().toLowerCase().contains(keyword)) {
                    filteredList.add(transaction);
                }
            }
            transactionTable.setItems(filteredList);
        }
    }

    /**
     * Displays an alert dialog with a given title and message.
     *
     * @param title   The title of the alert.
     * @param message The message of the alert.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
