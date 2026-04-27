package campus.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "registrations")
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Event name is required")
    @Column(name = "event_name", nullable = false)
    private String eventName;

    @NotBlank(message = "Registration number is required")
    @Column(name = "reg_no", nullable = false)
    private String regNo;

    @Pattern(regexp = "\\d{2}-\\d{2}-\\d{4}", message = "Date must be in dd-mm-yyyy format")
    @Column(name = "date", nullable = false)
    private String date;

    public Registration() {}

    public Registration(String eventName, String regNo, String date) {
        this.eventName = eventName;
        this.regNo = regNo;
        this.date = date;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEventName() { return eventName; }
    public void setEventName(String eventName) { this.eventName = eventName; }

    public String getRegNo() { return regNo; }
    public void setRegNo(String regNo) { this.regNo = regNo; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
}
