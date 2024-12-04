package controller.adminController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.book.Book;
import services.book.BookService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import static services.book.BookService.*;
import static services.book.BookService.viewDescription;

public class EditBookController {

    private LibraryAdminController parentController;

    public void setParentController(LibraryAdminController parentController) {
        this.parentController = parentController;
    }

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

    public static void show(Book book, LibraryAdminController parentController) throws Exception {
        FXMLLoader loader = new FXMLLoader(EditBookController.class.getResource("/adminController/editBook.fxml"));
        Parent root2 = loader.load();

        EditBookController controller = loader.getController();
        controller.setParentController(parentController);
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
            bookImage.setImage(new Image("file:" + viewImage));
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
            saveImage(selectedBook.getId());

            System.out.println("Book details updated successfully.");

            Stage stage2 = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage2.close();

            parentController.refreshBookList();
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

    private void saveImage(int nextID) {
        String bookId = String.valueOf(nextID);

        // Lấy ảnh từ ImageView
        Image image = bookImage.getImage();

        if (image == null) {
            URL resource = getClass().getResource("/Images/macdinh.jpg");
            if (resource == null) {
                System.out.println("Ảnh không tìm thấy!");
                return;
            } else {
                image = new Image(resource.toString());
            }
        }

        // Đường dẫn thư mục lưu ảnh
        File imageDir = new File("src/main/resources/Images/" + nextID);

        // Kiểm tra và tạo thư mục nếu chưa tồn tại
        if (!imageDir.exists()) {
            if (imageDir.mkdirs()) {
                System.out.println("Thư mục Images đã được tạo.");
            } else {
                System.out.println("Không thể tạo thư mục Images.");
                return;
            }
        }

        // Đường dẫn file ảnh
        File outputFile = new File(imageDir, "bookImage" + ".jpg");

        PixelReader pixelReader = image.getPixelReader();
        WritableImage writableImage = new WritableImage((int) image.getWidth(), (int) image.getHeight());

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                writableImage.getPixelWriter().setColor(x, y, pixelReader.getColor(x, y));
            }
        }

        try {
            BufferedImage bufferedImage = new BufferedImage(
                    (int) writableImage.getWidth(),
                    (int) writableImage.getHeight(),
                    BufferedImage.TYPE_INT_ARGB
            );

            for (int x = 0; x < writableImage.getWidth(); x++) {
                for (int y = 0; y < writableImage.getHeight(); y++) {
                    javafx.scene.paint.Color color = writableImage.getPixelReader().getColor(x, y);
                    int r = (int) (color.getRed() * 255);
                    int g = (int) (color.getGreen() * 255);
                    int b = (int) (color.getBlue() * 255);
                    int a = (int) (color.getOpacity() * 255);
                    int argb = (a << 24) | (r << 16) | (g << 8) | b;
                    bufferedImage.setRGB(x, y, argb);
                }
            }

            ImageIO.write(bufferedImage, "PNG", outputFile);
            System.out.println("Ảnh đã được lưu thành công: " + outputFile.getAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Lỗi khi lưu ảnh.");
        }
    }

    @FXML
    private void handleClose(ActionEvent event) {
        // Handle "Quick Add" button click event
        Stage stage2 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage2.close();
    }
}

