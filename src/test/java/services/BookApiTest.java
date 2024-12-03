package services;

import model.book.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.book.BookApi;

import static org.junit.jupiter.api.Assertions.*;
import static services.book.BookService.apiKey;

class BookApiTest {
    private BookApi bookApi;
    @BeforeEach
    void setUp() {
        bookApi = new BookApi();
    }

    @Test
    void fetchBookInfor() {
        BookApi bookApi = new BookApi();
        String bookTitle = "The Alchemist";
        int amount = 1;
        String bookInfor = String.valueOf(bookApi.fetchBookInfor(apiKey, bookTitle, amount));
        assertNotNull(bookInfor);

    }
}