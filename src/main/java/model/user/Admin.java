package model.user;

import model.Database;
import services.book.BookService;
import services.transaction.BorrowService;
import services.transaction.ReturnService;
import services.user.AdminService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Admin extends Account {
    private ReturnService returnService = new ReturnService();
    private BookService service = new BookService();
    private BorrowService borrowService = new BorrowService();
    private AdminService adminService = new AdminService();

    public Admin() {

    }

    public Admin(String fullname, String username, String password, String email, String phonenumber, String user_identification_id) {
        super(fullname, username, password, email, phonenumber, user_identification_id);
    }

    //CAC THAO TAC LIEN QUAN DEN SACH
    //hien thi tat ca cac sach trong database
    public void adminViewBook(){
        viewBook();
    }

    //tim sach theo ten
    public void adminSearchByTitle(String title) {
        searchBookByTitle(title);
    }

    //tim sach theo the loai
    public void adminSearchByGenre(String category) {
        searchBookByTitle(category);
    }

    //tim sach theo ten
    public void adminSearchByPublisher(String publisher) {
        searchBookByPublisher(publisher);
    }

    //them sach vao database qua api
    public void addBookByApi (String api_key,String addTitle, int amount){
        service.addBook(api_key,addTitle,amount);
    }

    //them sach vao database thu cong
    public void addBookManually(String title, String author, String category, int year, int pages, int available_amount, String image, String description, String publisher) {
        try {
            BookService bookService = new BookService();
            bookService.addBookManually(title, author, category, year, pages, available_amount, image, description, publisher);
        } catch (IllegalArgumentException e) {
            System.err.println("Failed to add book: " + e.getMessage());
        }
    }

    //xoa sach
    public void deleteBook(int bookId) {
        service.deleteBook(bookId);
    }

    //sua cac thong tin cua sach
    //sua title
    public void modifyTitle(int bookId, String title){
        service.modifyTitle(bookId, title);

    }
    //sua author
    public void modifyAuthor(int bookId, String author) {
        service.modifyAuthor(bookId, author);
    }
    //sua category
    public void modifyCategory(int bookId, String category) {
        modifyCategory(bookId, category);
    }
    //sua year
    public void modifyYear(int bookId, int year) {
        modifyYear(bookId, year);
    }
    //sua pages
    public void modifyPages(int bookId, int pages) {
        modifyPages(bookId, pages);
    }
    //sua available_amount
    public void modifyAvailable_amount(int bookId, int available_amount) {
        modifyAvailable_amount(bookId, available_amount);
    }
    //sua image
    public void modifyImage(int bookId, String image) {
        modifyImage(bookId, image);
    }
    //sua description
    public void modifyDescription(int bookId, String description) {
        modifyDescription(bookId, description);
    }

    public void modifyPublisher(int bookId, String publisher) {
        modifyPublisher(bookId, publisher);
    }

    //CAC THAO TAC LIEN QUAN DEN STUDENT


    //dang ky tai koan admin
    public void adminRegister(String username, String password, String identificationId, String fullname, String email, String phonenumber) {
        register(username, password, identificationId, fullname, email, phonenumber);
    }

    //dang nhap tai khoan admin
    public void adminLogin(String username, String password) {
        login(username, password);

    }

    //thay doi mat khau
    public void adminChangePassword(String username, String newPassword) {
        changePassword(username, newPassword);
    }

    //dang xuat
    public void adminLogout() {
        logout();
    }

    // xem danh sach cac student
    public void viewAccount() {
        adminService.viewAccount();
    }

    // xoa tai khoan student
//    public void deleteUser(String user_id) {
//        String query = "DELETE FROM users WHERE user_id = ?";
//        try (Connection conn = Database.getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(query)) {
//            pstmt.setString(1, user_id);
//            pstmt.executeUpdate();
//            System.out.println("Successfully deleted user with id: " + user_id);
//        } catch (SQLException e) {
//            System.err.println("Failed to delete user: " + e.getMessage());
//        }
//    }
}