package controller.adminController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
                            setStyle("-fx-background-color: #ffeb3b; -fx-text-fill: black;"); // Màu vàng
                            break;
                        case "Returned":
                            setStyle("-fx-background-color: #4caf50; -fx-text-fill: white;"); // Màu xanh lá
                            break;
                        case "Overdue":
                            setStyle("-fx-background-color: #f44336; -fx-text-fill: white;"); // Màu đỏ
                            break;
                        default:
                            setStyle(""); // Mặc định
                            break;
                    }
                }
            }
        });
    }

    public void refreshTransactionList() {
        transactionList.clear();
        transactionTable.refresh();
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
            refreshTransactionList();
        } else {
            showAlert("Error", "Please select a transaction to accept.");
        }
    }

    @FXML
    public void handleOverdue() {
        Transaction selectedTransaction = transactionTable.getSelectionModel().getSelectedItem();
        if (selectedTransaction != null) {
            TransactionService transactionService = new TransactionService();
            transactionService.overdueNoti(selectedTransaction.getId());
            refreshTransactionList();
        } else {
            showAlert("Error", "Please select a transaction to accept.");
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