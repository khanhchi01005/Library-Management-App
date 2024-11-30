package model.book;

public class BookTest {
    public static void main(String[] args) {
        String apiKey = "AIzaSyBfjT51wJH2HsApA1swWIU_66ngAmS3C9k"; // Replace with your actual API key
        String query = "Effective Java";
        int availableAmount = 5;

        Book book = Book.bookApi(apiKey, query, availableAmount);
        if (book != null && book.getTitle() != null) {
            System.out.println("API connection successful. Book title: " + book.getTitle());
        } else {
            System.out.println("Failed to connect to API or fetch book information.");
        }
    }
}