package model;

public class SessionManager {
    private static String username;
    private static String identificationId;
    private static Boolean isLogin = false;

    // Phương thức để thiết lập username
    public static void setUsername(String user) {
        username = user;
    }

    // Phương thức để lấy username
    public static String getUsername() {
        return username;
    }

    public static void setIdentificationId(String id) {
        identificationId = id;
    }

    public static String getIdentificationId() {
        return identificationId;
    }

    // Phương thức để thiết lập username
    public static void setisLogin(Boolean Login) {
        isLogin = Login;
    }

    // Phương thức để lấy username
    public static Boolean getisLogin() {
        return isLogin;
    }
}
