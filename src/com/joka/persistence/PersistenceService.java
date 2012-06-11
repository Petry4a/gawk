package com.joka.persistence;

import com.joka.domain.Item;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author jonas
 */
public class PersistenceService {

    private static final Logger logger = Logger.getLogger(PersistenceService.class.getName());

    private static final String QUERY_GET_ALL_ITEMS = "select from Item";

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

    public void saveItem(Item item) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.persist(item);

        logger.info(String.format("Save item \"%s\" to the database.", item));

        entityManager.close();
    }

    public List<Item> getItems() {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query query = entityManager.createQuery(QUERY_GET_ALL_ITEMS);

        return new ArrayList<Item>(query.getResultList());
    }



}
