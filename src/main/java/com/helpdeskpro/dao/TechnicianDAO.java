package com.helpdeskpro.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.helpdeskpro.config.HibernateUtil;
import com.helpdeskpro.entity.Technician;

public class TechnicianDAO {
	// CREATE:Save a technician

	public void save(Technician technician) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			Transaction transaction = session.beginTransaction();

			try {
				session.persist(technician);
				transaction.commit();

			} catch (Exception e) {
				if (transaction.isActive()) {
					transaction.rollback();
				}
				throw e;
			}
		}
	}

	// READ: Find a technician by ID
	public Technician findById(Long id) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			return session.find(Technician.class, id);
		}
	}

	// READ: Find all technicians
	public List<Technician> findAll() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			return session.createQuery("FROM Technician", Technician.class).getResultList();
		}
	}

	// UPDATE: Change specialization and availability
	public boolean update(Long id, String specialization, boolean available) {

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			Transaction transaction = session.beginTransaction();

			try {
				Technician technician = session.find(Technician.class, id);

				if (technician == null) {
					transaction.rollback();
					return false;
				}

				technician.setSpecialization(specialization);
				technician.setAvailable(available);

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

	// DELETE: Remove a technician by ID
	public boolean deleteById(Long id) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			Transaction transaction = session.beginTransaction();

			try {
				Technician technician = session.find(Technician.class, id);

				if (technician == null) {
					transaction.rollback();
					return false;
				}

				session.remove(technician);
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

	public Technician findByEmail(String email) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			return session.createQuery("FROM Technician WHERE email = :email", Technician.class)
					.setParameter("email", email).uniqueResult();
		}
	}

}
