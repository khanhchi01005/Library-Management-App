package adminController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.book.Book;
import services.book.BookService;
import app.MainApp;

import java.util.Objects;

public class HomeAdminController {

    @FXML
    private TableView<Book> bookTable;

    @FXML
    private TableColumn<Book, Integer> idColumn;

    @FXML
    private TableColumn<Book, String> nameColumn;

    @FXML
    private TableColumn<Book, String> imageColumn;

    @FXML
    private TableColumn<Book, String> authorColumn;

    @FXML
    private TableColumn<Book, String> publisherColumn;

    @FXML
    private TableColumn<Book, String> genreColumn;

    @FXML
    private TableColumn<Book, Integer> quantityColumn;

    @FXML
    private TableColumn<Book, Integer> pageColumn;

    @FXML
    private TextField searchField;

    private BookService bookService;
    private ObservableList<Book> bookList; // Danh sách gốc
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
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        imageColumn.setCellValueFactory(new PropertyValueFactory<>("image"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        publisherColumn.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("availableAmount"));
        pageColumn.setCellValueFactory(new PropertyValueFactory<>("pages"));

        BookService bookService = new BookService();
        bookList = FXCollections.observableArrayList(bookService.getAllBooks());
        bookTable.setItems(bookList);

    }

    public void refreshBookList() {
        bookList.clear();
        bookList.addAll(bookService.getAllBooks());
        bookTable.refresh();
    }

    // Thêm sách
    @FXML
    public void handleAdd() {
        try {
            // Mở Stage 2 khi nhấn Login
            AddBookController.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Sửa sách
    @FXML
    public void handleEdit() {
        Book selectedBook = bookTable.getSelectionModel().getSelectedItem();

        if (selectedBook != null) {
            try {
                EditBookController.show(selectedBook); // Truyền sách được chọn vào EditBookController
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            showAlert("Thông báo", "Vui lòng chọn một sách để chỉnh sửa.");
        }
    }

    // Xóa sách
    @FXML
    public void handleDelete() {
        BookService bookService = new BookService();
        if (bookService == null) {
            showAlert("Error", "Book service is not initialized!");
            return;
        }
        Book selectedBook = bookTable.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            // Remove the book from the database
            bookService.deleteBook(selectedBook.getId());

            // Remove the book from the TableView
            bookList.remove(selectedBook);
            bookTable.refresh();
        } else {
            showAlert("Error", "Please select a book to delete!");
        }
    }

    // Thông tin sách
    @FXML
    public void handleDetail() {
        Book selectedBook = bookTable.getSelectionModel().getSelectedItem();
        try {
            // Mở Stage 2 khi nhấn Login
            DetailBookController.show(selectedBook);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Tìm kiếm sách
    @FXML
    public void handleSearch() {
        String keyword = searchField.getText().toLowerCase();
        ObservableList<Book> filteredList = FXCollections.observableArrayList();

        for (Book book : bookList) {
            if (book.getTitle().toLowerCase().contains(keyword) ||
                    book.getAuthor().toLowerCase().contains(keyword) ||
                    book.getCategory().toLowerCase().contains(keyword) ||
                    book.getPublisher().toLowerCase().contains(keyword)) {
                filteredList.add(book);
            }
        }

        bookTable.setItems(filteredList);
    }

    protected void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
}
