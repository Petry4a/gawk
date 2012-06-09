package com.joka.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author jonas
 */
@Entity
public class Item implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    private String title;

    private String link;

    private String comments;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date publishedDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (publishedDate != null ? !publishedDate.equals(item.publishedDate) : item.publishedDate != null)
            return false;
        if (title != null ? !title.equals(item.title) : item.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (publishedDate != null ? publishedDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return getTitle();
    }
}
