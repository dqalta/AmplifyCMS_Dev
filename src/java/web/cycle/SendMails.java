/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.cycle;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;
import javax.imageio.ImageIO;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.struts2.interceptor.SessionAware;
import org.hibernate.Session;
import org.hibernate.Transaction;
import static org.hibernate.criterion.Expression.sql;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import sql.masonryAdmin.admin.AdminSQL;
import sql.masonryAdmin.admin.DtoEmail;
import sql.masonryAdmin.admin.DtoEmailConfiguration;
import util.Imagen;
import util.UtilSecurity;
import web.sesion.ORMUtil;

/**
 *
 * @author CR108002
 */
public class SendMails implements Job, SessionAware {

    Session mdk;
    Map session;

    public SendMails() {
        mdk = ORMUtil.getSesionCMS().openSession();
    }

    @Override
    public void setSession(Map session) {
        this.session = session;
    }

    public Map getSession() {
        return session;
    }

    public void execute(JobExecutionContext context)
            throws JobExecutionException {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                ArrayList<DtoEmail> arr = new ArrayList<>();
                arr = AdminSQL.getPendingEmails(mdk);
                for (int i = 0; i < arr.size(); i++) {
                    DtoEmail co = arr.get(i);
                    String resultSend = sendEmail(co);

                    Transaction tn = null;
                    try {
                        tn = mdk.beginTransaction();
                        AdminSQL.updateEmail(mdk, co.getId(), resultSend, resultSend.contains("MDK_Error") ? "PENDING" : "SENT");
                        tn.commit();
                    } catch (Exception x) {
                        if (tn != null) {
                            tn.rollback();
                        }
                    }
                }

                mdk.close();
            }
        }
        );
        t.start();
    }

    public String sendEmail(DtoEmail ma) {
        String message = "SENT";
        try {

            DtoEmailConfiguration conf = AdminSQL.getEmailConfiguration(mdk);

            Properties props = System.getProperties();
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.port", conf.getHostPort());
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.auth", "true");

            // Create a Session object to represent a mail session with the specified properties. 
            javax.mail.Session session = javax.mail.Session.getDefaultInstance(props);

            // Create a message with the specified information. 
            MimeMessage msg = new MimeMessage(session);

            msg.setFrom(new InternetAddress(conf.getEmail(), conf.getName()));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(ma.getRecipients()));
            msg.setSubject(ma.getSubject());

            MimeMultipart multipart = new MimeMultipart("related");

            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(ma.getBody(), "text/html");

            multipart.addBodyPart(messageBodyPart);

            msg.setContent(multipart);

            msg.setHeader("X-SES-CONFIGURATION-SET", conf.getConfigSet());

            Transport transport = session.getTransport();
            try {

                // Connect to Amazon SES using the SMTP username and password you specified above.
                transport.connect(conf.getHost(), conf.getUser(), conf.getPassword());

                // Send the email.
                transport.sendMessage(msg, msg.getAllRecipients());
                message = "SENT";
            } catch (Exception ex) {
                message = "MDK_Error: " + ex.getMessage();
            } finally {
                transport.close();
            }

        } catch (Exception ex) {
            message = "MDK_Error: " + ex.getMessage();
            //Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error de correo", x);
        } finally {
            return message;
        }
    }
}
