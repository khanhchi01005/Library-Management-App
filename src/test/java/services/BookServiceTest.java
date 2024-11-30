package services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;
import model.book.Book;
import services.book.BookService;


import static org.junit.jupiter.api.Assertions.*;

class BookServiceTest {
    private BookService bookService;

    @BeforeEach
    void setUp() throws SQLException {
        bookService = new BookService();
    }

    @Test
    void getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        assertNotNull(books);
    }

    @Test
    void viewOneBook() {
        List<Book> books = bookService.getAllBooks();
        Book book = books.getFirst();
        int book_id = book.getId();
        bookService.viewOneBook(book_id);
        assertNotNull(book);
    }

    @Test
    void searchBookByTitle() {

    }

    @Test
    void searchBookByGenre() {
    }

    @Test
    void searchBookByPublisher() {
    }

    @Test
    void addBook() {
    }

    @Test
    void addBookManually() {
    }

    @Test
    void getBookByTitle() {
    }

    @Test
    void deleteBook() {
    }

    @Test
    void modifyTitle() {
    }

    @Test
    void modifyAuthor() {
    }

    @Test
    void modifyCategory() {
    }

    @Test
    void modifyYear() {
    }

    @Test
    void modifyPages() {
    }

    @Test
    void modifyAvailable_amount() {
    }

    @Test
    void modifyImage() {
    }

    @Test
    void modifyDescription() {
    }

    @Test
    void modifyPublisher() {
    }
}