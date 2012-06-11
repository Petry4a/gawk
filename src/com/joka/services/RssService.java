package com.joka.services;

import com.joka.domain.Item;
import com.joka.handlers.RssHandler;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * @author jonas
 */
public class RssService {

    private static final String rssFeedUrlString = "http://feeds.feedburner.com/filipochfredrik/podcast";

    private static RssService instance = null;

    private RssService() {}

    /**
     * Method to access the singleton RssService
     * @return RssService
     */
    public static RssService getInstance() {

        if(instance == null) {
            instance = new RssService();
        }

        return instance;
    }

    /**
     * Creates a connection to the feed url.
     * @return an InputStream connected to the feed.
     * @throws IOException
     */
    public InputStream getFeed() throws IOException {

        URL url = new URL(rssFeedUrlString);

        URLConnection connection = url.openConnection();
        return connection.getInputStream();
    }

    public List<Item> getItems() throws IOException, SAXException, ParserConfigurationException {

        //Init
        RssHandler rssHandler = new RssHandler();
        InputStream inputStream = getFeed();

        //Parse
        SAXParserFactory.newInstance().newSAXParser().parse(inputStream, rssHandler);

        //Clean up
        inputStream.close();

        return rssHandler.getItems();
    }

}
