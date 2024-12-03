package services.user;

import model.user.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import model.SessionManager;

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
        List<Account> accounts = credentials.register("anguyen", "nguyenvana", "ST01", "Nguyen Van A", "nguyena@gmail.com", "0987654321");
        assertNotNull(accounts);
        assertFalse(accounts.isEmpty(), "Account list should not be empty");
        Account account = accounts.get(0);
        assertEquals("anguyen", account.getUsername());
        assertEquals("nguyenvana", account.getPassword());
        assertEquals("ST01", account.getIdentificationId());
        assertEquals("Nguyen Van A", account.getFullname());
        assertEquals("nguyena@gmail.com", account.getEmail());
        assertEquals("0987654321", account.getPhonenumber());
    }

    @Test
    void searchAccount() {
    }

    @Test
    void login() {
        // Test valid login
        credentials.register("anguyen", "nguyenvana", "ST01", "Nguyen Van A", "nguyena@gmail.com", "0987654321");
        credentials.login("anguyen", "nguyenvana");
        assertTrue(SessionManager.isLogin());

        // Test invalid login
        credentials.login("anguyen", "wrongpassword");
        assertFalse(SessionManager.isLogin());
    }

    @Test
    void changePassword() {
        // Register a new account
        credentials.register("anguyen", "nguyenvana", "ST01", "Nguyen Van A", "nguyena@gmail.com", "0987654321");

        // Change the password
        credentials.changePassword("anguyen", "newpassword");

        // Verify the password has been changed
        credentials.login("anguyen", "newpassword");
        Object SessionManager;
        assertTrue(SessionManager.isLogin());
    }
}