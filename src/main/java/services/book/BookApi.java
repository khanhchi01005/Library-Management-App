package services.book;
import model.book.Book;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;
import java.net.URLEncoder;
public class BookApi {
    public BookApi() {
    }

    // goi API de lay thong tin sach
    public static Book fetchBookInfor(String apiKey, String query, int available_amount) {
        Book newBook = new Book();
        try {
            // Mã hóa chuỗi query để loại bỏ ký tự không hợp lệ
            String encodedQuery = URLEncoder.encode(query, "UTF-8");
            String urlString = "https://www.googleapis.com/books/v1/volumes?q=" + encodedQuery + "&key=" + apiKey;

            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Parse JSON response
                JSONObject jsonResponse = new JSONObject(response.toString());
                JSONArray items = jsonResponse.getJSONArray("items");

                if (items != null && items.length() > 0) {
                    JSONObject item = items.getJSONObject(0);
                    JSONObject volumeInfo = item.getJSONObject("volumeInfo");

                    String bookTitle = volumeInfo.optString("title", "Unknown Title");
                    String author = volumeInfo.optJSONArray("authors") != null ? volumeInfo.getJSONArray("authors").optString(0, "Unknown Author") : "Unknown Author";
                    String category = volumeInfo.optJSONArray("categories") != null ? volumeInfo.getJSONArray("categories").optString(0, "Unknown Category") : "Unknown Category";
                    String yearString = volumeInfo.optString("publishedDate", "0");
                    int year = 0;
                    if (yearString.matches("\\d{4}")) {
                        year = Integer.parseInt(yearString.substring(0, 4));
                    }
                    int pages = volumeInfo.optInt("pageCount", 0);
                    String image = volumeInfo.optJSONObject("imageLinks") != null ? volumeInfo.getJSONObject("imageLinks").optString("thumbnail", "") : "";
                    String description = volumeInfo.optString("description", "No Description");
                    String publisher = volumeInfo.optString("publisher", "Unknown Publisher");

                    newBook = new Book(bookTitle, author, category, year, pages, available_amount, image, description,publisher);

                }
            } else {

                System.out.println("URL: " + urlString); // In ra URL để kiểm tra
                System.out.println("Response Code: " + conn.getResponseCode()); // Kiểm tra mã phản hồi
                System.out.println("GET request failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newBook;
    }
}
