package services.transaction;

import model.transaction.Transaction;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static services.book.BookService.*;


public class TransactionService {
    public TransactionService() {

    }

    //Thuc hien xem list cac don muon sach cua admin
    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        String query = "SELECT transaction_id, username, book_id, book_title, borrowed_date, returned_date FROM transaction WHERE state ='Not accepted' ";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Transaction transaction = new Transaction(
                        rs.getInt("transaction_id"),
                        rs.getString("username"),
                        rs.getInt("book_id"),
                        rs.getString("book_title"),
                        rs.getString("borrowed_date"),
                        rs.getString("returned_date")
                );
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            System.err.println("Failed to fetch transactions: " + e.getMessage());
            e.printStackTrace();  // More detailed logging
        }
        return transactions;
    }


    //hien danh sach muon cua user
    public List<Transaction> getUserTransactions(String username) {
        List<Transaction> transactions = new ArrayList<>();
        String query = "SELECT transaction_id, book_id, book_title, borrowed_date, returned_date FROM transaction WHERE username = ? , state ='Not accepted'";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Transaction transaction = new Transaction(
                        rs.getInt("transaction_id"),
                        rs.getInt("book_id"),
                        rs.getString("book_title"),
                        rs.getString("borrowed_date"),
                        rs.getString("returned_date")
                );
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            System.err.println("Failed to fetch transactions: " + e.getMessage());
            e.printStackTrace();  // More detailed logging
        }
        return transactions;
    }

    //Hien cac don tra sach
    public List<Transaction> getReturnTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        String query = "SELECT transaction_id, username, book_id, book_title, borrowed_date, returned_date,state FROM transaction WHERE state IN('Borrowing', 'Returned' )";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Transaction transaction = new Transaction(
                        rs.getInt("transaction_id"),
                        rs.getString("username"),
                        rs.getInt("book_id"),
                        rs.getString("book_title"),
                        rs.getString("borrowed_date"),
                        rs.getString("returned_date"),
                        rs.getString("state")
                );
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            System.err.println("Failed to fetch transactions: " + e.getMessage());
            e.printStackTrace();  // More detailed logging
        }
        return transactions;
    }

    public List<Transaction> getReturnUserTransactions(String username) {
        List<Transaction> transactions = new ArrayList<>();
        String query = "SELECT transaction_id, book_id, book_title, borrowed_date, returned_date, state " +
                "FROM transaction " +
                "WHERE username = ? AND state IN ('Borrowing', 'Returned')";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            // Set the username parameter
            pstmt.setString(1, username);

            // Execute the query
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Transaction transaction = new Transaction(
                        rs.getInt("transaction_id"),
                        rs.getInt("book_id"),
                        rs.getString("book_title"),
                        rs.getString("borrowed_date"),
                        rs.getString("returned_date"),
                        rs.getString("state")
                );
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            System.err.println("Failed to fetch transactions: " + e.getMessage());
            e.printStackTrace();  // More detailed logging
        }
        return transactions;
    }

    //xoa don muon sach
    public void deleteTransaction(int transaction_id) {
        String query = "DELETE FROM transaction WHERE transaction_id = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            // Set the transaction_id parameter
            pstmt.setInt(1, transaction_id);

            // Execute the query
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Failed to delete transaction: " + e.getMessage());
            e.printStackTrace();  // More detailed logging
        }
    }

    //duyet don muon sach
    public void acceptTransaction(int transaction_id) {
        String query = "UPDATE transaction SET state = 'Borrowing' WHERE transaction_id = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setInt(1, transaction_id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Failed to accept transaction: " + e.getMessage());
            e.printStackTrace();  // More detailed logging
        }
    }

    public void confirmTransaction(int transaction_id) {
        String query = "UPDATE transaction SET state = 'Returned' WHERE transaction_id = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setInt(1, transaction_id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Failed to confirm transaction: " + e.getMessage());
            e.printStackTrace();  // More detailed logging
        }
    }


    // student thuc hien muon sach
    public void borrowBook(String username, int book_id, String borrowed_date, String returned_date) {
        // kiem tra xem sach con ko
        String checkAvailability = "SELECT available_amount, title FROM books WHERE book_id = ?";
        String insertBorrowed = "INSERT INTO transaction (username, book_id, book_title, borrowed_date, returned_date,state) VALUES (?, ?, ?, ?, ?, ?)";
        String updateBook = "UPDATE books SET available_amount = available_amount - 1 WHERE book_id = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // kiem tra so luong sach va lay title
            String bookTitle = null;
            try (PreparedStatement checkStmt = connection.prepareStatement(checkAvailability)) {
                checkStmt.setInt(1, book_id);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next()) {
                    if (rs.getInt("available_amount") <= 0) {
                        System.out.println("model.book.Book is out of stock");
                        return;
                    }
                    bookTitle = rs.getString("title");
                } else {
                    System.out.println("model.book.Book not found");
                    return;
                }
            }

            // kiem tra ngay tra ve
            LocalDate borrowedDate = LocalDate.parse(borrowed_date);
            LocalDate returnedDate = LocalDate.parse(returned_date);
            if (returnedDate.isAfter(borrowedDate.plusDays(90))) {
                System.out.println("Returned date cannot be more than 90 days from the borrowed date");
                return;
            }

            // thuc hien muon sach
            try (PreparedStatement insertStmt = connection.prepareStatement(insertBorrowed)) {
                insertStmt.setString(1, username);
                insertStmt.setInt(2, book_id);
                insertStmt.setString(3, bookTitle);
                insertStmt.setString(4, borrowed_date);
                insertStmt.setString(5, returned_date);
                insertStmt.setString(6, "Not accepted");
                insertStmt.executeUpdate();
            }

            // cap nhat so luong sach
            try (PreparedStatement updateStmt = connection.prepareStatement(updateBook)) {
                updateStmt.setInt(1, book_id);
                updateStmt.executeUpdate();

            }
            System.out.println("model.book.Book borrowed successfully");
        } catch (SQLException e) {
            System.err.println("Failed to borrow book: " + e.getMessage());
        }
    }
}