package model;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static Database instance;

    private Connection connection;

    Dotenv dotenv = Dotenv.load();

    private final String url = dotenv.get("url");
    private final String username = dotenv.get("user");
    private final String password =  dotenv.get("password");

    // Constructor private để ngăn việc tạo instance từ bên ngoài
    private Database() {
        try {
            // Khởi tạo kết nối
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to connect to database");
        }
    }

    // Phương thức public để lấy instance duy nhất
    public static Database getInstance() {
        if (instance == null) {
            synchronized (Database.class) {
                if (instance == null) { // Double-Checked Locking
                    instance = new Database();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            // Kiểm tra nếu kết nối đã bị đóng, tái tạo kết nối
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(url, username, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to reconnect to the database");
        }
        return connection;
    }

}