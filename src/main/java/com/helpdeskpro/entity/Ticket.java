package com.helpdeskpro.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tickets")
public class Ticket {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 150)
	private String title;

	@Column(nullable = false, length = 1000)
	private String description;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private TicketStatus status = TicketStatus.OPEN;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private TicketPriority priority = TicketPriority.MEDIUM;

	@Column(nullable = false)
	private LocalDateTime createdAt = LocalDateTime.now();

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private User reportedBy;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "technician_id")
	private Technician assignedTechnician;

	public Ticket() {
	}

	public Ticket(String title, String description, TicketPriority priority, User reportedBy) {
		this.title = title;
		this.description = description;
		this.priority = priority;
		this.reportedBy = reportedBy;
		this.status = TicketStatus.OPEN;
		this.createdAt = LocalDateTime.now();
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public TicketStatus getStatus() {
		return status;
	}

	public void setStatus(TicketStatus status) {
		this.status = status;
	}

	public TicketPriority getPriority() {
		return priority;
	}

	public void setPriority(TicketPriority priority) {
		this.priority = priority;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public User getReportedBy() {
		return reportedBy;
	}

	public void setReportedBy(User reportedBy) {
		this.reportedBy = reportedBy;
	}

	public Technician getAssignedTechnician() {
		return assignedTechnician;
	}

	public void setAssignedTechnician(Technician assignedTechnician) {
		this.assignedTechnician = assignedTechnician;
	}

	@Override
	public String toString() {
		return "Ticket{" + "id=" + id + ", title='" + title + '\'' + ", description='" + description + '\''
				+ ", status=" + status + ", priority=" + priority + ", createdAt=" + createdAt + ", reportedById="
				+ (reportedBy != null ? reportedBy.getId() : null) + ", assignedTechnicianId="
				+ (assignedTechnician != null ? assignedTechnician.getId() : null) + '}';

	}
}