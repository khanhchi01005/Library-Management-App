package adminController;

import app.MainApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.book.Book;
import services.book.BookService;

import java.io.File;

import static services.book.BookService.*;
import static services.book.BookService.viewDescription;

public class EditBookController {

    @FXML
    private TextField booknameField;

    @FXML
    private TextField authorField;

    @FXML
    private TextField publisherField;

    @FXML
    private TextField categoryField;

    @FXML
    private ImageView bookImage;

    @FXML
    private TextField yearField;

    @FXML
    private TextField pagesField;

    @FXML
    private TextField availableAmountField;

    @FXML
    private TextField descriptionField;

    private Book book;

    @FXML
    private void initialize() {
        // This method is called automatically after FXML is loaded.
        System.out.println("AddBookController initialized.");
    }

    public static void show(Book book) throws Exception {
        FXMLLoader loader = new FXMLLoader(EditBookController.class.getResource("/adminController/editBook.fxml"));
        Parent root2 = loader.load();

        EditBookController controller = loader.getController();
        controller.setBookData(book); // Gửi dữ liệu sách vào controller

        Stage stage2 = new Stage();
        stage2.setScene(new Scene(root2));
        stage2.setTitle("Edit book");
        stage2.setResizable(false);
        stage2.show();
    }

    public void setBookData(Book book) {
        this.book = book;
        BookService bookService = new BookService();
        bookService.viewOneBook(book.getId());

        try {
            booknameField.setText(viewTitle);
            authorField.setText(viewAuthor);
            publisherField.setText(viewPublishers);
            categoryField.setText(viewCategory);
            yearField.setText(String.valueOf(viewYear));
            pagesField.setText(String.valueOf(viewPages));
            availableAmountField.setText(String.valueOf(viewAvailable_amount));
            descriptionField.setText(viewDescription);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleEditButton(ActionEvent event) {
        BookService bookService = new BookService();
        if (bookService == null) {
            return;
        }

        Book selectedBook = new Book(); // Create a new Book object to hold the updated values
        selectedBook.setId(book.getId()); // Set the ID of the selected book

        String bookName = booknameField.getText();
        String author = authorField.getText();
        String publisher = publisherField.getText();
        String category = categoryField.getText();
        int year = Integer.parseInt(yearField.getText());
        int pages = Integer.parseInt(pagesField.getText());
        int availableAmount = Integer.parseInt(availableAmountField.getText());
        String description = descriptionField.getText();
        String image = (bookImage.getImage() != null) ? bookImage.getImage().getUrl() : "default/image.png";

        if (bookName.isEmpty() || author.isEmpty() || publisher.isEmpty() || category.isEmpty()) {
            System.out.println("Please fill in all fields.");
        } else {
            bookService.modifyTitle(selectedBook.getId(), bookName);
            bookService.modifyAuthor(selectedBook.getId(), author);
            bookService.modifyPublisher(selectedBook.getId(), publisher);
            bookService.modifyCategory(selectedBook.getId(), category);
            bookService.modifyYear(selectedBook.getId(), year);
            bookService.modifyPages(selectedBook.getId(), pages);
            bookService.modifyAvailable_amount(selectedBook.getId(), availableAmount);
            bookService.modifyDescription(selectedBook.getId(), description);
            bookService.modifyImage(selectedBook.getId(), image);

            System.out.println("Book details updated successfully.");

            Stage stage2 = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage2.close();

            try {
                MainApp.switchScene("/adminController/homeAdmin.fxml");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void chooseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Chọn ảnh");

        // Lọc chỉ hiển thị các file ảnh
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Ảnh", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            bookImage.setImage(image);
        }
    }

    @FXML
    private void handleClose(ActionEvent event) {
        // Handle "Quick Add" button click event
        Stage stage2 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage2.close();
    }
}

