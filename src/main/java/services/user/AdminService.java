package services.user;

import model.user.Account;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static services.book.BookService.*;

public class AdminService {

    public void viewProfile(int user_id) {
        String query = "SELECT * FROM users WHERE user_id = ?";
        try(Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, user_id);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                rs.getString("identification_id");
                rs.getString("fullname");
                rs.getString("phonenumber");
                rs.getString("email");
            }
        } catch(SQLException e){
            System.err.println("Failed to view student: " + e.getMessage());
        }
    }

    public List<Account> viewAccount() {
        List<Account> accounts = new ArrayList<>();
        String query = "SELECT user_id,username, password,identification_id,fullname,phonenumber,email FROM users ";
        try(Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement pstmt = connection.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                Account account = new Account(
                    rs.getInt("user_id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("identification_id"),
                    rs.getString("fullname"),
                    rs.getString("phonenumber"),
                    rs.getString("email")
                );
                accounts.add(account);
            }
        } catch(SQLException e){
            System.err.println("Failed to view student: " + e.getMessage());
        }
        return accounts;
    }

    // xoa tai khoan student
    public void deleteUser(int user_id) {
        String query = "DELETE FROM users WHERE user_id = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, user_id);
            pstmt.executeUpdate();
            System.out.println("Successfully deleted user with id: " + user_id);
        } catch (SQLException e) {
            System.err.println("Failed to delete user: " + e.getMessage());
        }
    }
}
