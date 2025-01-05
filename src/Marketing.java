import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class Marketing {
    public static void main(String[] args) {
        String senderEmail = "your_email@gmail.com";
        final String uname = "your_email@gmail.com";
        final String pwd = "your_app_password";

        Properties propvls = new Properties();
        propvls.put("mail.smtp.auth", "true");
        propvls.put("mail.smtp.ssl.enable", "true");
        propvls.put("mail.smtp.host", "smtp.gmail.com");
        propvls.put("mail.smtp.port", "465");

        Session session = Session.getInstance(propvls, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(uname, pwd);
            }
        });

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/contactinfo", "root", "admin")) {
            java.sql.Statement stmt = conn.createStatement();
            String sql = "SELECT name, email FROM contacts";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                String emailTemplate = "Hello " + name + ",\n\nWe are launching a new outlet @ New Road. Visit us for discounts up to 80%.\n\nThank you.\nABC Outlet";

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(senderEmail));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
                message.setSubject("New Outlet Launch!");
                message.setText(emailTemplate);
                Transport.send(message);

                System.out.println("Email sent successfully to " + email);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}