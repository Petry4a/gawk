package com.joka.services;

import com.joka.domain.Item;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author jonas
 */
public class NotificationService {

    private static final Logger logger = Logger.getLogger(NotificationService.class.getName());

    private static NotificationService instance;

    private static final String ERROR_MESSAGE = "An error occurred.";

    private static final String EMAIL_SUBJECT_TEMPLATE = "%s new episodes of Filip & Fredriks podcas is available.";
    private static final String NEW_LINE_CHARS = "\r\n";

    private static final String EMAIL_FROM = "jonas.lmk@gmail.com";
    private static final String EMAIL_TO = EMAIL_FROM;

    private NotificationService() {}

    public static NotificationService getInstance() {

        if(instance == null) {
            instance = new NotificationService();
        }

        return instance;
    }

    public void sendNotifications(List<Item> items) {

        //TODO add functionality to send notification to multiple recipients.

        String messageSubject = String.format(EMAIL_SUBJECT_TEMPLATE, items.size());
        String messageBody = getMessageBody(items);

        Properties properties = new Properties();
        Session session = Session.getDefaultInstance(properties, null);

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL_FROM));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(EMAIL_TO));
            message.setSubject(messageSubject);
            message.setText(messageBody);

            Transport.send(message);
            logger.info(String.format("Sent message to recipient %s", EMAIL_TO));

        } catch (AddressException e) {
            logger.log(Level.SEVERE, ERROR_MESSAGE, e);
            e.printStackTrace();
        } catch (MessagingException e) {
            logger.log(Level.SEVERE, ERROR_MESSAGE, e);
            e.printStackTrace();
        }


    }

    private String getMessageBody(List<Item> items) {

        StringBuffer sb = new StringBuffer();

        sb.append(String.format(EMAIL_SUBJECT_TEMPLATE, items.size()));
        sb.append(NEW_LINE_CHARS);
        sb.append(NEW_LINE_CHARS);
        for(Item item : items) {

            sb.append(item.getTitle());
            sb.append(NEW_LINE_CHARS);
            sb.append(item.getLink());
            sb.append(NEW_LINE_CHARS);
            sb.append(NEW_LINE_CHARS);

        }

        return sb.toString();
    }


}
