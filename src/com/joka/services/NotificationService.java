package com.joka.services;

import com.joka.domain.Item;
import com.joka.domain.Registration;
import com.joka.persistence.PersistenceService;

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

    private static final String EMAIL_SUBJECT_TEMPLATE = "%s new episode(s) of Filip & Fredriks podcast is available.";
    private static final String NEW_LINE_CHARS = "\r\n";

    private static final String EMAIL_FROM = "jonas.lmk@gmail.com";

    private NotificationService() {}

    public static NotificationService getInstance() {

        if(instance == null) {
            instance = new NotificationService();
        }

        return instance;
    }

    public void sendNotifications(List<Item> items) {

        String messageSubject = String.format(EMAIL_SUBJECT_TEMPLATE, items.size());
        String messageBody = getMessageBody(items);

        List<Registration> registrations = PersistenceService.getInstance().getAllConfirmedRegistrations();

        for(Registration registration : registrations) {

            sendNotification(registration, messageSubject, messageBody);
        }

    }

    public void sendNotification(Registration registration, String messageSubject, String messageBody) {

        Properties properties = new Properties();
        Session session = Session.getDefaultInstance(properties, null);

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL_FROM));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(registration.getEmail()));
            message.setSubject(messageSubject);
            message.setText(messageBody);

            Transport.send(message);
            logger.info(String.format("Sent message to recipient %s", registration.getEmail()));

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
