package controller.userController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.SessionManager;
import model.book.Book;
import services.book.BookService;
import controller.adminController.DetailBookController;

public class LibraryUserController {

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

    @FXML
    private ImageView bookImage;

    private BookService bookService;
    private ObservableList<Book> bookList; // Danh sách gốc

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

        bookImage.setImage(new Image("file:" + "src/main/resources/Images/macdinh.jpg"));

        bookTable.setOnMouseClicked(event -> {
            Book selectedBook = bookTable.getSelectionModel().getSelectedItem();
            if (selectedBook != null) {
                String imagePath = selectedBook.getImage(); // Lấy đường dẫn hình ảnh từ đối tượng sách
                if (imagePath != null && !imagePath.isEmpty()) {
                    bookImage.setImage(new Image("file:" + imagePath));
                }
            }
        });
    }

    public void refreshBookList() {
        bookList.clear();
        bookTable.refresh();
        BookService bookService = new BookService();
        bookList = FXCollections.observableArrayList(bookService.getAllBooks());
        bookTable.setItems(bookList);
    }

    @FXML
    public void handleBorrow() {
        Book selectedBook = bookTable.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            try {
                BorrowBookController.show(selectedBook.getId(), SessionManager.getUsername(), this); // Truyền sách được chọn vào EditBookController
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            showAlert("Thông báo", "Vui lòng chọn một sách để chỉnh sửa.");
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
