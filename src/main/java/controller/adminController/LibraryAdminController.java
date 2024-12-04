package controller.adminController;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.book.Book;
import services.book.BookService;

public class LibraryAdminController {

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

    @FXML
    private ImageView loadingImage;

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

        // Gọi hàm load dữ liệu sách với multithreading
        loadBookData();

        bookImage.setImage(new Image("file:" + "src/main/resources/Images/macdinh.jpg"));

        bookTable.setOnMouseClicked(event -> {
            Book selectedBook = bookTable.getSelectionModel().getSelectedItem();
            if (selectedBook != null) {
                String imagePath = selectedBook.getImage();
                if (imagePath != null && !imagePath.isEmpty()) {
                    bookImage.setImage(new Image("file:" + imagePath));
                }
            }
        });
    }

    private void loadBookData() {
        loadingImage.setImage(new Image("file:src/main/resources/Images/download.gif"));
        loadingImage.setVisible(true); // Hiển thị ảnh động khi bắt đầu tải dữ liệu

        Task<ObservableList<Book>> loadBooksTask = new Task<>() {
            @Override
            protected ObservableList<Book> call() throws Exception {
                BookService bookService = new BookService();
                return FXCollections.observableArrayList(bookService.getAllBooks());
            }
        };

        loadBooksTask.setOnSucceeded(event -> {
            bookList = loadBooksTask.getValue();
            bookTable.setItems(bookList);

            // Ẩn ảnh động khi tải xong
            loadingImage.setVisible(false);
            loadingImage.setImage(null); // Dọn dẹp ảnh để tiết kiệm bộ nhớ
        });

        loadBooksTask.setOnFailed(event -> {
            showAlert("Error", "Failed to load book data.");
            loadingImage.setVisible(false); // Ẩn ảnh động nếu thất bại
        });

        new Thread(loadBooksTask).start();
    }

    public void refreshBookList() {
        loadingImage.setImage(new Image("file:src/main/resources/Images/download.gif"));
        loadingImage.setVisible(true); // Hiển thị ảnh động khi làm mới danh sách

        Task<ObservableList<Book>> refreshBooksTask = new Task<>() {
            @Override
            protected ObservableList<Book> call() throws Exception {
                BookService bookService = new BookService();
                return FXCollections.observableArrayList(bookService.getAllBooks());
            }
        };

        refreshBooksTask.setOnSucceeded(event -> {
            Platform.runLater(() -> {
                bookList.clear();
                bookList.addAll(refreshBooksTask.getValue());
                bookTable.refresh();

                // Ẩn ảnh động khi làm mới xong
                loadingImage.setVisible(false);
                loadingImage.setImage(null); // Dọn dẹp ảnh
            });
        });

        refreshBooksTask.setOnFailed(event -> {
            showAlert("Error", "Failed to refresh book data.");
            loadingImage.setVisible(false); // Ẩn ảnh động nếu thất bại
        });

        new Thread(refreshBooksTask).start();
    }

    // Thêm sách
    @FXML
    public void handleAdd() {
        try {
            AddBookController.show(this);
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
                EditBookController.show(selectedBook, this); // Truyền sách được chọn vào EditBookController
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
