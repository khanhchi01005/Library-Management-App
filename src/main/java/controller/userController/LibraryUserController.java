/**
 * Controller for managing the library user interface, including book browsing,
 * searching, viewing details, and borrowing books.
 */
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
    private ObservableList<Book> bookList; // Original list of books

    /**
     * Initializes the controller and loads the book data into the table.
     */
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

        bookService = new BookService();
        bookList = FXCollections.observableArrayList(bookService.getAllBooks());
        bookTable.setItems(bookList);

        bookImage.setImage(new Image("file:src/main/resources/Images/macdinh.jpg"));

        bookTable.setOnMouseClicked(event -> {
            Book selectedBook = bookTable.getSelectionModel().getSelectedItem();
            if (selectedBook != null) {
                String imagePath = selectedBook.getImage(); // Get image path from the book object
                if (imagePath != null && !imagePath.isEmpty()) {
                    bookImage.setImage(new Image("file:" + imagePath));
                }
            }
        });
    }

    /**
     * Refreshes the book list in the table view.
     */
    public void refreshBookList() {
        bookList.clear();
        bookTable.refresh();
        bookList = FXCollections.observableArrayList(bookService.getAllBooks());
        bookTable.setItems(bookList);
    }

    /**
     * Handles the borrow button action. If the selected book has an available amount of 0,
     * an alert is displayed, and the borrowing process is prevented.
     */
    @FXML
    public void handleBorrow() {
        Book selectedBook = bookTable.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            if (selectedBook.getAvailableAmount() > 0) {
                try {
                    BorrowBookController.show(selectedBook.getId(), SessionManager.getUsername(), this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                showAlert("Notification", "This book is currently out of stock and cannot be borrowed.");
            }
        } else {
            showAlert("Notification", "Please select a book to borrow.");
        }
    }

    /**
     * Handles the detail button action, opening the detail view for the selected book.
     */
    @FXML
    public void handleDetail() {
        Book selectedBook = bookTable.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            try {
                DetailBookController.show(selectedBook);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            showAlert("Notification", "Please select a book to view details.");
        }
    }

    /**
     * Handles the search functionality, filtering the book list based on the search keyword.
     */
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

    /**
     * Displays an alert dialog with the specified title and message.
     *
     * @param title   The title of the alert dialog.
     * @param message The message to display in the alert dialog.
     */
    protected void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
}
