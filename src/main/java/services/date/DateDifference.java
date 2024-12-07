package services.date;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateDifference {
    public static long calculateDateDifference(String date1, String date2) {
        // Định dạng ngày tháng theo chuẩn yyyy-MM-dd
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Chuyển đổi chuỗi ngày thành đối tượng LocalDate
        LocalDate parsedDate1 = LocalDate.parse(date1, formatter);
        LocalDate parsedDate2 = LocalDate.parse(date2, formatter);

        // Tính hiệu số ngày giữa hai ngày
        return ChronoUnit.DAYS.between(parsedDate1, parsedDate2);
    }
}
