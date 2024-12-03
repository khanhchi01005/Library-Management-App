module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.json;
    requires mysql.connector.j;
    requires java.net.http;
    requires java.mail;
    requires com.google.zxing;
    requires java.desktop;
    requires javafx.media;

    opens app to javafx.fxml;
    exports app;

    opens adminController to javafx.fxml;
    opens model to javafx.base; // Allow access to the model package
    exports adminController;

    opens userController to javafx.fxml;
    opens utils.QR to javafx.fxml;
    exports userController;
    opens model.user to javafx.base;
    opens model.transaction to javafx.base;
    opens model.book to javafx.base;
    exports utils.QR;
}