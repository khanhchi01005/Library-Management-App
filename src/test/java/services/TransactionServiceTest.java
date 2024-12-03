package services;

import model.transaction.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.transaction.TransactionService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransactionServiceTest {
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        transactionService = new TransactionService();
    }

    @Test
    void getAllTransactions() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        assertNotNull(transactions);
    }

    @Test
    void getUserTransactions() {
        List<Transaction> transactions = transactionService.getUserTransactions("ST01");
        assertNull(transactions);
        transactions = transactionService.getUserTransactions("Nguyen Van B");
        assertNotNull(transactions);
    }

    @Test
    void getReturnTransactions() {
        List<Transaction> transactions = transactionService.getReturnTransactions();
        assertNotNull(transactions);
    }

    @Test
    void getReturnUserTransactions() {
        List<Transaction> transactions = transactionService.getReturnUserTransactions("ST01");
        assertNull(transactions);
        transactions = transactionService.getReturnUserTransactions("Nguyen Van B");
        assertNotNull(transactions);
    }

    @Test
    void borrowBook() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        assertFalse(transactions.isEmpty(), "Transaction list should not be empty");
        Transaction transaction = transactions.get(0);
        String username = transaction.getUserName();
        int book_id = transaction.getBookId();
        String borrowed_date = transaction.getBorrowedDate();
        String returned_date = transaction.getReturnedDate();
        transactionService.borrowBook(username, book_id, borrowed_date, returned_date);
        assertNotNull(transaction);
    }

    @Test
    void acceptTransaction() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        assertFalse(transactions.isEmpty(), "Transaction list should not be empty");
        Transaction transaction = transactions.get(0);
        transactionService.acceptTransaction(transaction.getId());
        assertNotNull(transaction);
    }

    @Test
    void confirmTransaction() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        assertFalse(transactions.isEmpty(), "Transaction list should not be empty");
        Transaction transaction = transactions.get(0);
        transactionService.confirmTransaction(transaction.getId());
        assertNotNull(transaction);
    }

    @Test
    void deleteTransaction() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        assertFalse(transactions.isEmpty(), "Transaction list should not be empty");
        Transaction transaction = transactions.get(0);
        transactionService.deleteTransaction(transaction.getId());
        assertNotNull(transaction);
    }

    @Test
    void overdueNoti() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        assertFalse(transactions.isEmpty(), "Transaction list should not be empty");
        Transaction transaction = transactions.get(0);
        transactionService.overdueNoti(transaction.getId());
        assertNotNull(transaction);
    }
}