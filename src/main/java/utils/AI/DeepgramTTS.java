package utils.AI;

import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class DeepgramTTS {
    private static final String DEEPGRAM_URL = "https://api.deepgram.com/v1/speak?model=aura-asteria-en";
    private static final String DEEPGRAM_API_KEY = "97637cbb390c858b7eb9657c07ec41e36738b16d"; // Replace with your actual API key

    public static void Text2Speech(
        String name,
        String author,
        String publisher,
        String numbers,
        String pages
    ) {
        JSONObject jsonPayload = new JSONObject();
        jsonPayload.put("text", "This is PageTurners Library, I will provide this book information, book title : " + name + " written by " + author + ", published by " + publisher + ". There are " + numbers + " copies available, with " + pages + " pages in total.");
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
                System.out.println("Audio download complete");
            } else {
                System.err.println("Error: " + response.statusCode() + " - " + response.body());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        Text2Speech("Effective Java", "Joshua Bloch", "Addison-Wesley", "5", "416");
    }
}