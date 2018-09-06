package golikov.alexander.backend.Notifiers;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSender {
    private static Properties mailServerProperties;
    private static Session getMailSession;
    private static MimeMessage mimeMessage;
    private InternetAddress[] recipients;
    private String msgSubject;
    private String msgBody;

    public EmailSender(String msgSubject, String msgBody, InternetAddress[] recipients) {
        this.msgSubject = msgSubject;
        this.msgBody = msgBody;
        this.recipients = recipients;
    }

    public void sendMessage ()  {


        mailServerProperties = System.getProperties();
        mailServerProperties.put("mail.smtp.port","587");
        mailServerProperties.put("mail.smtp.auth","true");
        mailServerProperties.put("mail.smtp.starttls.enable","true");


        try {
            getMailSession = Session.getDefaultInstance(mailServerProperties,null);
            mimeMessage = new MimeMessage(getMailSession);
            mimeMessage.addRecipients(Message.RecipientType.TO, recipients);
//            mimeMessage.addRecipient(Message.RecipientType.TO,new InternetAddress("alex2@medline.spb.ru"));
            mimeMessage.setSubject("***HeartBeat*** " + msgSubject);
            mimeMessage.setContent(msgBody ,"text/html");
            Transport transport = getMailSession.getTransport("smtp");
            transport.connect("smtp.gmail.com","cah4ec84","GOpr0ject");
            transport.sendMessage(mimeMessage,mimeMessage.getAllRecipients());
            transport.close();
        } catch (MessagingException e) {
            e.printStackTrace();
        }



    }
}
