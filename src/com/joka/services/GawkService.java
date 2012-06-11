package com.joka.services;

import com.joka.domain.Item;
import com.joka.persistence.PersistenceService;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author jonas
 */
public class GawkService {

    private static final Logger logger = Logger.getLogger(GawkService.class.getName());

    private static final String ERROR_MESSAGE = "An error occurred.";

    private static GawkService instance;

    private GawkService() {}

    public static GawkService getInstance() {

        if(instance == null) {
            instance = new GawkService();
        }

        return instance;
    }

    public void performCheck() {

        try {

            //Load items from url
            List<Item> feedItems = RssService.getInstance().getItems();
            logger.info(String.format("Got %s episodes from feed.", feedItems.size()));

            //Load items from db
            List<Item> persistedItems = PersistenceService.getInstance().getItems();
            logger.info(String.format("Loaded %s episodes from db.", persistedItems.size()));

            //List for new episodes
            List<Item> newItems = new ArrayList<Item>();

            //Check feed
            for(Item feedItem : feedItems) {

                //If an episode is not in the persisted list add it to the newItems list
                if(!persistedItems.contains(feedItem)) {
                    newItems.add(feedItem);
                }
            }

            //If new episodes is found
            if(newItems.size() > 0) {

                logger.info(String.format("Found %s new episodes", newItems));

                //Send notifications
                NotificationService.getInstance().sendNotifications(newItems);

                //Save new episodes
                for(Item item : newItems) {
                    PersistenceService.getInstance().saveItem(item);
                }

            }
            else {
                logger.info("No new episodes found.");
            }

        } catch (IOException e) {
            logger.log(Level.SEVERE, ERROR_MESSAGE, e);
            e.printStackTrace();
        } catch (SAXException e) {
            logger.log(Level.SEVERE, ERROR_MESSAGE, e);
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            logger.log(Level.SEVERE, ERROR_MESSAGE, e);
            e.printStackTrace();
        }
    }
}
