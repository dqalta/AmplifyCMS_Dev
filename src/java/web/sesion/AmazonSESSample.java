package web.sesion;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.imageio.ImageIO;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.PreencodedMimeBodyPart;
import util.UtilSecurity;
import util.Imagen;

public class AmazonSESSample {

    public static void sendMail() throws MessagingException, UnsupportedEncodingException {
        String FROM = "yeisonfallas@gmail.com";
        String FROMNAME = "Masonry Desk";

        // Replace recipient@example.com with a "To" address. If your account 
        // is still in the sandbox, this address must be verified.
        String TO = "yeisonfallas@gmail.com";

        // Replace smtp_username with your Amazon SES SMTP user name.
        String SMTP_USERNAME = "AKIA5IWSWKB4PUTALS66";

        // Replace smtp_password with your Amazon SES SMTP password.
        String SMTP_PASSWORD = "BM8bBcvVe7gnt60WvejtducQ6lPlI5uCuaaXTXDTn8OR";

        // The name of the Configuration Set to use for this message.
        // If you comment out or remove this variable, you will also need to
        // comment out or remove the header below.
        String CONFIGSET = "ConfigSet";

        // Amazon SES SMTP host name. This example uses the EE.UU. Oeste (Oregón) region.
        // See https://docs.aws.amazon.com/ses/latest/DeveloperGuide/regions.html#region-endpoints
        // for more information.
        //String HOST = "email-smtp.us-west-2.amazonaws.com";
        String HOST = "email-smtp.us-east-1.amazonaws.com";
        // The port you will connect to on the Amazon SES SMTP endpoint. 
        int PORT = 587;

        String SUBJECT = "Amazon SES test (SMTP interface accessed using Java)";

        String base64 = "";
        String password = UtilSecurity.randomPassword(3, 2, 1, 2);
        if (Imagen.generar(password)) {
            try {
                System.out.println("Image");
                BufferedImage img = ImageIO.read(new File(System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") + "password" + ".png"));
                //PRUEBAS BufferedImage img = ImageIO.read(new File("C:\\Users\\CR108002\\AppData\\Local\\Temp\\" + correo + ".png"));
                base64 = "data:image/png;base64,";
                base64 = base64 + Imagen.encodeToString(img, "png");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        // Create a Properties object to contain connection configuration information.
        Properties props = System.getProperties();

        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.port", PORT);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");

        // Create a Session object to represent a mail session with the specified properties. 
        Session session = Session.getDefaultInstance(props);

        // Create a message with the specified information. 
        MimeMessage msg = new MimeMessage(session);

        msg.setFrom(new InternetAddress(FROM, FROMNAME));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(TO));
        msg.setSubject(SUBJECT);

        MimeMultipart multipart = new MimeMultipart("related");

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        String BODY = "<h4>" + password + "</h4>"
                + "<img src=\"http://3.15.28.209:8080/MasonryCMS/masonryAdmin/queries/ajax/image-password.mdk?ps=" + password + "\">";
        messageBodyPart.setContent(BODY, "text/html");

        multipart.addBodyPart(messageBodyPart);

        /*messageBodyPart = new MimeBodyPart();
        DataSource fds = new FileDataSource(
                System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") + "password" + ".png");

        messageBodyPart.setDataHandler(new DataHandler(fds));
        messageBodyPart.setHeader("Content-ID", "<image>");

        multipart.addBodyPart(messageBodyPart);*/
        msg.setContent(multipart);

        // Add a configuration set header. Comment or delete the 
        // next line if you are not using a configuration set
        msg.setHeader(
                "X-SES-CONFIGURATION-SET", CONFIGSET);

        // Create a transport.
        Transport transport = session.getTransport();

        // Send the message.
        try {
            System.out.println("Sending...");

            // Connect to Amazon SES using the SMTP username and password you specified above.
            transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);

            // Send the email.
            transport.sendMessage(msg, msg.getAllRecipients());
            System.out.println("Email sent!");
        } catch (Exception ex) {
            System.out.println("The email was not sent.");
            System.out.println("Error message: " + ex.getMessage());
        } finally {
            // Close and terminate the connection.
            transport.close();
        }
    }
}
