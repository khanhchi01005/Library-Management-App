package adminController;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import model.book.Book;
import services.book.BookService;

import static services.book.BookService.*;

public class DetailBookController {

    // Kết nối các thành phần trong FXML với controller
    @FXML
    private ImageView bookImageView;  // Hình ảnh sách

    @FXML
    private Label titleLabel;  // Tên sách
    @FXML
    private Label authorLabel; // Tác giả
    @FXML
    private Label publisherLabel; // Nhà xuất bản
    @FXML
    private Label categoryLabel; // Thể loại
    @FXML
    private Label yearLabel; // Năm xuất bản
    @FXML
    private Label pageCountLabel; // Số trang
    @FXML
    private Label availableLabel; // Số sách còn lại

    @FXML
    private TextArea descriptionArea; // Mô tả sách

    // Phương thức khởi tạo controller, nơi bạn có thể thiết lập các giá trị ban đầu
    public void initialize() {
        System.out.println("DetailBookController initialized.");
    }

    public static void show(Book book) throws Exception {
        FXMLLoader loader = new FXMLLoader(DetailBookController.class.getResource("/adminController/detailBook.fxml"));
        Parent root2 = loader.load();

        DetailBookController controller = loader.getController();
        controller.setBookData(book); // Gửi dữ liệu sách vào controller


        Stage stage2 = new Stage();
        stage2.setScene(new Scene(root2));
        stage2.setTitle("Detail book");
        stage2.setResizable(false);
        stage2.show();
    }

    public void setBookData(Book book) {
        BookService bookService = new BookService();
        bookService.viewOneBook(book.getId());

        try {
            titleLabel.setText(viewTitle);
            authorLabel.setText(viewAuthor);
            publisherLabel.setText(viewPublishers);
            categoryLabel.setText(viewCategory);
            yearLabel.setText(String.valueOf(viewYear));
            pageCountLabel.setText(String.valueOf(viewPages));
            availableLabel.setText(String.valueOf(viewAvailable_amount));
            descriptionArea.setText(viewDescription);

            Image image = new Image("file:" + viewImage); // Use "file:" for local files
            bookImageView.setImage(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

