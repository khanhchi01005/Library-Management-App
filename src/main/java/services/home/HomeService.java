package services.home;

import model.Database;
import model.user.Account;
import model.book.Book;
import model.transaction.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static services.book.BookService.*;

public class HomeService {
    public int getTotalBooks() {
        String query = "SELECT COUNT(*) AS total_books FROM books";
        int totalBooks = 0;

        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                totalBooks = rs.getInt("total_books");
            }
        } catch (SQLException e) {
            System.err.println("Failed to get total books: " + e.getMessage());
        }

        return totalBooks;
    }

    public int getTotalTransactions() {
        String query = "SELECT COUNT(*) AS total_transactions FROM transaction";
        int totalTransactions = 0;

        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                totalTransactions = rs.getInt("total_transactions");
            }
        } catch (SQLException e) {
            System.err.println("Failed to get total transactions: " + e.getMessage());
        }

        return totalTransactions;
    }
    public int getTotalAccounts() {
        String query = "SELECT COUNT(*) AS total_accounts FROM users";
        int totalAccounts = 0;

        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                totalAccounts = rs.getInt("total_accounts");
            }
        } catch (SQLException e) {
            System.err.println("Failed to get total accounts: " + e.getMessage());
        }

        return totalAccounts;
    }

    public int getTotalAvailableBooks() {
        String query = "SELECT SUM(available_amount) AS total_available FROM books";
        int totalAvailableBooks = 0;

        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                totalAvailableBooks = rs.getInt("total_available");
            }
        } catch (SQLException e) {
            System.err.println("Failed to get total available books: " + e.getMessage());
        }

        return totalAvailableBooks;
    }
}
