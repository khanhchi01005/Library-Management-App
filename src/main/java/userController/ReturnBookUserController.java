package userController;

import app.MainApp;
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

public class ReturnBookUserController {

    @FXML
    private TableView<Transaction> transactionTable;

    @FXML
    private TableColumn<Transaction, Integer> idColumn;

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
        MainApp.switchScene("/userController/homeUser.fxml");
    }

    @FXML
    public void handleSwitchPage2() {
        MainApp.switchScene("/userController/borrowBookUser.fxml");
    }

    @FXML
    public void handleSwitchPage3() {
        MainApp.switchScene("/userController/returnBookUser.fxml");
    }

    @FXML
    public void handleSwitchPage4() {
        MainApp.switchScene("/userController/rules.fxml");
    }

    @FXML
    public void handleSwitchPage5() {
        MainApp.switchScene("/userController/profileUser.fxml");
    }

    @FXML
    public void handleLogout() {
        MainApp.switchScene("/adminController/main.fxml");
    }

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        bookNameColumn.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        borrowDateColumn.setCellValueFactory(new PropertyValueFactory<>("borrowedDate"));
        returnDateColumn.setCellValueFactory(new PropertyValueFactory<>("returnedDate"));
        stateColumn.setCellValueFactory(new PropertyValueFactory<>("state"));


        TransactionService transactionService = new TransactionService();
        transactionList = FXCollections.observableArrayList(transactionService.getReturnUserTransactions(SessionManager.getUsername()));
        transactionTable.setItems(transactionList);
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
