package services.book;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URLEncoder;
import java.util.Hashtable;
import services.book.BookService;


public class QR {
    public QR() {

    }

    public void createQR(String title, int bookID) {
        QR qrGenerator = new QR();

        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("searchTitle cannot be null or empty");
        }
        try{
            String encodedTitle = URLEncoder.encode(title, "UTF-8");
            String qrData = "https://google.com/search?q=" + encodedTitle;

            Hashtable<EncodeHintType, Object> hintMap = new Hashtable<>();
            hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");

            // Tạo QR code
            MultiFormatWriter qrCodeWriter = new MultiFormatWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(qrData, BarcodeFormat.QR_CODE, 400, 400, hintMap);

            BufferedImage qrImage = new BufferedImage(400, 400, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < 400; x++) {
                for (int y = 0; y < 400; y++) {
                    qrImage.setRGB(x, y, bitMatrix.get(x, y) ? 0x000000 : 0xFFFFFF);
                }
            }
            File outputFile = new File("src/main/resources/Images/" + bookID, "QR.png");
            outputFile.getParentFile().mkdirs(); // Tạo thư mục nếu chưa tồn tại
            ImageIO.write(qrImage, "PNG", outputFile);

        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
