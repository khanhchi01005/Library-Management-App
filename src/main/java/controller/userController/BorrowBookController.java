package controller.userController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.SessionManager;
import services.date.DateDifference;
import services.transaction.TransactionService;

/**
 * Controller class for borrowing books in the user interface.
 */
public class BorrowBookController {

    private LibraryUserController parentController;

    @FXML
    private TextField borrowedDateField;

    @FXML
    private TextField returnedDateField;

    public static int book_id;

    /**
     * Sets the parent controller to allow communication between controllers.
     *
     * @param parentController the parent LibraryUserController instance.
     */
    public void setParentController(LibraryUserController parentController) {
        this.parentController = parentController;
    }

    /**
     * Initializes the controller after the FXML file has been loaded.
     */
    @FXML
    private void initialize() {
        System.out.println("BorrowBookController initialized.");
    }

    /**
     * Displays the Borrow Book UI with the specified book ID and username.
     *
     * @param id the ID of the book to be borrowed.
     * @param username the username of the user borrowing the book.
     * @param parentController the parent controller.
     * @throws Exception if the FXML file cannot be loaded.
     */
    public static void show(int id, String username, LibraryUserController parentController) throws Exception {
        FXMLLoader loader = new FXMLLoader(BorrowBookController.class.getResource("/userController/borrowBook.fxml"));
        Parent root = loader.load();

        book_id = id;

        BorrowBookController controller = loader.getController();
        controller.setParentController(parentController);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Borrow Book");
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Handles the Borrow button click event to process borrowing a book.
     */
    @FXML
    private void handleBorrowButton() {
        try {
            String borrowedDate = borrowedDateField.getText();
            String returnedDate = returnedDateField.getText();

            long dateDifference = DateDifference.calculateDateDifference(borrowedDate, returnedDate);
            if (dateDifference <= 0 || dateDifference >= 90) {
                showAlert("Error", "Please enter a valid date.");
                return;
            }

            String username = SessionManager.getUsername(); // Replace with the actual username

            TransactionService transactionService = new TransactionService();
            transactionService.borrowBook(username, book_id, borrowedDate, returnedDate);

            System.out.println("Book borrowed successfully.");

            // Close the window after borrowing the book
            Stage stage = (Stage) borrowedDateField.getScene().getWindow();
            stage.close();

            parentController.refreshBookList();
        } catch (Exception e) {
            System.out.println("Error borrowing book: " + e.getMessage());
        }
    }

    /**
     * Handles the Close button click event to close the current window.
     *
     * @param event the ActionEvent triggered by the button click.
     */
    @FXML
    private void handleClose(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            System.out.println("Error closing window: " + e.getMessage());
        }
    }

    /**
     * Displays an alert with the specified title and message.
     *
     * @param title the title of the alert dialog.
     * @param message the message to be displayed in the alert dialog.
     */
    protected void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
}
