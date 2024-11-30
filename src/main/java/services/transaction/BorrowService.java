package services.transaction;

import model.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class BorrowService {

    // muon sach
    public void borrowBook(String user_id, int book_id) {
        //kiem tra xem sach con ko
        String checkAvailability = "SELECT available_amount FROM book WHERE book_id = ?";
        String insertBorrowed = "INSERT INTO borrowed (borrowed_date, due_date, user_id, book_id, state, book_status) VALUES (?, ?, ?, ?, ?, ?)";
        String updateBook = "UPDATE book SET available_amount = available_amount - 1 WHERE book_id = ?";

        try (Connection conn = Database.getConnection()) {
            // kiem tra so luong sach
            try (PreparedStatement checkStmt = conn.prepareStatement(checkAvailability)) {
                checkStmt.setInt(1, book_id);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next()) {
                    if (rs.getInt("available_amount") <= 0) {
                        System.out.println("model.book.Book is out of stock");
                        return;
                    }
                } else {
                    System.out.println("model.book.Book not found");
                    return;
                }
            }

            // thuc hien muon sach
            LocalDate borrowedDate = LocalDate.now();
            LocalDate dueDate = borrowedDate.plusDays(90);

            try (PreparedStatement insertStmt = conn.prepareStatement(insertBorrowed)) {
                insertStmt.setString(1, borrowedDate.toString());
                insertStmt.setString(2, dueDate.toString());
                insertStmt.setString(3, user_id);
                insertStmt.setInt(4, book_id);
                insertStmt.setString(5, "pending");
                insertStmt.setString(6, "pending");
                insertStmt.executeUpdate();
            }

            // cap nhat so luong sach
            try (PreparedStatement updateStmt = conn.prepareStatement(updateBook)) {
                updateStmt.setInt(1, book_id);
                updateStmt.executeUpdate();
            }
            System.out.println("model.book.Book borrowed successfully");
        } catch (SQLException e) {
            System.err.println("Failed to borrow book: " + e.getMessage());
        }
    }

    //xem cac sach da muon
    public void viewBorrowedBook(String user_id) {
        String query = "SELECT * FROM borrowed WHERE user_id =?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1,user_id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println("model.transaction.Borrowed date: " + rs.getString("borrowed_date"));
                System.out.println("Due date: " + rs.getString("due_date"));
                System.out.println("model.book.Book id: " + rs.getString("book_id"));
                System.out.println("State: " + rs.getString("state"));
                System.out.println("model.book.Book status: " + rs.getString("book_status"));
                System.out.println("====================================");
            }
        } catch (SQLException e){
            System.err.println("Failed to view borrowed book: " + e.getMessage());
        }
    }

    //duyet don muon sach
    public void reviewBorrowApplication (int borrowed_id, String book_status,String state) {
        String query = "UPDATE borrowed SET state = ?, book_status = ?"+  "WHERE borrowed_id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)){
            pstmt.setString(1,state);
            pstmt.setString(2,book_status);
            pstmt.setInt(3,borrowed_id);
            pstmt.executeUpdate();
            System.out.println("Successfully approved application with id: " + borrowed_id);
        } catch (SQLException e){
            System.err.println("Failed to approve application: " + e.getMessage());
        }
    }

}
