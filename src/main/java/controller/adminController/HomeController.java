package controller.adminController;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import model.SessionManager;
import model.user.Account;
import services.home.HomeService;
import services.user.Credentials;

/**
 * Controller for managing the home page of the admin panel.
 * Displays user information, library statistics, and provides navigation options.
 */
public class HomeController {

    private Account account;

    // Labels for library statistics
    @FXML
    private Label booksLabel;
    @FXML
    private Label transactionsLabel;
    @FXML
    private Label membersLabel;
    @FXML
    private Label totalBooksLabel;

    // Labels for user account information
    @FXML
    private Label userIDField;
    @FXML
    private Label usernameField;
    @FXML
    private Label roleIDField;
    @FXML
    private Label fullnameField;
    @FXML
    private Label phoneNumberField;
    @FXML
    private Label emailField;

    @FXML
    private Label fullnamebigField;

    // Buttons for navigation
    @FXML
    private Button libraryButton;
    @FXML
    private Button accountButton;
    @FXML
    private Button borrowButton;
    @FXML
    private Button returnButton;

    // Panes for UI layout
    @FXML
    private Pane booksPane;
    @FXML
    private Pane transactionsPane;
    @FXML
    private Pane membersPane;
    @FXML
    private Pane openPane;
    @FXML
    private Pane totalBooksPane;

    /**
     * Initializes the home controller, populates user and library information.
     */
    @FXML
    public void initialize() {
        populateUserInfo();
        populateLibraryStatistics();
    }

    /**
     * Populates the user information fields with data from the session manager and credentials service.
     */
    private void populateUserInfo() {
        Credentials credentials = new Credentials();
        String username = SessionManager.getUsername(); // Retrieve username from session
        Account account = credentials.getAccountInfo(username);

        if (account != null) {
            userIDField.setText(String.valueOf(account.getId()));
            usernameField.setText(account.getUsername());
            roleIDField.setText(account.getIdentificationId());
            fullnameField.setText(account.getFullname());
            fullnamebigField.setText(account.getFullname());
            phoneNumberField.setText(account.getPhonenumber());
            emailField.setText(account.getEmail());
        } else {
            System.err.println("No user found for the provided username.");
        }
    }

    /**
     * Populates the library statistics labels with data from the home service.
     */
    private void populateLibraryStatistics() {
        HomeService homeService = new HomeService();

        try {
            booksLabel.setText(String.valueOf(homeService.getTotalBooks()));
            transactionsLabel.setText(String.valueOf(homeService.getTotalTransactions()));
            membersLabel.setText(String.valueOf(homeService.getTotalAccounts()));
            totalBooksLabel.setText(String.valueOf(homeService.getTotalAvailableBooks()));
        } catch (Exception e) {
            System.err.println("Error fetching library statistics: " + e.getMessage());
        }
    }
}
