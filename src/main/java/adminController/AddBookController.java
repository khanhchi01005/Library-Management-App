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
import services.book.BookService;

import java.io.File;

import static services.book.BookService.apiKey;

public class AddBookController {

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

    public static void show() throws Exception {
        FXMLLoader loader = new FXMLLoader(AddBookController.class.getResource("/adminController/addBook.fxml"));
        Parent root2 = loader.load();
        Stage stage2 = new Stage();
        stage2.setScene(new Scene(root2));
        stage2.setTitle("Add book");
        stage2.setResizable(false);
        stage2.show();
    }

    @FXML
    private void handleAddButton(ActionEvent event) {
        BookService bookService = new BookService();
        if (bookService == null) {
            return;
        }
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
            bookService.addBookManually(bookName, author, category, year, pages, availableAmount, image, description, publisher);
            System.out.println("Book added:");
            System.out.println("Name: " + bookName);
            System.out.println("Author: " + author);
            System.out.println("Publisher: " + publisher);
            System.out.println("Category: " + category);

            // Refresh the book list in HomeAdminController
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
    private void handleQuickAdd(ActionEvent event) {
        BookService bookService = new BookService();
        if (bookService == null) {
            return;
        }
        String bookName = booknameField.getText();
        int available_amount= Integer.parseInt(availableAmountField.getText());
        String image = (bookImage.getImage() != null) ? bookImage.getImage().getUrl() : "default/image.png";


        if (bookName.isEmpty() || available_amount == 0) {
            System.out.println("Please fill in all fields.");
        } else {
            bookService.addBook(apiKey, bookName, available_amount);
            System.out.println("Book added:");
            System.out.println("Name: " + bookName);
            System.out.println("Available amount: " + available_amount);

            // Refresh the book list in HomeAdminController
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

