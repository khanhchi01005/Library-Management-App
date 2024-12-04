package utils.AI;

import org.json.JSONObject;
import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.*;

import static services.book.BookService.*;

public class DeepgramTTS {

    private static final String DEEPGRAM_URL = "https://api.deepgram.com/v1/speak?model=aura-asteria-en";
    private static final String DEEPGRAM_API_KEY = "97637cbb390c858b7eb9657c07ec41e36738b16d";// Load from environment variables

    static class Book {
        String title, author, publisher, category, description;
        int year, pages, availableAmount;

        public Book(String title, String author, String publisher, String category, String description, int year, int pages, int availableAmount) {
            this.title = title;
            this.author = author;
            this.publisher = publisher;
            this.category = category;
            this.description = description;
            this.year = year;
            this.pages = pages;
            this.availableAmount = availableAmount;
        }
    }

    public static void textToSpeech(Book book) {
        if (book == null) {
            System.err.println("Book details are missing. Cannot generate TTS.");
            return;
        }

        JSONObject jsonPayload = new JSONObject();
        jsonPayload.put("text", "This is PageTurners Library, I will provide this book information. Book title: "
                + book.title + ", written by " + book.author + ", published by " + book.publisher
                + " in " + book.year + " with " + book.pages + " pages, " + book.availableAmount
                + " copies available. Description: " + book.description
                + ". It belongs to the " + book.category + " category.");

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(DEEPGRAM_URL))
                .header("Authorization", "Token " + DEEPGRAM_API_KEY)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload.toString()))
                .build();

        try {
            HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
            if (response.statusCode() == 200) {
                String audioFilePath = "output.mp3";
                try (OutputStream fileStream = new FileOutputStream(audioFilePath)) {
                    response.body().transferTo(fileStream);
                }
                System.out.println("Audio download complete: " + audioFilePath);
            } else {
                System.err.println("Error: HTTP " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Error generating TTS: " + e.getMessage());
        }
    }

    public static Book hearOneBook(int bookId) {
        String query = "SELECT title, author, category, year, pages, available_amount, description, publisher FROM books WHERE book_id = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setInt(1, bookId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Book(
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("publisher"),
                        rs.getString("category"),
                        rs.getString("description"),
                        rs.getInt("year"),
                        rs.getInt("pages"),
                        rs.getInt("available_amount")
                );
            }
        } catch (SQLException e) {
            System.err.println("Failed to view book: " + e.getMessage());
        }
        return null;
    }

    //    public static void main(String[] args) {
//        int bookId = 37; // Example book ID
//        Book book = hearOneBook(bookId);
//        textToSpeech(book);
//    }
    public void hearBook(int bookId) {
        Book book = hearOneBook(bookId);
        textToSpeech(book);
    }
}