package controller.userController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.transaction.Transaction;
import services.transaction.TransactionService;

/**
 * Controller for managing the return book functionality for users.
 * This class displays user transactions and allows users to search and manage returns.
 */
public class ReturnBookUserController {

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

    /**
     * Initializes the controller, setting up the table columns and populating the transaction list.
     */
    @FXML
    public void initialize() {
        setupTableColumns();
        loadTransactionData();
        customizeStateColumn();
    }

    /**
     * Sets up the table columns with the appropriate data mappings.
     */
    private void setupTableColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        bookNameColumn.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        borrowDateColumn.setCellValueFactory(new PropertyValueFactory<>("borrowedDate"));
        returnDateColumn.setCellValueFactory(new PropertyValueFactory<>("returnedDate"));
        stateColumn.setCellValueFactory(new PropertyValueFactory<>("state"));
    }

    /**
     * Loads transaction data into the table from the transaction service.
     */
    private void loadTransactionData() {
        TransactionService transactionService = new TransactionService();
        transactionList = FXCollections.observableArrayList(transactionService.getReturnTransactions());
        transactionTable.setItems(transactionList);
    }

    /**
     * Customizes the state column to apply conditional formatting based on transaction state.
     */
    private void customizeStateColumn() {
        stateColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String state, boolean empty) {
                super.updateItem(state, empty);
                if (empty || state == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(state);
                    switch (state) {
                        case "Borrowing":
                            setStyle("-fx-background-color: #ffeb3b; -fx-text-fill: black;"); // Yellow
                            break;
                        case "Returned":
                            setStyle("-fx-background-color: #4caf50; -fx-text-fill: white;"); // Green
                            break;
                        case "Overdue":
                            setStyle("-fx-background-color: #f44336; -fx-text-fill: white;"); // Red
                            break;
                        default:
                            setStyle("");
                            break;
                    }
                }
            }
        });
    }

    /**
     * Refreshes the transaction list and reloads the table data.
     */
    public void refreshTransactionList() {
        transactionList.clear();
        loadTransactionData();
        transactionTable.refresh();
        showAlert(Alert.AlertType.INFORMATION, "Success", "Transaction list refreshed successfully.");
    }

    /**
     * Filters the transaction table based on the user's search input.
     */
    @FXML
    public void handleSearch() {
        String keyword = searchField.getText().toLowerCase();
        ObservableList<Transaction> filteredList = FXCollections.observableArrayList();

        for (Transaction transaction : transactionList) {
            if (transaction.getUserName().toLowerCase().contains(keyword) ||
                    transaction.getBookTitle().toLowerCase().contains(keyword) ||
                    transaction.getState().toLowerCase().contains(keyword)) {
                filteredList.add(transaction);
            }
        }

        if (filteredList.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "No Results", "No transactions match your search criteria.");
        } else {
            transactionTable.setItems(filteredList);
        }
    }

    /**
     * Displays an alert dialog to the user.
     *
     * @param alertType The type of alert (e.g., ERROR, INFORMATION, WARNING).
     * @param title     The title of the alert.
     * @param message   The message content of the alert.
     */
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
}