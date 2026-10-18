package com.helpdeskpro.dao;

import java.util.List;

import org.hibernate.Session;

import com.helpdeskpro.config.HibernateUtil;
import com.helpdeskpro.entity.User;

public class UserDAO {

	// Find a user by ID
	public User findById(Long id) {

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			return session.find(User.class, id);
		}
	}

	// Retrieve all users
	public List<User> findAll() {

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			return session.createQuery("FROM User", User.class).getResultList();
		}
	}

	// Save a new user
	public void save(User user) {

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			org.hibernate.Transaction transaction = session.beginTransaction();

			try {
				session.persist(user);
				transaction.commit();

			} catch (Exception e) {
				if (transaction.isActive()) {
					transaction.rollback();
				}
				throw e;
			}
		}
	}

	// Update an existing user
	public boolean update(Long id, String department, String jobTitle) {

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			org.hibernate.Transaction transaction = session.beginTransaction();

			try {
				User user = session.find(User.class, id);

				if (user == null) {
					transaction.rollback();
					return false;
				}

				user.setDepartment(department);
				user.setJobTitle(jobTitle);

				transaction.commit();

				return true;

			} catch (Exception e) {
				if (transaction.isActive()) {
					transaction.rollback();
				}
				throw e;
			}
		}
	}

	// Delete a user by ID
	public boolean deleteById(Long id) {

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			org.hibernate.Transaction transaction = session.beginTransaction();

			try {
				User user = session.find(User.class, id);

				if (user == null) {
					transaction.rollback();
					return false;
				}

				session.remove(user);
				transaction.commit();

				return true;

			} catch (Exception e) {
				if (transaction.isActive()) {
					transaction.rollback();
				}
				throw e;
			}
		}
	}

	public User findByEmail(String email) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createQuery("FROM User WHERE email = :email", User.class).setParameter("email", email)
					.uniqueResult();
		}
	}

}