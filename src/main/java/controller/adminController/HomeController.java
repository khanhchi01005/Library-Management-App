package controller.adminController;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import model.SessionManager;
import model.user.Account;
import services.home.HomeService;
import services.user.Credentials;

public class HomeController {

    private Account account;

    @FXML
    private Label booksLabel;

    @FXML
    private Label transactionsLabel;

    @FXML
    private Label membersLabel;

    @FXML
    private Label totalBooksLabel;

    @FXML
    private Label userIDField, usernameField, roleIDField, fullnameField, phoneNumberField, emailField;

    @FXML
    private Label fullnamebigField;

    @FXML
    private Button libraryButton;

    @FXML
    private Button accountButton;

    @FXML
    private Button borrowButton;

    @FXML
    private Button returnButton;

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

    @FXML
    public void initialize() {
        Credentials credentials = new Credentials();

        // Ví dụ gọi getAccountInfo với một username cố định hoặc từ một nguồn khác
        String username = SessionManager.getUsername(); // Có thể thay thế bằng giá trị từ input
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
            System.out.println("No user found.");
        }

        HomeService homeService = new HomeService();
        booksLabel.setText(String.valueOf(homeService.getTotalBooks()));
        transactionsLabel.setText(String.valueOf(homeService.getTotalTransactions()));
        membersLabel.setText(String.valueOf(homeService.getTotalAccounts()));
        totalBooksLabel.setText(String.valueOf(homeService.getTotalAvailableBooks()));
    }
}
