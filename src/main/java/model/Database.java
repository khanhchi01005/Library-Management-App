package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    // Biến static giữ instance duy nhất của lớp
    private static Database instance;

    // Biến giữ Connection
    private Connection connection;

    // URL, username, password cho database
    private final String url = "jdbc:mysql://localhost:3306/uet_library";
    private final String username = "root";
    private final String password = "123456";

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