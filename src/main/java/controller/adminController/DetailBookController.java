package controller.adminController;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import model.book.Book;
import services.book.BookService;
import utils.AI.DeepgramTTS;
import utils.Sound.SoundUtils;

import static services.book.BookService.*;

/**
 * Controller for displaying detailed information about a book.
 */
public class DetailBookController {

    @FXML
    private ImageView bookImageView;

    @FXML
    private ImageView bookQRImageView;

    @FXML
    private ImageView loadingImage;

    @FXML
    private Button speakerButton;

    @FXML
    private Button muteButton;

    @FXML
    private Label titleLabel;

    @FXML
    private Label authorLabel;

    @FXML
    private Label publisherLabel;

    @FXML
    private Label categoryLabel;

    @FXML
    private Label yearLabel;

    @FXML
    private Label pageCountLabel;

    @FXML
    private Label availableLabel;

    @FXML
    private TextArea descriptionArea;

    private int id;

    /**
     * Initializes the controller.
     */
    public void initialize() {
        System.out.println("DetailBookController initialized.");
    }

    /**
     * Displays the details of a specific book.
     *
     * @param book The book to display.
     * @throws Exception If the FXML file cannot be loaded.
     */
    public static void show(Book book) throws Exception {
        FXMLLoader loader = new FXMLLoader(DetailBookController.class.getResource("/adminController/detailBook.fxml"));
        Parent root = loader.load();

        DetailBookController controller = loader.getController();
        controller.setBookData(book);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Detail Book");
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Displays the details of a book being searched.
     *
     * @param book The book to display.
     * @throws Exception If the FXML file cannot be loaded.
     */
    public static void showSearch(Book book) throws Exception {
        FXMLLoader loader = new FXMLLoader(DetailBookController.class.getResource("/adminController/detailBook.fxml"));
        Parent root = loader.load();

        DetailBookController controller = loader.getController();
        controller.setSearchingBookData(book);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Detail Book");
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Populates the UI with data from a searched book.
     *
     * @param book The book data to display.
     */
    public void setSearchingBookData(Book book) {
        try {
            titleLabel.setText(book.getTitle());
            authorLabel.setText(book.getAuthor());
            publisherLabel.setText(book.getPublisher());
            categoryLabel.setText(book.getCategory());
            yearLabel.setText(String.valueOf(book.getYear()));
            pageCountLabel.setText(String.valueOf(book.getPages()));
            availableLabel.setText("Searching");
            descriptionArea.setText(book.getDescription());

            Image image = new Image(book.getImage());
            bookImageView.setImage(image);

            speakerButton.setDisable(true);
            muteButton.setDisable(true);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load book data.");
        }
    }

    /**
     * Populates the UI with data from a book.
     *
     * @param book The book data to display.
     */
    public void setBookData(Book book) {
        BookService bookService = new BookService();
        bookService.viewOneBook(book.getId());
        this.id = book.getId();

        try {
            titleLabel.setText(viewTitle);
            authorLabel.setText(viewAuthor);
            publisherLabel.setText(viewPublishers);
            categoryLabel.setText(viewCategory);
            yearLabel.setText(String.valueOf(viewYear));
            pageCountLabel.setText(String.valueOf(viewPages));
            availableLabel.setText(String.valueOf(viewAvailable_amount));
            descriptionArea.setText(viewDescription);

            bookImageView.setImage(new Image("file:" + viewImage));
            bookQRImageView.setImage(new Image("file:src/main/resources/Images/" + this.id + "/QR.png"));
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load book details.");
        }
    }

    /**
     * Handles the action of playing the book's audio description.
     */
    public void handleBookSpeaker() {
        loadingImage.setImage(new Image("file:src/main/resources/Images/loading.gif"));
        loadingImage.setVisible(true);

        String currentBGM = "src/main/resources/Sound/bgm.m4a";
        SoundUtils.stopBackgroundMusic();

        Task<Void> ttsTask = new Task<>() {
            @Override
            protected Void call() {
                DeepgramTTS deepgramTTS = new DeepgramTTS();
                try {
                    deepgramTTS.hearBook(id);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        };

        ttsTask.setOnSucceeded(event -> {
            SoundUtils.playBackgroundMusic("output.mp3", false);
            MediaPlayer mediaPlayer = SoundUtils.getMediaPlayer();
            loadingImage.setVisible(false);

            mediaPlayer.setOnEndOfMedia(() -> SoundUtils.playBackgroundMusic(currentBGM, true));
        });

        ttsTask.setOnFailed(event -> {
            showAlert("Error", "Failed to generate audio.");
            loadingImage.setVisible(false);
        });

        new Thread(ttsTask).start();
    }

    /**
     * Handles the action of muting the current audio.
     */
    public void handleMute() {
        SoundUtils.stopBackgroundMusic();
        SoundUtils.playBackgroundMusic("src/main/resources/Sound/bgm.m4a", true);
    }

    /**
     * Displays an alert message to the user.
     *
     * @param title   The title of the alert.
     * @param message The content of the alert.
     */
    protected void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
}
