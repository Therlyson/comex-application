package therlyson.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

public class JPAutil {


    private static final EntityManagerFactory factory = Persistence.
            createEntityManagerFactory("comex", getProperties());

    public static Map<String, String> getProperties() {
        Map<String, String> properties = new HashMap<>();
        properties.put("javax.persistence.jdbc.user", System.getenv("MYSQL_USER"));
        properties.put("javax.persistence.jdbc.password", System.getenv("MYSQL_PASSWORD"));
        return properties;
    }

    public static EntityManager getEntityManager(){
        return factory.createEntityManager();
    }
}
