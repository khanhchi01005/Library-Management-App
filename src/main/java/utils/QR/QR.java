package utils.QR;

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
//    private static BookService bookService = new BookService(); // Khởi tạo BookService
//    private static String searchTitle;
//
//    public static void searchBook(int bookId) {
//        // Giả lập tìm kiếm tiêu đề sách
//        searchTitle = bookService.getBookTitle(bookId); // Dùng khi có BookService
//    }
//
//
//
//    public static void main(String[] args) {
//        QR qrGenerator = new QR();
//        qrGenerator.searchBook(37); // Gọi phương thức để khởi tạo searchTitle
//
//        if (searchTitle == null || searchTitle.isEmpty()) {
//            throw new IllegalArgumentException("searchTitle cannot be null or empty");
//        }
//
//        try {
//            String encodedTitle = URLEncoder.encode(searchTitle, "UTF-8");
//            String qrData = "https://google.com/search?q=" + encodedTitle;
//
//            Hashtable<EncodeHintType, Object> hintMap = new Hashtable<>();
//            hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
//
//            // Tạo QR code
//            MultiFormatWriter qrCodeWriter = new MultiFormatWriter();
//            BitMatrix bitMatrix = qrCodeWriter.encode(qrData, BarcodeFormat.QR_CODE, 400, 400, hintMap);
//
//            // Chuyển QR code thành hình ảnh
//            BufferedImage qrImage = new BufferedImage(400, 400, BufferedImage.TYPE_INT_RGB);
//            for (int x = 0; x < 400; x++) {
//                for (int y = 0; y < 400; y++) {
//                    qrImage.setRGB(x, y, bitMatrix.get(x, y) ? 0x000000 : 0xFFFFFF);
//                }
//            }
//
//            // Lưu hình ảnh QR code
//            String outputPath = "C://Users//admin//Downloads//QRCode_Book"  + ".png";
//            ImageIO.write(qrImage, "PNG", new File(outputPath));
//            System.out.println("QR code generated successfully for book title \""  + "\": " + outputPath);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
