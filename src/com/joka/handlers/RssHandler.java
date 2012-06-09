package com.joka.handlers;

import com.joka.domain.Item;
import com.joka.utils.DateUtil;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author jonas
 */
public class RssHandler extends DefaultHandler {

    private static final String ITEM = "item";
    private static final String TITLE = "title";
    private static final String LINK = "link";
    private static final String COMMENTS = "comments";
    private static final String PUB_DATE = "pubDate";

    private List<Item> items;
    private Item currentItem;

    private StringBuilder sb;

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();

        items = new ArrayList<Item>();
        sb = new StringBuilder();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);

        if(qName.equalsIgnoreCase(ITEM)) {
            this.currentItem = new Item();
            items.add(currentItem);
        }
    }


    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);

        if(this.currentItem != null) {

            //Parse tags
            if(qName.equalsIgnoreCase(TITLE)) {
                currentItem.setTitle(sb.toString().trim());
            }
            else if(qName.equalsIgnoreCase(LINK)) {
                currentItem.setLink(sb.toString().trim());
            }
            else if(qName.equalsIgnoreCase(COMMENTS)) {
                currentItem.setComments(sb.toString().trim());
            }
            else if(qName.equalsIgnoreCase(PUB_DATE)) {
                Date publishDate = DateUtil.parseRssDateString(sb.toString().trim());
                currentItem.setPublishedDate(publishDate);
            }
        }

        //Reset the string buffer
        sb.setLength(0);
    }

    @Override
    public void characters(char[] chars, int start, int length) throws SAXException {
        super.characters(chars, start, length);

        sb.append(chars, start, length);
    }

    public List<Item> getItems() {
        return items;
    }
}
