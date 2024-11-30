package adminController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import app.MainApp;
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

        TransactionService transactionService = new TransactionService();
        transactionList = FXCollections.observableArrayList(transactionService.getAllTransactions());
        transactionTable.setItems(transactionList);
//
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

    // Thông tin sách
    @FXML
    public void handleDetail() {
//        Book selectedBook = bookTable.getSelectionModel().getSelectedItem();
//        if (selectedBook != null) {
//            bookList.remove(selectedBook);
//            filteredList.remove(selectedBook);
//            nameField.clear();
//            authorField.clear(); // Xóa thông tin nếu dòng bị xóa
//        } else {
//            showAlert("Lỗi", "Vui lòng chọn sách để xóa!");
//        }
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