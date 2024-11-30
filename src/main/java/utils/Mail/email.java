package utils.Mail;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class email {
    public static void main(String[] args) {
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
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("duongdangnghi@gmail.com"));
            message.setSubject("binh duong oiiii");
            message.setText("muahahhahaahahah");

            // Gửi email
            Transport.send(message);
            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
