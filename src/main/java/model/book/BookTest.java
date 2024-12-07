package model.book;
import java.util.List;

public class BookTest {
    public static void main(String[] args) {
        String apiKey = "AIzaSyBfjT51wJH2HsApA1swWIU_66ngAmS3C9k"; // Thay bằng API key thực tế
        String query = "Effective Java";
        String numberSearching = "5";

        // Gọi phương thức bookApi để lấy danh sách sách
        List<Book> books = Book.bookApi(apiKey, query, numberSearching);

        if (books != null && !books.isEmpty()) {
            System.out.println("API connection successful. Fetched " + books.size() + " books:");
            for (Book book : books) {
                System.out.println("Title: " + book.getTitle());
                System.out.println("Author: " + book.getAuthor());
                System.out.println("Category: " + book.getCategory());
                System.out.println("Year: " + book.getYear());
                System.out.println("Pages: " + book.getPages());
                System.out.println("Available Amount: " + book.getAvailableAmount());
                System.out.println("Publisher: " + book.getPublisher());
                System.out.println("Description: " + book.getDescription());
                System.out.println("Image: " + book.getImage());
                System.out.println("---------------------------------------");
            }
        } else {
            System.out.println("Failed to connect to API or fetch book information.");
        }
    }
}
