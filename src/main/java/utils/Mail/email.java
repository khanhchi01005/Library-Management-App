package utils.Mail;

import io.github.cdimascio.dotenv.Dotenv;
import services.transaction.TransactionService;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class email {
    Dotenv dotenv = Dotenv.load();
    String gmail = dotenv.get("gmail");
    String password = dotenv.get("mailPassword");
    private TransactionService transactionService = new TransactionService();

    public void sendEmail(String email) {
        // Thiết lập các thuộc tính SMTP
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Tạo một session để gửi email
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(gmail, password);
            }
        });

        try {
            // Tạo email
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(gmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("Return Book Notification");
            message.setText("Please return your borrowed book as soon as possible!!!!!!");
            Transport.send(message);
            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}