package com.joka.domain;

import com.google.appengine.api.datastore.Key;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author jonas
 */

@Entity
public class Registration implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Key id;

    private String email;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date registrationDate;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date confirmedData;

    private String confirmationHash;

    public Key getId() {
        return id;
    }

    public void setId(Key id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Date getConfirmedData() {
        return confirmedData;
    }

    public void setConfirmedData(Date confirmedData) {
        this.confirmedData = confirmedData;
    }

    public String getConfirmationHash() {
        return confirmationHash;
    }

    public void setConfirmationHash(String confirmationHash) {
        this.confirmationHash = confirmationHash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Registration that = (Registration) o;

        if (email != null ? !email.equals(that.email) : that.email != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return email != null ? email.hashCode() : 0;
    }

    @Override
    public String toString() {
        return getEmail();
    }
}
