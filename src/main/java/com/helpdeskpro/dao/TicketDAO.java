package com.helpdeskpro.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.helpdeskpro.config.HibernateUtil;
import com.helpdeskpro.entity.Technician;
import com.helpdeskpro.entity.Ticket;
import com.helpdeskpro.entity.TicketPriority;
import com.helpdeskpro.entity.TicketStatus;

public class TicketDAO {

	public long countAllTickets() {
		try (org.hibernate.Session session = HibernateUtil.getSessionFactory().openSession()) {

			return session.createQuery("SELECT COUNT(t) FROM Ticket t", Long.class).getSingleResult();
		}
	}

	public java.util.List<Object[]> countTicketsByStatus() {
		try (org.hibernate.Session session = HibernateUtil.getSessionFactory().openSession()) {

			return session.createQuery("SELECT t.status, COUNT(t) FROM Ticket t GROUP BY t.status", Object[].class)
					.getResultList();
		}
	}

	public java.util.List<Object[]> countTicketsByPriority() {
		try (org.hibernate.Session session = HibernateUtil.getSessionFactory().openSession()) {

			return session.createQuery("SELECT t.priority, COUNT(t) FROM Ticket t GROUP BY t.priority", Object[].class)
					.getResultList();
		}
	}

	public java.util.List<Object[]> findTicketsWithDetails() {
		try (org.hibernate.Session session = HibernateUtil.getSessionFactory().openSession()) {

			return session.createQuery(
					"SELECT t.id, t.title, t.status, t.priority, " + "u.name, tech.name " + "FROM Ticket t "
							+ "JOIN t.reportedBy u " + "LEFT JOIN t.assignedTechnician tech " + "ORDER BY t.id",
					Object[].class).getResultList();
		}
	}

	// Save a ticket
	public void save(Ticket ticket) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			Transaction transaction = session.beginTransaction();

			try {
				session.persist(ticket);
				transaction.commit();

			} catch (Exception e) {
				if (transaction.isActive()) {
					transaction.rollback();
				}
				throw e;
			}
		}
	}

	// Find a ticket by ID
	public Ticket findById(Long id) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			return session.find(Ticket.class, id);
		}
	}

	// Find all tickets
	public List<Ticket> findAll() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			return session.createQuery("FROM Ticket", Ticket.class).getResultList();
		}
	}

	// Change ticket status and priority
	public boolean updateStatusAndPriority(Long id, TicketStatus status, TicketPriority priority) {

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			Transaction transaction = session.beginTransaction();

			try {
				Ticket ticket = session.find(Ticket.class, id);

				if (ticket == null) {
					transaction.rollback();
					return false;
				}

				ticket.setStatus(status);
				ticket.setPriority(priority);

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

	// Assign a technician to a ticket
	public boolean assignTechnician(Long ticketId, Long technicianId) {

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			Transaction transaction = session.beginTransaction();

			try {
				Ticket ticket = session.find(Ticket.class, ticketId);

				Technician technician = session.find(Technician.class, technicianId);

				if (ticket == null || technician == null) {
					transaction.rollback();
					return false;
				}
				if (ticket.getAssignedTechnician() != null) {
					transaction.rollback();
					return false;
				}
				if (!technician.isAvailable()) {
					transaction.rollback();
					return false;
				}

				ticket.setAssignedTechnician(technician);
				ticket.setStatus(TicketStatus.IN_PROGRESS);

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

	// Delete a ticket by ID
	public boolean deleteById(Long id) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			Transaction transaction = session.beginTransaction();

			try {
				Ticket ticket = session.find(Ticket.class, id);

				if (ticket == null) {
					transaction.rollback();
					return false;
				}

				session.remove(ticket);
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

	// Find tickets by status
	public List<Ticket> findByStatus(TicketStatus status) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			return session.createQuery("FROM Ticket WHERE status = :status", Ticket.class)
					.setParameter("status", status).getResultList();
		}
	}

	// Find tickets by priority
	public List<Ticket> findByPriority(TicketPriority priority) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			return session.createQuery("FROM Ticket WHERE priority = :priority", Ticket.class)
					.setParameter("priority", priority).getResultList();
		}
	}

	// Find tickets assigned to a technician
	public List<Ticket> findByTechnicianId(Long technicianId) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createQuery("FROM Ticket WHERE assignedTechnician.id = :technicianId", Ticket.class)
					.setParameter("technicianId", technicianId).getResultList();
		}
	}

	// Retrieve tickets page by page
	public List<Ticket> findTicketsByPage(int pageNumber, int pageSize) {
		if (pageNumber < 1 || pageSize < 1) {
			throw new IllegalArgumentException("Page number and page size must be greater than zero.");
		}

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createQuery("FROM Ticket ORDER BY id", Ticket.class)
					.setFirstResult((pageNumber - 1) * pageSize).setMaxResults(pageSize).getResultList();
		}
	}
}