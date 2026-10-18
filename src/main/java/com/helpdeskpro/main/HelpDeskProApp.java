package com.helpdeskpro.main;

import java.util.Scanner;

import com.helpdeskpro.config.HibernateUtil;
import com.helpdeskpro.dao.TechnicianDAO;
import com.helpdeskpro.dao.TicketDAO;
import com.helpdeskpro.dao.UserDAO;
import com.helpdeskpro.entity.Technician;
import com.helpdeskpro.entity.Ticket;
import com.helpdeskpro.entity.TicketPriority;
import com.helpdeskpro.entity.TicketStatus;
import com.helpdeskpro.entity.User;
import com.helpdeskpro.service.TechnicianService;
import com.helpdeskpro.service.TicketService;
import com.helpdeskpro.service.UserService;

public class HelpDeskProApp {

	private static int readInt(Scanner scanner, String prompt) {
		while (true) {
			System.out.print(prompt);
			String input = scanner.nextLine().trim();

			try {
				return Integer.parseInt(input);
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Please enter a number.");
			}
		}
	}

	private static <E extends Enum<E>> E readEnum(Scanner scanner, Class<E> enumClass, String prompt) {

		while (true) {
			System.out.print(prompt);
			String input = scanner.nextLine().trim().toUpperCase();

			try {
				return Enum.valueOf(enumClass, input);
			} catch (IllegalArgumentException e) {
				System.out.println(
						"Invalid choice. Valid options: " + java.util.Arrays.toString(enumClass.getEnumConstants()));
			}
		}
	}

	private static long readLong(Scanner scanner, String prompt) {
		while (true) {
			System.out.print(prompt);

			try {
				long value = Long.parseLong(scanner.nextLine().trim());

				if (value < 1) {
					System.out.println("Value must be greater than zero.");
					continue;
				}

				return value;

			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Please enter a valid ID number.");
			}
		}
	}

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		TicketService ticketService = new TicketService();
		TicketDAO ticketDAO = new TicketDAO();
		UserDAO userDAO = new UserDAO();
		TechnicianDAO technicianDAO = new TechnicianDAO();
		UserService userService = new UserService();
		TechnicianService technicianService = new TechnicianService();
		try {
			int choice;

			do {
				System.out.println("\n===== HELPDESKPRO =====");
				System.out.println("1. Create Ticket");
				System.out.println("2. View Ticket by ID");
				System.out.println("3. View All Tickets");
				System.out.println("4. Update Ticket Status");
				System.out.println("5. Filter Tickets by Status");
				System.out.println("6. Filter Tickets by Priority");
				System.out.println("7. View Tickets by Technician");
				System.out.println("8. Assign Technician");
				System.out.println("9. Delete Ticket");
				System.out.println("10. View Tickets by Page");
				System.out.println("11. View Dashboard");
				System.out.println("12. View Tickets with User and Technician Details");
				System.out.println("13. Create User");
				System.out.println("14. View All Users");
				System.out.println("15. Create Technician");
				System.out.println("16. View All Technicians");
				System.out.println("17. Technician Availability");
				System.out.println("0. Exit");

				choice = readInt(scanner, "Enter your choice: ");

				switch (choice) {

				case 1:
					System.out.print("Enter ticket title: ");
					String title = scanner.nextLine().trim();

					System.out.print("Enter description: ");
					String description = scanner.nextLine().trim();

					if (title.isEmpty() || description.isEmpty()) {
						System.out.println("Title and description are required.");
						break;
					}

					TicketPriority priority = readEnum(scanner, TicketPriority.class,
							"Enter priority (LOW, MEDIUM, HIGH, CRITICAL): ");

					long userId = readLong(scanner, "Enter reporting user ID: ");

					try {
						Long ticketId = ticketService.createTicket(title, description, priority, userId);

						System.out.println("Ticket created successfully. ID: " + ticketId);
					} catch (IllegalArgumentException e) {
						System.out.println("Could not create ticket: " + e.getMessage());
					} catch (Exception e) {
						System.out.println("An unexpected error occurred while creating the ticket.");
					}
					break;

				case 2:
					long id = readLong(scanner, "Enter ticket ID: ");

					Ticket ticket = ticketService.findTicket(id);

					if (ticket != null) {
						System.out.println(ticket);
					} else {
						System.out.println("Ticket not found.");
					}
					break;
				case 3:
					java.util.List<Ticket> tickets = ticketDAO.findAll();

					if (tickets.isEmpty()) {
						System.out.println("No tickets found.");
					} else {
						System.out.println("\n===== ALL TICKETS =====");

						for (Ticket t : tickets) {
							System.out.println(t);
						}
					}
					break;

				case 4:
					Long updateId = readLong(scanner, "Enter ticket ID: ");

					System.out.println("Available statuses: OPEN, IN_PROGRESS, RESOLVED, CLOSED");

					TicketStatus newStatus = readEnum(scanner, TicketStatus.class,
							"Enter status (OPEN, IN_PROGRESS, RESOLVED, CLOSED): ");
					boolean updated = ticketService.updateTicketStatus(updateId, newStatus);

					if (updated) {
						System.out.println("Ticket status updated successfully!");
					} else {
						System.out.println("Ticket not found.");
					}
					break;

				case 5:
					TicketStatus filterStatus = readEnum(scanner, TicketStatus.class,
							"Enter status to filter (OPEN, IN_PROGRESS, RESOLVED, CLOSED): ");

					java.util.List<Ticket> filteredTickets = ticketDAO.findByStatus(filterStatus);

					if (filteredTickets.isEmpty()) {
						System.out.println("No tickets found with status " + filterStatus);
					} else {
						System.out.println("\n===== FILTERED TICKETS =====");

						for (Ticket t : filteredTickets) {
							System.out.println(t);
						}
					}
					break;

				case 6:
					TicketPriority filterPriority = readEnum(scanner, TicketPriority.class,
							"Enter priority to filter (LOW, MEDIUM, HIGH, CRITICAL): ");

					java.util.List<Ticket> priorityTickets = ticketDAO.findByPriority(filterPriority);

					if (priorityTickets.isEmpty()) {
						System.out.println("No tickets found with priority " + filterPriority);
					} else {
						System.out.println("\n===== TICKETS BY PRIORITY =====");

						for (Ticket t : priorityTickets) {
							System.out.println(t);
						}
					}
					break;

				case 7:
					Long technicianId = readLong(scanner, "Enter technician ID: ");

					java.util.List<Ticket> technicianTickets = ticketDAO.findByTechnicianId(technicianId);

					if (technicianTickets.isEmpty()) {
						System.out.println("No tickets assigned to technician ID " + technicianId);
					} else {
						System.out.println("\n===== TECHNICIAN'S TICKETS =====");

						for (Ticket t : technicianTickets) {
							System.out.println(t);
						}
					}
					break;

				case 8:
					Long assignTicketId = readLong(scanner, "Enter ticket ID: ");

					Long assignTechnicianId = readLong(scanner, "Enter technician ID: ");
					boolean assigned = ticketDAO.assignTechnician(assignTicketId, assignTechnicianId);

					if (assigned) {
						System.out.println("Technician assigned successfully!");
					} else {
						System.out.println(
								"Assignment failed. Check the ticket ID, technician ID, and technician availability.");
					}
					break;

				case 9:
					Long deleteTicketId = readLong(scanner, "Enter ticket ID to delete: ");
					System.out.print("Are you sure you want to delete ticket " + deleteTicketId + "? (yes/no): ");

					String confirmation = scanner.nextLine().trim();

					if (confirmation.equalsIgnoreCase("yes")) {
						boolean deleted = ticketDAO.deleteById(deleteTicketId);

						if (deleted) {
							System.out.println("Ticket deleted successfully.");
						} else {
							System.out.println("Ticket not found.");
						}
					} else {
						System.out.println("Deletion cancelled.");
					}
					break;

				case 10:
					int pageNumber = readInt(scanner, "Enter page number: ");
					int pageSize = readInt(scanner, "Enter page size: ");
					if (pageNumber < 1 || pageSize < 1) {
						System.out.println("Page number and page size must be greater than 0.");
						break;
					}

					java.util.List<Ticket> pageTickets = ticketDAO.findTicketsByPage(pageNumber, pageSize);

					if (pageTickets.isEmpty()) {
						System.out.println("No tickets found on this page.");
					} else {
						System.out.println("\n===== TICKETS — PAGE " + pageNumber + " =====");

						for (Ticket t : pageTickets) {
							System.out.println(t);
						}
					}
					break;

				case 11:
					System.out.println("\n========== HELPDESKPRO DASHBOARD ==========");

					long totalTickets = ticketDAO.countAllTickets();
					System.out.println("Total Tickets: " + totalTickets);

					System.out.println("\n--- Tickets by Status ---");

					java.util.List<Object[]> statusCounts = ticketDAO.countTicketsByStatus();

					for (Object[] row : statusCounts) {
						System.out.println(row[0] + ": " + row[1]);
					}

					System.out.println("\n--- Tickets by Priority ---");

					java.util.List<Object[]> priorityCounts = ticketDAO.countTicketsByPriority();

					for (Object[] row : priorityCounts) {
						System.out.println(row[0] + ": " + row[1]);
					}

					System.out.println("===========================================");
					break;

				case 12:
					java.util.List<Object[]> detailedTickets = ticketDAO.findTicketsWithDetails();

					System.out.println("\n========== TICKET DETAILS ==========");

					if (detailedTickets.isEmpty()) {
						System.out.println("No tickets found.");
					} else {
						for (Object[] row : detailedTickets) {
							System.out.println("ID: " + row[0] + " | Title: " + row[1] + " | Status: " + row[2]
									+ " | Priority: " + row[3] + " | Reported By: " + row[4] + " | Technician: "
									+ (row[5] == null ? "Unassigned" : row[5]));
						}
					}

					break;

				case 13:
					System.out.print("Enter user name: ");
					String name = scanner.nextLine().trim();

					System.out.print("Enter user email: ");
					String email = scanner.nextLine().trim();

					System.out.print("Enter department: ");
					String department = scanner.nextLine().trim();

					System.out.print("Enter job title: ");
					String jobTitle = scanner.nextLine().trim();

					try {
						Long createdUserId = userService.createUser(name, email, department, jobTitle);

						System.out.println("User created successfully! ID: " + createdUserId);

					} catch (IllegalArgumentException e) {
						System.out.println("Could not create user: " + e.getMessage());

					} catch (Exception e) {
						System.out.println("Could not save user. Please check the input and database.");
					}
					break;

				case 14:
					java.util.List<User> users = userDAO.findAll();

					if (users.isEmpty()) {
						System.out.println("No users found.");
					} else {
						System.out.println("\n===== ALL USERS =====");

						for (User u : users) {
							System.out.println(u);
						}
					}
					break;

				case 15:
					System.out.print("Enter technician name: ");
					String technicianName = scanner.nextLine().trim();

					System.out.print("Enter technician email: ");
					String technicianEmail = scanner.nextLine().trim();

					System.out.print("Enter specialization (optional): ");
					String specialization = scanner.nextLine().trim();

					try {
						Long createdTechnicianId = technicianService.createTechnician(technicianName, technicianEmail,
								specialization);

						System.out.println("Technician created successfully! ID: " + createdTechnicianId);

					} catch (IllegalArgumentException e) {
						System.out.println("Could not create technician: " + e.getMessage());

					} catch (Exception e) {
						System.out.println("Could not save technician. Please check the input and database.");
					}
					break;

				case 16:
					java.util.List<Technician> technicians = technicianDAO.findAll();

					if (technicians.isEmpty()) {
						System.out.println("No technicians found.");
					} else {
						System.out.println("\n===== ALL TECHNICIANS =====");

						for (Technician tech : technicians) {
							System.out.println(tech);
						}
					}
					break;

				case 17:
					long availabilityTechnicianId = readLong(scanner, "Enter technician ID: ");

					System.out.println("1. Available");
					System.out.println("2. Unavailable");

					int availabilityChoice = readInt(scanner, "Choose availability: ");

					if (availabilityChoice != 1 && availabilityChoice != 2) {
						System.out.println("Invalid choice. Please select 1 or 2.");
						break;
					}

					boolean available = availabilityChoice == 1;

					try {
						boolean availabilityupdated = technicianService
								.updateTechnicianAvailability(availabilityTechnicianId, available);

						if (availabilityupdated) {
							System.out.println("Technician availability updated successfully!");
						} else {
							System.out.println("Technician not found with ID: " + availabilityTechnicianId);
						}

					} catch (Exception e) {
						System.out.println("Could not update technician availability: " + e.getMessage());
					}
					break;

				case 0:
					System.out.println("Exiting HelpDeskPro...");
					break;

				default:
					System.out.println("Invalid choice. Try again.");
				}

			} while (choice != 0);

		} catch (IllegalArgumentException e) {
			System.out.println("Invalid input: " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			scanner.close();
			HibernateUtil.shutdown();
		}
	}
}
