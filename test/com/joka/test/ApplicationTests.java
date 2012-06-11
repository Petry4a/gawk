package com.joka.test;

import com.joka.domain.Item;
import com.joka.services.RssService;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * @author jonas
 */
public class ApplicationTests {

    //private final LocalServiceTestHelper

    @Test
    public void TestGetFeed() throws IOException {

        BufferedInputStream inputStream = new BufferedInputStream(RssService.getInstance().getFeed());

        int oneByte;
        int byteCounter = 0;
        while((oneByte = inputStream.read()) != -1) {
            byteCounter++;
        }

        System.out.println(String.format("Success, read %s bytes!", byteCounter));
    }

    @Test
    public void TestGetItems() throws IOException, SAXException, ParserConfigurationException {

        List<Item> items = RssService.getInstance().getItems();
        assertTrue(items.size() > 0);

        for(Item item : items) {
            System.out.println(item);
        }

        System.out.println(String.format("Success, parsed %s items!", items.size()));
    }
}
