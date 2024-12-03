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
        List<Book> books = bookService.getAllBooks();
        Book book = books.getFirst();
        String title = book.getTitle();
        bookService.searchBookByTitle(title);
        assertNotNull(book);

    }

    @Test
    void searchBookByGenre() {
        List<Book> books = bookService.getAllBooks();
        Book book = books.getFirst();
        String category = book.getCategory();
        bookService.searchBookByGenre(category);
        assertNotNull(book);
    }

    @Test
    void searchBookByPublisher() {
        List<Book> books = bookService.getAllBooks();
        Book book = books.getFirst();
        String publisher = book.getTitle();
        bookService.searchBookByPublisher(publisher);
        assertNotNull(book);
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
        List<Book> books = bookService.getAllBooks();
        Book book = books.getFirst();
        int bookId = book.getId();
        String title = book.getTitle();
        bookService.modifyTitle(bookId,title);
        assertNotNull(book);
    }

    @Test
    void modifyAuthor() {
        List<Book> books = bookService.getAllBooks();
        Book book = books.getFirst();
        int bookId = book.getId();
        String author = book.getAuthor();
        bookService.modifyAuthor(bookId,author);
        assertNotNull(book);
    }

    @Test
    void modifyCategory() {
        List<Book> books = bookService.getAllBooks();
        Book book = books.getFirst();
        int bookId = book.getId();
        String category = book.getCategory();
        bookService.modifyCategory(bookId,category);
        assertNotNull(book);
    }

    @Test
    void modifyYear() {
        List<Book> books = bookService.getAllBooks();
        Book book = books.getFirst();
        int bookId = book.getId();
        int year = book.getYear();
        bookService.modifyYear(bookId,year);
        assertNotNull(book);
    }

    @Test
    void modifyPages() {
        List<Book> books = bookService.getAllBooks();
        Book book = books.getFirst();
        int bookId = book.getId();
        int pages  = book.getPages();
        bookService.modifyPages(bookId,pages);
        assertNotNull(book);
    }

    @Test
    void modifyAvailable_amount() {
        List<Book> books = bookService.getAllBooks();
        Book book = books.getFirst();
        int bookId = book.getId();
        int amount = book.getAvailableAmount();
        bookService.modifyAvailable_amount(bookId,amount);
        assertNotNull(book);
    }

    @Test
    void modifyImage() {
        List<Book> books = bookService.getAllBooks();
        Book book = books.getFirst();
        int bookId = book.getId();
        String image = book.getImage();
        bookService.modifyImage(bookId,image);
        assertNotNull(book);
    }

    @Test
    void modifyDescription() {
        List<Book> books = bookService.getAllBooks();
        Book book = books.getFirst();
        int bookId = book.getId();
        String description = book.getDescription();
        bookService.modifyAuthor(bookId,description);
        assertNotNull(book);
    }

    @Test
    void modifyPublisher() {
        List<Book> books = bookService.getAllBooks();
        Book book = books.getFirst();
        int bookId = book.getId();
        String publisher = book.getPublisher();
        bookService.modifyPublisher(bookId,publisher);
        assertNotNull(book);
    }
}