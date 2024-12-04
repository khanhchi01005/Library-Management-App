package services.user;

import model.SessionManager;
import model.user.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;

import static services.book.BookService.*;

public class Credentials {

    public boolean checkIdentification(String identification_id) {
        String first_id = identification_id.substring(0, 2);
        String second_id = identification_id.substring(2, 4);
        //Kiem tra xem id nay ton tai trong database chua
        String query = "SELECT * FROM users WHERE identification_id = ?";
        try(Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement pstmt = connection.prepareStatement(query)){
            pstmt.setString(1, identification_id);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                return false;
            }
        } catch (SQLException e){
            System.err.println("Failed to check identification: " + e.getMessage());
        }

        //Kiem tra xem nguoi dung nhap vao id hop le khong
        if(first_id.equals("AD") && second_id.matches("[0-9]+") && identification_id.length() == 4 ) {
            return true;
        }
        else if(first_id.equals("ST") && second_id.matches("[0-9]+") && identification_id.length() == 4) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean checkUsername(String username){
        //Kiem tra xem username nay da ton tai torng database chua
        String query ="SELECT * FROM users WHERE username = ?";
        try(Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement pstmt = connection.prepareStatement(query)){
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                return false;
            }
        } catch (SQLException e){
            System.err.println("Failed to check username: " + e.getMessage());
        }
        return true;
    }

    public boolean checkEmail(String email){
        //Kiem tra xem email nay da ton tai trong database chua
        String query = "SELECT * FROM users WHERE email = ?";
        try(Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement pstmt = connection.prepareStatement(query)){
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                return false;
            }
        } catch (SQLException e){
            System.err.println("Failed to check email: " + e.getMessage());
        }

        //Kiem tra xem email nhap dung dinh dang chua
        if(email.matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")){
            return true;
        }
        else {
            return false;
        }
    }


    // kiem tra so dien thoai dung dinh dang chua
    public boolean checkPhonenumber (String phonenumber){
        if(phonenumber.matches("^[0-9]{10}$")){
            return true;
        }
        else {
            return false;
        }
    }

    public void register(String username, String password, String identificationId, String fullname, String email, String phonenumber) {
        if (checkUsername(username) && checkIdentification(identificationId) && checkEmail(email) && checkPhonenumber(phonenumber)) {
            String query = "INSERT INTO users (username, password, identification_id, fullname, email, phonenumber) VALUES (?, ?, ?, ?, ?, ?)";
            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                pstmt.setString(3, identificationId);
                pstmt.setString(4, fullname);
                pstmt.setString(5, email);
                pstmt.setString(6, phonenumber);
                pstmt.executeUpdate();

                System.out.println("Successfully registered");
            } catch (SQLException e) {
                System.err.println("Failed to register: " + e.getMessage());
            }
        } else {
            System.out.println("Failed to register, please re-enter your information");
        }
    }

    public void searchAccount(String username) {
        String query = "SELECT username, password, identification_id, fullname, email, phonenumber FROM users WHERE username LIKE ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, "%" + username + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Username: " + rs.getString("username"));
                System.out.println("Password: " + rs.getString("password"));
                System.out.println("Identification ID: " + rs.getString("identification_id"));
                System.out.println("Full Name: " + rs.getString("fullname"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("Phone Number: " + rs.getString("phonenumber"));
            }
        } catch (SQLException e) {
            System.err.println("Failed to search account: " + e.getMessage());
        }
    }

    // xu ly dang nhap
    public void login(String username, String password) {
        String query = "SELECT identification_id FROM users WHERE username = ? AND password = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            if (connection == null) {
                System.out.println("Connection failed.");
                return;
            }

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    SessionManager.setIdentificationId(rs.getString("identification_id"));
                    System.out.println("Login successfully");
                    SessionManager.setisLogin(true);
                } else {
                    System.out.println("Login failed");
                    SessionManager.setisLogin(false);
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
    };

    // xu ly dang xuat

    public void logout() {
        System.out.println("Logout successfully");
    };

    // xu ly thay doi mat khau
    public void changePassword(String username, String newPassword) {
        String query = "SELECT * FROM users WHERE username = ?";
        boolean check = false;
        try(Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement pstmt = connection.prepareStatement(query)){
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                check = true;
            }
        } catch (SQLException e){
            System.err.println("Failed to check email: " + e.getMessage());
        }
        if(check) {
            String update = "UPDATE users SET password = ?" + "WHERE username =?";
            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 PreparedStatement pstmt = connection.prepareStatement(update)) {
                pstmt.setString(1, newPassword);
                pstmt.setString(2, username);
                pstmt.executeUpdate();
                System.out.println("Password changed successfully");
            } catch (SQLException e) {
                System.err.println("Failed to change password: " + e.getMessage());
            }
        }
        else {
            System.out.println("Cannot find user to change password");
        }
    }

    public Account getAccountInfo(String username) {
        String query = "SELECT * FROM users WHERE username = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("user_id");
                String password = rs.getString("password");
                String identificationId = rs.getString("identification_id");
                String fullname = rs.getString("fullname");
                String email = rs.getString("email");
                String phonenumber = rs.getString("phonenumber");

                return new Account(id, username, password, identificationId, fullname, phonenumber, email);
            }
        } catch (SQLException e) {
            System.err.println("Failed to retrieve account information: " + e.getMessage());
        }
        return null; // Return null if no user is found or an error occurs
    }


}
