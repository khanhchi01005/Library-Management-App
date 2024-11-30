package utils.QR;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Hashtable;

public class QR {
    public static void main(String[] args) {
        try {
            // Nội dung QR code
            String qrData = "This is Sherlock Holmes written by Sir Arthur Conan Doyle.";
            // Nội dung hiển thị thêm trên ảnh
            String additionalText = "Book Info:\nSherlock Holmes by Sir Arthur Conan Doyle\nPublished: 2013";

            // Cấu hình QR code
            Hashtable<EncodeHintType, Object> hintMap = new Hashtable<>();
            hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");

            // Tạo QR code
            MultiFormatWriter qrCodeWriter = new MultiFormatWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(qrData, BarcodeFormat.QR_CODE, 200, 200, hintMap);

            // Chuyển QR code thành hình ảnh
            BufferedImage qrImage = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < 200; x++) {
                for (int y = 0; y < 200; y++) {
                    qrImage.setRGB(x, y, bitMatrix.get(x, y) ? 0x000000 : 0xFFFFFF);
                }
            }

            // Tạo ảnh nền trắng lớn hơn để chứa QR code và văn bản
            int width = 400;  // Chiều rộng ảnh tổng
            int height = 600; // Chiều cao ảnh tổng
            BufferedImage combinedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = combinedImage.createGraphics();

            // Tô nền trắng
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, width, height);

            // Vẽ QR code ở giữa ảnh
            int qrX = (width - 200) / 2; // Canh giữa theo chiều ngang
            int qrY = 50;               // Vị trí QR code (cách trên 50px)
            g.drawImage(qrImage, qrX, qrY, null);

            // Vẽ thêm văn bản bên dưới QR code
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            int textX = 50;             // Lề trái của văn bản
            int textY = qrY + 250;      // Cách QR code một khoảng

            // Chia văn bản thành các dòng và vẽ
            String[] lines = additionalText.split("\n");
            for (String line : lines) {
                g.drawString(line, textX, textY);
                textY += 30; // Khoảng cách giữa các dòng
            }

            // Lưu hình ảnh
            String outputPath = "C://Users//admin//Downloads//QRWithText.png";
            ImageIO.write(combinedImage, "PNG", new File(outputPath));
            System.out.println("Image with QR and text generated successfully: " + outputPath);

            g.dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
