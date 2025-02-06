package services.user;

import model.Database;
import model.user.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import model.SessionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CredentialsTest {
    private Credentials credentials;

    @BeforeEach
    void setUp() {
        credentials = new Credentials();
    }

    @Test
    void register() {
        credentials.register("anguyen", "nguyenvana", "ST01", "Nguyen Van A", "nguyena@gmail.com", "0987654321");
        // Assuming the database is pre-populated with the registered account
        String query = "SELECT * FROM users WHERE username = 'anguyen'";
        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            assertTrue(rs.next(), "Account should be registered in the database");
            assertEquals("anguyen", rs.getString("username"));
            assertEquals("nguyenvana", rs.getString("password"));
            assertEquals("ST01", rs.getString("identification_id"));
            assertEquals("Nguyen Van A", rs.getString("fullname"));
            assertEquals("nguyena@gmail.com", rs.getString("email"));
            assertEquals("0987654321", rs.getString("phonenumber"));
        } catch (SQLException e) {
            fail("SQL exception occurred: " + e.getMessage());
        }
    }
}