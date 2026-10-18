package com.helpdeskpro.service;

import com.helpdeskpro.dao.TicketDAO;
import com.helpdeskpro.dao.UserDAO;
import com.helpdeskpro.entity.Ticket;
import com.helpdeskpro.entity.TicketPriority;
import com.helpdeskpro.entity.TicketStatus;
import com.helpdeskpro.entity.User;

public class TicketService {
	private final TicketDAO ticketDAO = new TicketDAO();
	private final UserDAO userDAO = new UserDAO();

	public Long createTicket(String title, String description, TicketPriority priority, Long userId) {

		// Validate the input
		if (title == null || title.isBlank()) {
			throw new IllegalArgumentException("Ticket title cannot be empty.");
		}

		if (description == null || description.isBlank()) {
			throw new IllegalArgumentException("Ticket description cannot be empty.");
		}

		if (priority == null) {
			throw new IllegalArgumentException("Ticket priority is required.");
		}

		if (userId == null || userId < 1) {
			throw new IllegalArgumentException("A valid user ID is required.");
		}

		// Check whether the reporting user exists
		User user = userDAO.findById(userId);

		if (user == null) {
			throw new IllegalArgumentException("User not found: " + userId);
		}

		// Create and save the ticket
		Ticket ticket = new Ticket(title.trim(), description.trim(), priority, user);

		ticketDAO.save(ticket);

		return ticket.getId();
	}

	public Ticket findTicket(Long ticketId) {
		if (ticketId == null || ticketId < 1) {
			throw new IllegalArgumentException("A valid ticket ID is required.");
		}

		return ticketDAO.findById(ticketId);
	}

	public boolean updateTicketStatus(Long ticketId, TicketStatus status) {

		if (ticketId == null || ticketId < 1) {
			throw new IllegalArgumentException("A valid ticket ID is required.");
		}

		if (status == null) {
			throw new IllegalArgumentException("Ticket status is required.");
		}

		Ticket ticket = ticketDAO.findById(ticketId);

		if (ticket == null) {
			return false;
		}

		return ticketDAO.updateStatusAndPriority(ticketId, status, ticket.getPriority());
	}
}
