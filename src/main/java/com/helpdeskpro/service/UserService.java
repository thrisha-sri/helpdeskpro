package com.helpdeskpro.service;

import com.helpdeskpro.dao.UserDAO;
import com.helpdeskpro.entity.User;

public class UserService {
	private final UserDAO userDAO = new UserDAO();

	public Long createUser(String name, String email, String department, String jobTitle) {

		if (name == null || name.isBlank()) {
			throw new IllegalArgumentException("Name is required.");
		}

		if (email == null || email.isBlank()) {
			throw new IllegalArgumentException("Email is required.");
		}

		if (!email.trim().matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
			throw new IllegalArgumentException("Please enter a valid email address.");
		}
		if (department == null || department.isBlank()) {
			throw new IllegalArgumentException("Department is required.");
		}

		if (jobTitle == null || jobTitle.isBlank()) {
			throw new IllegalArgumentException("Job title is required.");
		}
		name = name.trim();
		email = email.trim().toLowerCase();

		if (userDAO.findByEmail(email) != null) {
			throw new IllegalArgumentException("A user with this email already exists.");
		}

		User user = new User(name, email, department == null ? null : department.trim(),
				jobTitle == null ? null : jobTitle.trim());

		userDAO.save(user);

		return user.getId();
	}
}
