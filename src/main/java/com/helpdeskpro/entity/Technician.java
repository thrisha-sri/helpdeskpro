package com.helpdeskpro.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "technicians")
public class Technician {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 100)
	private String name;

	@Column(nullable = false, unique = true, length = 150)
	private String email;

	@Column(length = 100)
	private String specialization;

	@Column(nullable = false)
	private boolean available = true;

	public Technician() {
	}

	public Technician(String name, String email, String specialization) {
		this.name = name;
		this.email = email;
		this.specialization = specialization;
		this.available = true;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	@Override
	public String toString() {
		String displayedSpecialization = (specialization == null || specialization.isBlank()) ? "Not specified"
				: specialization;

		return "Technician{" + "id=" + id + ", name='" + name + '\'' + ", email='" + email + '\'' + ", specialization='"
				+ displayedSpecialization + '\'' + ", available=" + available + '}';
	}

}
