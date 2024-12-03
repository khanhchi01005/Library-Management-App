package utils.Mail;

import services.transaction.TransactionService;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class email {
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
                return new PasswordAuthentication("khanhchi01005@gmail.com", "gcjs firf rjmd nsjw");
            }
        });

        try {
            // Tạo email
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("khanhchi01005@gmail.com"));
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