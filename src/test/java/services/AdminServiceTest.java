
package services;

import model.user.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.user.AdminService;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AdminServiceTest {

    private AdminService adminService;

    @BeforeEach
    void setUp() throws SQLException {
        adminService = new AdminService();
    }

    @Test
    void viewAccount() throws SQLException {
        List<Account> accounts = adminService.viewAccount();
        assertNotNull(accounts);
    }

    @Test
    void deleteUser() throws SQLException {
        List<Account> accounts = adminService.viewAccount();
        Account account = accounts.getFirst();
        int user_id = account.getId();
        adminService.deleteUser(user_id);
        List<Account> accountsAfterDelete = adminService.viewAccount();
        assertEquals(accounts.size() - 1, accountsAfterDelete.size());
    }

}
