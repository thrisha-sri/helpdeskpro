package com.helpdeskpro.config;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.helpdeskpro.entity.Technician;
import com.helpdeskpro.entity.Ticket;
import com.helpdeskpro.entity.User;

public class HibernateUtil {
	private static final SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {
		try {
			Configuration configuration = new Configuration().configure("hibernate.cfg.xml")
					.addAnnotatedClass(User.class).addAnnotatedClass(Technician.class).addAnnotatedClass(Ticket.class);

			configuration.setProperty("hibernate.hikari.username", System.getenv("HELPDESK_DB_USERNAME"));

			configuration.setProperty("hibernate.hikari.password", System.getenv("HELPDESK_DB_PASSWORD"));
			return configuration.buildSessionFactory();

		} catch (Throwable ex) {
			System.err.println("Failed to create SessionFactory: " + ex.getMessage());
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static void shutdown() {
		if (sessionFactory != null && !sessionFactory.isClosed()) {
			sessionFactory.close();
		}
	}
}