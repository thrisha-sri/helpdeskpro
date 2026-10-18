
package com.helpdeskpro.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 100)
	private String name;

	@Column(nullable = false, unique = true, length = 150)
	private String email;

	@Column(length = 100)
	private String department;

	@Column(name = "job_title", length = 100)
	private String jobTitle;

	// Required by Hibernate
	public User() {
	}

	public User(String name, String email, String department, String jobTitle) {
		this.name = name;
		this.email = email;
		this.department = department;
		this.jobTitle = jobTitle;
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

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	@Override
	public String toString() {
		return "User{" + "id=" + id + ", name='" + name + '\'' + ", email='" + email + '\'' + ", department='"
				+ department + '\'' + ", jobTitle='" + jobTitle + '\'' + '}';
	}
}
