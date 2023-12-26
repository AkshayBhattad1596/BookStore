# Online Bookstore API Documentation

### Prerequisites
- Java 11 or later
- PostgreSQL

### Setup Instructions
1. Clone the repository.
2. Open the project in your IDE or text editor.
3. Configure the project for Java 11+.
4. Set up the PostgreSQL database 
5. Run the application.

## PostgreSQL Database Setup

1. Update the database connection properties in `application.properties`:

   spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   spring.datasource.driver-class-name=org.postgresql.Driver
