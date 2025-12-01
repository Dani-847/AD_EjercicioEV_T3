package org.drk.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Proporciona una instancia singleton de SessionFactory para la gestión de sesiones de Hibernate.
 * Configura la conexión a la base de datos utilizando las variables de entorno DB_USER y DB_PASSWORD.
 * Utiliza el patrón de diseño Singleton para asegurar que solo exista una instancia de SessionFactory.
 */

public class DataProvider {

    private static SessionFactory sessionFactory =null;

    private DataProvider() {}

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            var configuration = new Configuration().configure();
            configuration.setProperty("hibernate.connection.username",System.getenv("DB_USER"));
            configuration.setProperty("hibernate.connection.password",System.getenv("DB_PASSWORD"));
            sessionFactory = configuration.buildSessionFactory();
        }
        return sessionFactory;
    }
}