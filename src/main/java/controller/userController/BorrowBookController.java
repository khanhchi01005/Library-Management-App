package controller.userController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.SessionManager;
import services.transaction.TransactionService;

public class BorrowBookController {

    private LibraryUserController parentController;

    public void setParentController(LibraryUserController parentController) {
        this.parentController = parentController;
    }

    @FXML
    private TextField borrowedDateField;

    @FXML
    private TextField returnedDateField;

    public static int book_id;

    @FXML
    private void initialize() {
        // This method is called automatically after FXML is loaded.
        System.out.println("AddBookController initialized.");
    }

    public static void show(int id, String username, LibraryUserController parentController) throws Exception {
        FXMLLoader loader = new FXMLLoader(BorrowBookController.class.getResource("/userController/borrowBook.fxml"));
        Parent root2 = loader.load();

        book_id = id;

        BorrowBookController controller = loader.getController();
        controller.setParentController(parentController);

        Stage stage2 = new Stage();
        stage2.setScene(new Scene(root2));
        stage2.setTitle("Borrow Book");
        stage2.setResizable(false);
        stage2.show();
    }

    @FXML
    private void handleBorrowButton() {
        String borrowedDate = borrowedDateField.getText();
        String returnedDate = returnedDateField.getText();
        String username = SessionManager.getUsername(); // Replace with the actual username

        TransactionService transactionService = new TransactionService();
        transactionService.borrowBook(username, book_id, borrowedDate, returnedDate);

        // Close the window after borrowing the book
        Stage stage2 = (Stage) borrowedDateField.getScene().getWindow();
        stage2.close();

        parentController.refreshBookList();
    }

    @FXML
    private void handleClose(ActionEvent event) {
        // Handle "Quick Add" button click event
        Stage stage2 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage2.close();
    }
}
