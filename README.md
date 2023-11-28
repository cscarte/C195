# C195
C195 - Software 2

# Scheduling System for a Fictional Company

**Introduction**

This project encompasses the development of a comprehensive scheduling system for a fictional company. Utilizing Java and JavaFX for the front and back end, and MySQL for the database, the system provides a user-friendly interface for managing customers, appointments, and generating reports.

**Key Features**

1. **User Authentication:**
    - Secure login form with username and password validation against the MySQL database.
    - Automatic detection and display of the user's time zone.
    - Dynamic language translation based on the user's computer language settings.
    - Real-time appointment notifications for upcoming appointments within 15 minutes.

2. **Customer Management:**
    - User-friendly interface for adding, modifying, and deleting customers from the database.
    - Maintaining customer data integrity through cascade deletion of associated appointments upon customer removal.

3. **Appointment Management:**
    - Seamless creation, modification, and deletion of appointments linked to specific customers.
    - Appointment time display in the user's time zone for enhanced user experience.
    - Appointment time validation against the company's business hours to ensure feasibility.
    - Appointment storage in UTC format for consistent database management.

4. **Reporting:**
    - Comprehensive reports generated at the user's request, providing valuable insights.
    - Total number of customer appointments by appointment type and month, aiding in business analysis.
    - Individual schedules for each contact within the organization for streamlined communication.
    - Customizable report displaying the number of customers in the database for data summarization.

**Technology Stack**

- **Front-End:** JavaFX, a graphical user interface (GUI) toolkit providing rich interactive elements.

- **Back-End:** Java, a versatile programming language for developing the application's core functionalities.

- **Database:** MySQL, a robust relational database management system (RDBMS) for data storage and retrieval.

**Installation**

1. **Prerequisites:**
    - Install Java Development Kit (JDK) 11 or higher
    - Install MySQL Community Server
    - Install JavaFX SDK

2. **Project Setup:**
    - Clone the project repository to your local machine
    - Configure MySQL database connection parameters
    - Set up project dependencies in your IDE

3. **Application Execution:**
    - Compile the Java code
    - Run the application using JavaFX
