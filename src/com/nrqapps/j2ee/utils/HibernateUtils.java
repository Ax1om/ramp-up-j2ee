package com.nrqapps.j2ee.utils;

import com.nrqapps.j2ee.models.Employee;
import com.nrqapps.j2ee.models.MaritalStatus;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * Created by Mauricio Enriquez on 8/17/17.
 * NrqApps © 2017
 */
public class HibernateUtils {
    private static SessionFactory sessionFactory;

    private static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration().configure("com/nrqapps/j2ee/config/hibernate.cfg.xml");
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(
                    configuration.getProperties()
                ).build();

            configuration
                .addAnnotatedClass(Employee.class)
                .addAnnotatedClass(MaritalStatus.class);
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }

        return sessionFactory;
    }

    public static Session getSession() {
        return getSessionFactory().openSession();
    }
}
