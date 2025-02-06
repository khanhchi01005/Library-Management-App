package controller.userController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.SessionManager;
import model.transaction.Transaction;
import services.transaction.TransactionService;

/**
 * Controller class for managing borrowed book transactions for the user.
 */
public class BorrowBookUserController {

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

    private ObservableList<Transaction> transactionList;

    /**
     * Initializes the controller and sets up the transaction table with user data.
     */
    @FXML
    public void initialize() {
        try {
            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            usernameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
            bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));
            bookNameColumn.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
            borrowDateColumn.setCellValueFactory(new PropertyValueFactory<>("borrowedDate"));
            returnDateColumn.setCellValueFactory(new PropertyValueFactory<>("returnedDate"));

            TransactionService transactionService = new TransactionService();
            transactionList = FXCollections.observableArrayList(
                    transactionService.getUserTransactions(SessionManager.getUsername())
            );
            transactionTable.setItems(transactionList);

            System.out.println("BorrowBookUserController initialized successfully.");
        } catch (Exception e) {
            showAlert("Initialization Error", "Failed to load transaction data: " + e.getMessage());
        }
    }

    /**
     * Handles the search functionality to filter transactions based on user input.
     */
    @FXML
    public void handleSearch() {
        try {
            String query = searchField.getText().toLowerCase();
            ObservableList<Transaction> filteredList = FXCollections.observableArrayList();

            for (Transaction transaction : transactionList) {
                if (transaction.getBookTitle().toLowerCase().contains(query) ||
                        String.valueOf(transaction.getBookId()).contains(query)) {
                    filteredList.add(transaction);
                }
            }

            transactionTable.setItems(filteredList);
            System.out.println("Search completed: " + query);
        } catch (Exception e) {
            showAlert("Search Error", "An error occurred while performing the search: " + e.getMessage());
        }
    }

    /**
     * Displays an alert dialog with the specified title and message.
     *
     * @param title the title of the alert dialog.
     * @param message the message to be displayed in the alert dialog.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
}
