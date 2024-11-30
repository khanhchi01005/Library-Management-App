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
        TransactionService transactionService = new TransactionService();
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

}