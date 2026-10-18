package com.helpdeskpro.service;

import com.helpdeskpro.dao.TechnicianDAO;
import com.helpdeskpro.entity.Technician;

public class TechnicianService {
	private final TechnicianDAO technicianDAO = new TechnicianDAO();

	public Long createTechnician(String name, String email, String specialization) {

		if (name == null || name.isBlank()) {
			throw new IllegalArgumentException("Technician name is required.");
		}

		if (email == null || email.isBlank()) {
			throw new IllegalArgumentException("Technician email is required.");
		}

		if (!email.trim().matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
			throw new IllegalArgumentException("Please enter a valid email address.");
		}

		name = name.trim();
		email = email.trim().toLowerCase();

		if (technicianDAO.findByEmail(email) != null) {
			throw new IllegalArgumentException("A technician with this email already exists.");
		}

		Technician technician = new Technician(name, email, specialization == null ? null : specialization.trim());

		technicianDAO.save(technician);

		return technician.getId();
	}

	public boolean updateTechnicianAvailability(Long technicianId, boolean available) {

		if (technicianId == null || technicianId < 1) {
			throw new IllegalArgumentException("A valid technician ID is required.");
		}

		Technician technician = technicianDAO.findById(technicianId);

		if (technician == null) {
			return false;
		}

		return technicianDAO.update(technicianId, technician.getSpecialization(), available);
	}

}
