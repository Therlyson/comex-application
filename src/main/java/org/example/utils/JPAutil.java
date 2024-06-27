package org.example.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAutil {
    private static final EntityManagerFactory factory = Persistence.
            createEntityManagerFactory("comex");

    public static EntityManager getEntityManager(){
        return factory.createEntityManager();
    }
}
