package services;

import model.book.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.book.BookApi;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static services.book.BookService.apiKey;

class BookApiTest {
    private BookApi bookApi;

    @BeforeEach
    void setUp() {
        bookApi = new BookApi(); // Khởi tạo đối tượng bookApi trước mỗi test case
    }

    @Test
    void fetchBookList() {
        String bookTitle = "Effective Java"; // Tiêu đề sách cần tìm
        String numberSearching = "5";

        // Gọi phương thức bookApi để lấy danh sách sách
        List<Book> books = bookApi.fetchBookList(apiKey, bookTitle, numberSearching);

        // Kiểm tra nếu danh sách sách không null và có ít nhất 1 cuốn sách
        assertNotNull(books, "Book list should not be null.");
        assertTrue(books.size() > 0, "Book list should contain at least one book.");

        // Kiểm tra rằng danh sách có chứa ít nhất 20 cuốn sách
        assertTrue(books.size() >= 20, "Book list should contain at least 20 books.");

        // Kiểm tra các thuộc tính của từng cuốn sách trong danh sách
        for (Book book : books) {
            assertNotNull(book.getTitle(), "Book title should not be null.");
        }
    }
}

