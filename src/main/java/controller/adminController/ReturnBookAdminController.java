package controller.adminController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.transaction.Transaction;
import services.transaction.TransactionService;

/**
 * Controller for managing return book transactions in the admin panel.
 * Provides functionality to view, search, confirm, and mark transactions as overdue.
 */
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

    @FXML
    private ImageView loadingImage;

    private ObservableList<Transaction> transactionList;

    /**
     * Initializes the controller and loads the return transaction data.
     * Sets up the table columns and styles the "state" column based on transaction status.
     */
    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        bookNameColumn.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        borrowDateColumn.setCellValueFactory(new PropertyValueFactory<>("borrowedDate"));
        returnDateColumn.setCellValueFactory(new PropertyValueFactory<>("returnedDate"));
        stateColumn.setCellValueFactory(new PropertyValueFactory<>("state"));

        loadTransactionData();

        // Style the "state" column based on transaction status.
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
                            setStyle(""); // Default
                            break;
                    }
                }
            }
        });
    }

    /**
     * Loads return transaction data from the service and populates the table.
     */
    private void loadTransactionData() {
        TransactionService transactionService = new TransactionService();
        transactionList = FXCollections.observableArrayList(transactionService.getReturnTransactions());
        transactionTable.setItems(transactionList);
    }

    /**
     * Refreshes the transaction list by reloading data from the service.
     */
    public void refreshTransactionList() {
        transactionList.clear();
        transactionTable.refresh();
        loadTransactionData();
    }

    /**
     * Confirms the selected transaction.
     * Updates the transaction status and refreshes the list.
     */
    @FXML
    public void handleConfirm() {
        Transaction selectedTransaction = transactionTable.getSelectionModel().getSelectedItem();
        if (selectedTransaction != null) {
            TransactionService transactionService = new TransactionService();
            transactionService.confirmTransaction(selectedTransaction.getId());
            refreshTransactionList();
            showAlert("Success", "Transaction confirmed successfully.");
        } else {
            showAlert("Error", "Please select a transaction to confirm.");
        }
    }

    /**
     * Marks the selected transaction as overdue and sends a notification.
     * Displays a loading animation while processing.
     */
    @FXML
    public void handleOverdue() {
        Transaction selectedTransaction = transactionTable.getSelectionModel().getSelectedItem();

        if (selectedTransaction != null) {
            // Show loading animation
            loadingImage.setImage(new Image("file:src/main/resources/Images/loading.gif"));
            loadingImage.setVisible(true);

            // Use a Task for background processing
            Task<Void> overdueTask = new Task<>() {
                @Override
                protected Void call() {
                    TransactionService transactionService = new TransactionService();
                    transactionService.overdueNoti(selectedTransaction.getId());
                    return null;
                }
            };

            overdueTask.setOnSucceeded(event -> {
                refreshTransactionList(); // Refresh list after processing
                loadingImage.setVisible(false); // Hide loading animation
                loadingImage.setImage(null);
                showAlert("Success", "Overdue notification sent successfully.");
            });

            overdueTask.setOnFailed(event -> {
                loadingImage.setVisible(false); // Hide loading animation
                showAlert("Error", "Failed to send overdue notification.");
            });

            new Thread(overdueTask).start(); // Start the background task
        } else {
            showAlert("Error", "Please select a transaction to mark as overdue.");
        }
    }

    /**
     * Handles the search functionality to filter transactions based on the keyword.
     */
    @FXML
    public void handleSearch() {
        String keyword = searchField.getText().toLowerCase();
        ObservableList<Transaction> filteredList = FXCollections.observableArrayList();

        for (Transaction transaction : transactionList) {
            if (transaction.getUserName().toLowerCase().contains(keyword) ||
                    transaction.getBookTitle().toLowerCase().contains(keyword)) {
                filteredList.add(transaction);
            }
        }

        if (filteredList.isEmpty()) {
            showAlert("Notification", "No transactions found matching the keyword.");
        }

        transactionTable.setItems(filteredList);
    }

    /**
     * Displays an alert dialog to the user.
     *
     * @param title   The title of the alert dialog.
     * @param message The content of the alert dialog.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
}
