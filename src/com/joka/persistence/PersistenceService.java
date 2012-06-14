package com.joka.persistence;

import com.joka.domain.Item;
import com.joka.domain.Registration;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author jonas
 */
public class PersistenceService {

    private static final Logger logger = Logger.getLogger(PersistenceService.class.getName());

    private static final String QUERY_GET_ALL_ITEMS = "select from Item";

    private static final String QUERY_GET_ALL_CONFIRMED_REGISTRATIONS = "select from Registration";
    private static final String QUERY_GET_REGISTRATION_BY_EMAIL = "select from Registration where email = :email";

    private static EntityManagerFactory entityManagerFactory;
    private static PersistenceService instance;

    private static void init() {
        instance = new PersistenceService();
        entityManagerFactory = Persistence.createEntityManagerFactory("transactions-optional");
    }

    public static PersistenceService getInstance() {

        if(instance == null) {
            init();
        }

        return instance;
    }

    public Item saveItem(Item item) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.persist(item);

        logger.info(String.format("Save item \"%s\" to the database.", item));

        entityManager.close();

        return item;
    }

    public Registration saveRegistration(Registration registration) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.persist(registration);

        logger.info(String.format("Saved registration \"%s\" to the database.", registration));

        entityManager.close();

        return registration;
    }

    public List<Item> getItems() {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query query = entityManager.createQuery(QUERY_GET_ALL_ITEMS);

        ArrayList items = new ArrayList<Item>(query.getResultList());

        entityManager.close();

        return items;
    }

    public Registration getRegistrationByEmail(String email) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query query = entityManager.createQuery(QUERY_GET_REGISTRATION_BY_EMAIL);

        query.setParameter("email", email);

        Object result = null;
        try {
            result = query.getSingleResult();
        }
        catch (NoResultException e) {
            //Do nothing
        }

        entityManager.close();

        return result == null ? null : (Registration)result;
    }

    public List<Registration> getAllConfirmedRegistrations() {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query query = entityManager.createQuery(QUERY_GET_ALL_CONFIRMED_REGISTRATIONS);

        ArrayList<Registration> registrations = new ArrayList<Registration>(query.getResultList());

        entityManager.close();

        return registrations;
    }
}
