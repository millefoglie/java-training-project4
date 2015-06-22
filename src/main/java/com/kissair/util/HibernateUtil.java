package com.kissair.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * The HibernateUtil class provided on the Hibernate documentation page.
 */
public class HibernateUtil {

    /** The Constant sessionFactory. */
    private static final SessionFactory SESSION_FACTORY = buildSessionFactory();
    
    /** The Constant LOGGER. */
    private static final Logger LOGGER = LogManager.getLogger();
    
    /**
     * Instantiates a new hibernate util.
     */
    private HibernateUtil() {}

    /**
     * Builds the session factory.
     *
     * @return the session factory
     */
    private static SessionFactory buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            Configuration configuration = new Configuration();
            
            configuration.configure("hibernate.cfg.xml");
          
            ServiceRegistry serviceRegistry =
        	    new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            return configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable e) {
            // Make sure you log the exception, as it might be swallowed
            LOGGER.error("Initial SessionFactory creation failed." + e);
            throw new ExceptionInInitializerError(e);
        }
    }

    /**
     * Gets the session factory.
     *
     * @return the session factory
     */
    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }
}
