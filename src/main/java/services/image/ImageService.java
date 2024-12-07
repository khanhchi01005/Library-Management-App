package services.image;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.nio.file.*;

public class ImageService {
    public static void downloadImage(String imageUrl, String destinationPath) {
        try {
            // Tạo đối tượng URL từ chuỗi đường dẫn ảnh
            URL url = new URL(imageUrl);
            // Mở kết nối và lấy stream đầu vào
            InputStream in = url.openStream();

            // Tạo đối tượng Path cho thư mục đích (nếu thư mục không tồn tại, nó sẽ được tạo)
            Path outputPath = Paths.get(destinationPath);
            Files.createDirectories(outputPath.getParent()); // Tạo thư mục Images nếu không tồn tại

            // Tạo stream đầu ra và sao chép dữ liệu vào file
            try (OutputStream out = new FileOutputStream(outputPath.toFile())) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
                System.out.println("Image downloaded successfully to: " + destinationPath);
            }
        } catch (IOException e) {
            System.err.println("Error downloading the image: " + e.getMessage());
        }
    }

    public static void saveImage(int ID, ImageView bookImage) {

        // Lấy ảnh từ ImageView
        Image image = bookImage.getImage();

        if (image == null) {
            URL resource = ImageService.class.getResource("src/main/resources/Images/macdinh.jpg");
            if (resource == null) {
                System.out.println("Ảnh không tìm thấy!");
                return;
            } else {
                image = new Image(resource.toString());
            }
        }

        // Đường dẫn thư mục lưu ảnh
        File imageDir = new File("src/main/resources/Images/" + ID);

        // Kiểm tra và tạo thư mục nếu chưa tồn tại
        if (!imageDir.exists()) {
            if (imageDir.mkdirs()) {
                System.out.println("Thư mục Images đã được tạo.");
            } else {
                System.out.println("Không thể tạo thư mục Images.");
                return;
            }
        }

        // Đường dẫn file ảnh
        File outputFile = new File(imageDir, "bookImage" + ".jpg");

        PixelReader pixelReader = image.getPixelReader();
        WritableImage writableImage = new WritableImage((int) image.getWidth(), (int) image.getHeight());

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                writableImage.getPixelWriter().setColor(x, y, pixelReader.getColor(x, y));
            }
        }

        try {
            BufferedImage bufferedImage = new BufferedImage(
                    (int) writableImage.getWidth(),
                    (int) writableImage.getHeight(),
                    BufferedImage.TYPE_INT_ARGB
            );

            for (int x = 0; x < writableImage.getWidth(); x++) {
                for (int y = 0; y < writableImage.getHeight(); y++) {
                    javafx.scene.paint.Color color = writableImage.getPixelReader().getColor(x, y);
                    int r = (int) (color.getRed() * 255);
                    int g = (int) (color.getGreen() * 255);
                    int b = (int) (color.getBlue() * 255);
                    int a = (int) (color.getOpacity() * 255);
                    int argb = (a << 24) | (r << 16) | (g << 8) | b;
                    bufferedImage.setRGB(x, y, argb);
                }
            }

            ImageIO.write(bufferedImage, "PNG", outputFile);
            System.out.println("Ảnh đã được lưu thành công: " + outputFile.getAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Lỗi khi lưu ảnh.");
        }
    }
}

