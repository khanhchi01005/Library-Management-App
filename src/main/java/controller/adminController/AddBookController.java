package controller.adminController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.book.BookService;
import services.image.ImageService;

import java.io.File;

/**
 * Controller for adding new books to the library system.
 */
public class AddBookController {

    private LibraryAdminController parentController;

    @FXML
    private TextField booknameField, authorField, publisherField, categoryField, yearField, pagesField, availableAmountField, descriptionField;

    @FXML
    private ImageView bookImage;

    /**
     * Sets the parent controller to enable refreshing the book list after adding a new book.
     *
     * @param parentController The parent controller.
     */
    public void setParentController(LibraryAdminController parentController) {
        this.parentController = parentController;
    }

    /**
     * Opens the add book window.
     *
     * @param parentController The parent controller.
     * @throws Exception if the FXML file cannot be loaded.
     */
    public static void show(LibraryAdminController parentController) throws Exception {
        FXMLLoader loader = new FXMLLoader(AddBookController.class.getResource("/adminController/addBook.fxml"));
        Parent root = loader.load();
        AddBookController controller = loader.getController();
        controller.setParentController(parentController);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Add Book");
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Handles adding a book manually with all details.
     *
     * @param event The action event.
     */
    @FXML
    private void handleAddButton(ActionEvent event) {
        BookService bookService = new BookService();

        if (validateInputs()) {
            try {
                String bookName = booknameField.getText();
                String author = authorField.getText();
                String publisher = publisherField.getText();
                String category = categoryField.getText();
                int year = Integer.parseInt(yearField.getText());
                int pages = Integer.parseInt(pagesField.getText());
                int availableAmount = Integer.parseInt(availableAmountField.getText());
                String description = descriptionField.getText();
                String image = "src/main/resources/Images/test.jpg";

                int nextID = bookService.addBookManually(bookName, author, category, year, pages, availableAmount, image, description, publisher);
                image = "src/main/resources/Images/" + nextID + "/bookImage.jpg";
                bookService.updateBookImage(nextID, image);
                ImageService.saveImage(nextID, bookImage);

                showAlert("Success", "Book added successfully.");
                closeWindow(event);
                parentController.refreshBookList();
            } catch (Exception e) {
                showAlert("Error", "Failed to add the book. Please try again.");
                e.printStackTrace();
            }
        }
    }

    /**
     * Opens a file chooser to select an image for the book.
     */
    @FXML
    private void chooseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg", "*.gif"));

        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            bookImage.setImage(image);
        }
    }

    /**
     * Closes the add book window.
     *
     * @param event The action event.
     */
    @FXML
    private void handleClose(ActionEvent event) {
        closeWindow(event);
    }

    /**
     * Validates input fields for manual book addition.
     *
     * @return true if inputs are valid, false otherwise.
     */
    private boolean validateInputs() {
        if (booknameField.getText().isEmpty() || authorField.getText().isEmpty() || publisherField.getText().isEmpty() || categoryField.getText().isEmpty()) {
            showAlert("Warning", "Please fill in all required fields.");
            return false;
        }
        try {
            Integer.parseInt(yearField.getText());
            Integer.parseInt(pagesField.getText());
            Integer.parseInt(availableAmountField.getText());
        } catch (NumberFormatException e) {
            showAlert("Warning", "Year, pages, and available amount must be numbers.");
            return false;
        }
        return true;
    }

    /**
     * Validates input fields for quick book addition.
     *
     * @return true if inputs are valid, false otherwise.
     */
    private boolean validateQuickAddInputs() {
        if (booknameField.getText().isEmpty() || availableAmountField.getText().isEmpty()) {
            showAlert("Warning", "Please fill in all required fields.");
            return false;
        }
        try {
            Integer.parseInt(availableAmountField.getText());
        } catch (NumberFormatException e) {
            showAlert("Warning", "Available amount must be a number.");
            return false;
        }
        return true;
    }

    /**
     * Displays an alert with a given title and message.
     *
     * @param title   The title of the alert.
     * @param message The message of the alert.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }

    /**
     * Closes the current window.
     *
     * @param event The action event.
     */
    private void closeWindow(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
