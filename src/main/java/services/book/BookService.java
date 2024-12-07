package services.book;

import model.Database;
import model.book.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import services.book.QR;

public class BookService {
    public static final String DB_URL = "jdbc:mysql://localhost:3306/uet_library";
    public static final String DB_USER = "root";
    public static final String DB_PASSWORD = "123456";
    public static final String apiKey = "AIzaSyBfjT51wJH2HsApA1swWIU_66ngAmS3C9k";
    public static ResultSet rs;
    public static String viewTitle;
    public static String viewAuthor;;
    public static String viewCategory;
    public static int viewYear;
    public static int viewPages;
    public static int viewAvailable_amount;
    public static String viewImage;
    public static String viewDescription;
    public static String viewPublishers;

    public BookService() {
    }

    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String query = "SELECT  book_id, title, image, author, publisher, category, available_amount, pages FROM books";

        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Book book = new Book(
                        rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("category"),
                        rs.getInt("pages"),
                        rs.getInt("available_amount"),
                        rs.getString("image"),
                        rs.getString("publisher")
                );
                books.add(book);
            }
        } catch (SQLException e) {
            System.err.println("Failed to fetch books: " + e.getMessage());
            e.printStackTrace();  // More detailed logging
        }
        return books;
    }

    // xem 1 cuon sach bat ky
    public void viewOneBook(int bookId){
        String query = "SELECT title,author,category,year, pages,available_amount,image,description,publisher FROM books WHERE book_id = ?";
        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)){
            pstmt.setInt(1, bookId);

            rs = pstmt.executeQuery();
            while (rs.next()){
                viewTitle = rs.getString("title");
                viewAuthor=  rs.getString("author");
                viewCategory = rs.getString("category");
                viewYear =  rs.getInt("year");
                viewPages =  rs.getInt("pages");
                viewAvailable_amount=  rs.getInt("available_amount");
                viewImage = rs.getString("image");
                viewDescription = rs.getString("description");
                viewPublishers = rs.getString("publisher");
            }
        } catch (SQLException e) {
            System.err.println("Failed to view book " + e.getMessage());
        }
    }

    public String getBookTitle(int bookId){
        String scanTitle = "";
        String query = "SELECT title FROM books WHERE book_id = ?";
        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1,bookId);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                scanTitle = rs.getString("title");
            }
        } catch (SQLException e) {
            System.err.println("Failed to search book: " + e.getMessage());
        }
        return scanTitle;
    }

    // tim kiem sach theo ten
    public void searchBookByTitle(String title){
        String query = "SELECT * FROM books WHERE title LIKE ?";
        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1,"%" + title + "%");
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                System.out.println("Title: " + rs.getString("title"));
                System.out.println("Author: " +rs.getString("author"));
                System.out.println("Category: " + rs.getString("category"));
                System.out.println("Year: "   + rs.getInt("year"));
                System.out.println("Pages: "+ rs.getInt("pages"));
                System.out.println("Available amount: " + rs.getInt("available_amount"));
                System.out.println("Image: " + rs.getString("image"));
                System.out.println("Description: " + rs.getString("description"));
                System.out.println("Publisher: " +rs.getString("publisher"));
            }
        } catch (SQLException e) {
            System.err.println("Failed to search book: " + e.getMessage());
        }
    }

    // tim kiem sach theo the loai
    public void searchBookByGenre(String category){
        String query = "SELECT * FROM books WHERE category LIKE ?";
        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1,"%" + category + "%");
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                System.out.println("Title: " + rs.getString("title"));
                System.out.println("Author: " +rs.getString("author"));
                System.out.println("Category: " + rs.getString("category"));
                System.out.println("Year: "   + rs.getInt("year"));
                System.out.println("Pages: "+ rs.getInt("pages"));
                System.out.println("Available amount: " + rs.getInt("available_amount"));
                System.out.println("Image: " + rs.getString("image"));
                System.out.println("Description: " + rs.getString("description"));
                System.out.println("Publisher: " +rs.getString("publisher"));
            }
        } catch (SQLException e) {
            System.err.println("Failed to search book: " + e.getMessage());
        }
    }

    // tim kiem sach theo NXB
    public void searchBookByPublisher(String publisher) {
        String query = "SELECT * FROM books WHERE publisher LIKE ?";
        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1,"%" + publisher + "%");
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                System.out.println("Title: " + rs.getString("title"));
                System.out.println("Author: " +rs.getString("author"));
                System.out.println("Category: " + rs.getString("category"));
                System.out.println("Year: "   + rs.getInt("year"));
                System.out.println("Pages: "+ rs.getInt("pages"));
                System.out.println("Available amount: " + rs.getInt("available_amount"));
                System.out.println("Image: " + rs.getString("image"));
                System.out.println("Description: " + rs.getString("description"));
                System.out.println("Publisher: " +rs.getString("publisher"));
            }
        } catch (SQLException e) {
            System.err.println("Failed to search book: " + e.getMessage());
        }
    }
    //them sach
    public void addBook(String api_key, String addTitle, int amount) {
        // Gọi hàm bookApi để lấy danh sách sách từ Google Books API
        String numberSearching = "10";
        List<Book> bookList = Book.bookApi(api_key, addTitle, numberSearching);

        String query = "INSERT INTO books (title, author, category, year, pages, available_amount, image, description, publisher) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            for (Book newBook : bookList) {
                pstmt.setString(1, newBook.getTitle());
                pstmt.setString(2, newBook.getAuthor());
                pstmt.setString(3, newBook.getCategory());
                pstmt.setInt(4, newBook.getYear());
                pstmt.setInt(5, newBook.getPages());
                pstmt.setInt(6, newBook.getAvailableAmount());
                pstmt.setString(7, newBook.getImage());
                pstmt.setString(8, newBook.getDescription());
                pstmt.setString(9, newBook.getPublisher());

                pstmt.addBatch(); // Thêm câu lệnh vào batch
            }

            pstmt.executeBatch(); // Thực thi tất cả câu lệnh trong batch
            System.out.println("Successfully added " + bookList.size() + " books.");
        } catch (SQLException e) {
            System.err.println("Failed to add books: " + e.getMessage());
        }
    }

    public int addBookManually(String title, String author, String category, int year, int pages, int available_amount, String image, String description, String publisher) {
        String query = "INSERT INTO books (title, author, category, year, pages, available_amount, image, description, publisher) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int bookId = -1; // Khởi tạo giá trị mặc định

        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, title);
            pstmt.setString(2, author);
            pstmt.setString(3, category);
            pstmt.setInt(4, year);
            pstmt.setInt(5, pages);
            pstmt.setInt(6, available_amount);
            pstmt.setString(7, image);
            pstmt.setString(8, description);
            pstmt.setString(9, publisher);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        bookId = rs.getInt(1); // Lấy ID của sách vừa thêm
                    }
                }
            } else {
                System.out.println("Book was not added.");
            }

            QR qr = new QR();
            qr.createQR(title, bookId);

        } catch (SQLException e) {
            System.err.println("Failed to add new book: " + e.getMessage());
        }
        return bookId;
    }

    public void updateBookImage(int bookId, String newImageUrl) {
        String query = "UPDATE books SET image = ? WHERE book_id = ?";

        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, newImageUrl);
            pstmt.setInt(2, bookId);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Image updated successfully for book ID: " + bookId);
            } else {
                System.out.println("No book found with ID: " + bookId);
            }

        } catch (SQLException e) {
            System.err.println("Failed to update image: " + e.getMessage());
        }
    }


    //them sach
    public Book getBookByTitle(String title) {
        String query = "SELECT * FROM books WHERE title = ?";
        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, title);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Book(
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("category"),
                        rs.getInt("year"),
                        rs.getInt("pages"),
                        rs.getInt("available_amount"),
                        rs.getString("image"),
                        rs.getString("description"),
                        rs.getString("publisher")
                );
            }
        } catch (SQLException e) {
            System.err.println("Failed to get book: " + e.getMessage());
        }
        return null;
    }


    //xoa sach
    public void deleteBook(int bookId) {
        String query = "DELETE FROM books WHERE book_id =?";
        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)  ){
            pstmt.setInt(1,bookId);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Successfully deleted book with title: " + bookId);
            } else {
                System.err.println("No book found with id: " + bookId);
            }

        } catch (SQLException e){
            System.err.println("Failed to delete book: " +e.getMessage());
        }
    }

    //SUA THONG TIN SACH
    public void modifyTitle(int bookId, String title){
        String query ="UPDATE books SET title =? " + "WHERE book_id =?";

        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)){
            pstmt.setString(1,title);
            pstmt.setInt(2,bookId);

            pstmt.executeUpdate();
            System.out.print("Successfully modified book title with id: " + bookId);

            QR qr = new QR();
            qr.createQR(title, bookId);

        } catch(SQLException e){
            System.err.println("Failed to modify book: " + e.getMessage());
        }

    }
    //sua author
    public void modifyAuthor(int bookId, String author) {
        String query = "UPDATE books SET author = ? " + "WHERE book_id = ?";
        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)){
            pstmt.setString(1,author);
            pstmt.setInt(2,bookId);
            pstmt.executeUpdate();
            System.out.println("Successfully modified book author with id: " + bookId);
        } catch(SQLException e){
            System.err.println("Failed to modify book: " + e.getMessage());
        }
    }
    //sua category
    public void modifyCategory(int bookId, String category) {
        String query = "UPDATE books SET category = ? " + "WHERE book_id = ?";
        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)){
            pstmt.setString(1,category);
            pstmt.setInt(2,bookId);
            pstmt.executeUpdate();
            System.out.println("Successfully modified book category with id: " + bookId);
        } catch(SQLException e){
            System.err.println("Failed to modify book: " + e.getMessage());
        }
    }
    //sua year
    public void modifyYear(int bookId, int year) {
        String query = "UPDATE books SET year = ? " + "WHERE book_id = ?";
        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)){
            pstmt.setInt(1,year);
            pstmt.setInt(2,bookId);
            pstmt.executeUpdate();
            System.out.println("Successfully modified book published year with id: " + bookId);
        } catch(SQLException e){
            System.err.println("Failed to modify book: " + e.getMessage());
        }
    }
    //sua pages
    public void modifyPages(int bookId, int pages) {
        String query = "UPDATE books SET pages = ? " + "WHERE book_id = ?";
        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)){
            pstmt.setInt(1,pages);
            pstmt.setInt(2,bookId);
            pstmt.executeUpdate();
            System.out.println("Successfully modified book pages with id: " + bookId);
        } catch(SQLException e){
            System.err.println("Failed to modify book: " + e.getMessage());
        }
    }
    //sua available_amount
    public void modifyAvailable_amount(int bookId, int available_amount) {
        String query = "UPDATE books SET  available_amount= ? " + "WHERE book_id = ?";
        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)){
            pstmt.setInt(1,available_amount);
            pstmt.setInt(2,bookId);
            pstmt.executeUpdate();
            System.out.println("Successfully modified book available amount with id: " + bookId);
        } catch(SQLException e){
            System.err.println("Failed to modify book: " + e.getMessage());
        }
    }
    //sua image
    public void modifyImage(int bookId, String image) {
        String query = "UPDATE books SET image = ? " + "WHERE book_id = ?";
        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)){
            pstmt.setString(1,image);
            pstmt.setInt(2,bookId);
            pstmt.executeUpdate();
            System.out.println("Successfully modified book image with id: " + bookId);
        } catch(SQLException e){
            System.err.println("Failed to modify book: " + e.getMessage());
        }
    }
    //sua description
    public void modifyDescription(int bookId, String description) {
        String query = "UPDATE books SET description = ? " + "WHERE book_id = ?";
        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)){
            pstmt.setString(1,description);
            pstmt.setInt(2,bookId);
            pstmt.executeUpdate();
            System.out.println("Successfully modified book description with id: " + bookId);
        } catch(SQLException e){
            System.err.println("Failed to modify book: " + e.getMessage());
        }
    }

    public void modifyPublisher(int bookId, String publisher) {
        String query = "UPDATE books SET publisher =? " + "WHERE book_id =?";
        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)){
            pstmt.setString(1, publisher);
            pstmt.setInt(2, bookId);
            pstmt.executeUpdate();
            System.out.println("Successfully modified book publisher with id: " + bookId);
        } catch (SQLException e){
            System.err.println("Failed to modify book: " + e.getMessage());
        }
    }
}