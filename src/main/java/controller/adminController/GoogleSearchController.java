package controller.adminController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.book.Book;
import services.book.BookService;
import services.image.ImageService;

/**
 * Controller for managing Google Book Search functionality.
 */
public class GoogleSearchController {

    @FXML
    private TableView<Book> bookTable;

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
    private TableColumn<Book, Integer> yearColumn;

    @FXML
    private TableColumn<Book, Integer> pageColumn;

    @FXML
    private TableColumn<Book, String> descriptionColumn;

    @FXML
    private TextField searchTitleField;

    @FXML
    private TextField searchNumberField;

    @FXML
    private TextField availableAmountField;

    @FXML
    private ImageView bookImage;

    @FXML
    private ImageView loadingImage;

    private ObservableList<Book> searchingBookList = FXCollections.observableArrayList();

    /**
     * Initializes the controller and sets up the table columns and event listeners.
     */
    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        imageColumn.setCellValueFactory(new PropertyValueFactory<>("image"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        publisherColumn.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        pageColumn.setCellValueFactory(new PropertyValueFactory<>("pages"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        loadingImage.setVisible(false);

        bookImage.setImage(new Image("file:" + "src/main/resources/Images/googleGif.gif"));

        bookTable.setOnMouseClicked(event -> {
            Book selectedBook = bookTable.getSelectionModel().getSelectedItem();
            if (selectedBook != null && selectedBook.getImage() != null) {
                bookImage.setImage(new Image(selectedBook.getImage()));
            }
        });
    }

    /**
     * Handles the search action for books using an external API.
     */
    @FXML
    public void handleSearch() {
        loadingImage.setImage(new Image("file:src/main/resources/Images/loading.gif"));
        loadingImage.setVisible(true);
        searchingBookList.clear();
        bookTable.refresh();

        String titleSearching = searchTitleField.getText().toLowerCase();
        String numberSearching = searchNumberField.getText().toLowerCase();

        Task<ObservableList<Book>> searchBooksTask = new Task<>() {
            @Override
            protected ObservableList<Book> call() throws Exception {
                return FXCollections.observableArrayList(Book.bookApi(BookService.apiKey, titleSearching, numberSearching));
            }
        };

        searchBooksTask.setOnSucceeded(event -> {
            searchingBookList = searchBooksTask.getValue();
            bookTable.setItems(searchingBookList);
            loadingImage.setVisible(false);
        });

        searchBooksTask.setOnFailed(event -> {
            showAlert("Error", "Failed to search books.");
            loadingImage.setVisible(false);
        });

        new Thread(searchBooksTask).start();
    }

    /**
     * Handles the action of adding a selected book to the system.
     *
     * @param event The event triggered by the add button.
     */
    @FXML
    private void handleAdd(ActionEvent event) {
        Book selectedBook = bookTable.getSelectionModel().getSelectedItem();
        if (selectedBook == null) {
            showAlert("Validation Error", "No book selected. Please select a book to add.");
            return;
        }

        try {
            int availableAmount = Integer.parseInt(availableAmountField.getText());
            BookService bookService = new BookService();

            int nextID = bookService.addBookManually(
                    selectedBook.getTitle(),
                    selectedBook.getAuthor(),
                    selectedBook.getCategory(),
                    selectedBook.getYear(),
                    selectedBook.getPages(),
                    availableAmount,
                    "src/main/resources/Images/test.jpg",
                    selectedBook.getDescription(),
                    selectedBook.getPublisher()
            );

            String updatedImagePath = "src/main/resources/Images/" + nextID + "/bookImage.jpg";
            bookService.updateBookImage(nextID, updatedImagePath);
            ImageService.saveImage(nextID, bookImage);

            showAlert("Success", "Book added successfully.");
        } catch (NumberFormatException e) {
            showAlert("Validation Error", "Invalid available amount. Please enter a valid number.");
        } catch (Exception e) {
            showAlert("Error", "Failed to add the book. Please try again.");
        }
    }

    /**
     * Opens a detailed view of the selected book.
     */
    @FXML
    public void handleDetail() {
        Book selectedBook = bookTable.getSelectionModel().getSelectedItem();
        if (selectedBook == null) {
            showAlert("Validation Error", "No book selected. Please select a book to view details.");
            return;
        }

        try {
            DetailBookController.showSearch(selectedBook);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to open book details.");
        }
    }

    /**
     * Displays an alert to the user.
     *
     * @param title   The title of the alert.
     * @param message The message to display.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
}
