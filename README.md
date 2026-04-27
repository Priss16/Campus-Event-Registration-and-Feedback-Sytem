# 🎓 Campus Event Management System

A full-stack Spring Boot web application for managing campus events,
student registrations, feedback, and certificates — with SQLite database.

---

## 🛠 Tech Stack

| Layer      | Technology                        |
|------------|-----------------------------------|
| Backend    | Java 17 + Spring Boot 3.2         |
| Database   | SQLite (via Hibernate JPA)        |
| Frontend   | Thymeleaf + HTML5 + CSS3          |
| Build Tool | Maven                             |

---

## 📁 Project Structure

```
campus-event-mgmt/
├── pom.xml
└── src/main/
    ├── java/campus/
    │   ├── CampusEventApplication.java     ← Main entry point
    │   ├── controller/
    │   │   ├── MainController.java         ← All web routes
    │   │   └── GlobalExceptionHandler.java ← Error handling
    │   ├── model/
    │   │   ├── Student.java
    │   │   ├── Event.java
    │   │   ├── Registration.java
    │   │   └── Feedback.java
    │   ├── repository/
    │   │   ├── StudentRepository.java
    │   │   ├── EventRepository.java
    │   │   ├── RegistrationRepository.java
    │   │   └── FeedbackRepository.java
    │   ├── service/
    │   │   ├── StudentService.java
    │   │   ├── EventService.java
    │   │   ├── RegistrationService.java
    │   │   └── FeedbackService.java
    │   └── exception/
    │       ├── InvalidDateFormatException.java
    │       ├── InvalidPhoneNumberException.java
    │       └── InvalidRatingException.java
    └── resources/
        ├── application.properties          ← SQLite config
        ├── static/css/style.css
        └── templates/
            ├── index.html
            ├── admin-login.html
            ├── admin-dashboard.html
            ├── add-student.html
            ├── add-event.html
            ├── student-login.html
            ├── student-dashboard.html
            ├── feedback.html
            ├── certificate.html
            └── error.html
```

---

## 🚀 How to Run

### Prerequisites
- Java 17+
- Maven 3.6+

### Steps

```bash
# 1. Navigate to project folder
cd campus-event-mgmt

# 2. Build the project
mvn clean install

# 3. Run the application
mvn spring-boot:run
```

Then open your browser and go to: **http://localhost:7070**

The SQLite database file `campus_events.db` will be created automatically in the project root.

---

## 🔐 Login Credentials

### Admin
- **Username:** `admin`
- **Password:** `admin123`

### Student
- Use credentials created by admin (Reg No + Password)

---

## ✅ Features

### Admin
- Login with credentials
- Add / Delete students (with phone validation)
- Add / Delete events (with date format validation dd-mm-yyyy)
- View all registrations and feedbacks with stats dashboard

### Student
- Login with Reg No + Password
- View available events
- Register for events
- Submit feedback with 1–5 star rating
- View and print participation certificates

---

## 📌 Validation Rules (from original Java code)
| Field       | Rule                                    |
|-------------|------------------------------------------|
| Phone       | Exactly 10 digits                        |
| Date        | Format: `dd-mm-yyyy`                     |
| Rating      | Integer between 1 and 5                  |


H2 Console: http://localhost:7070/h2-console
JDBC URL: jdbc:h2:file:./data/campuseventsdb
User: sa
Password: password
