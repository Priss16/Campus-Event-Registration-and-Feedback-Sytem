package campus.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Event name is required")
    @Column(name = "event_name", nullable = false)
    private String eventName;

    @Pattern(regexp = "\\d{2}-\\d{2}-\\d{4}", message = "Date must be in dd-mm-yyyy format")
    @Column(name = "date", nullable = false)
    private String date;

    @Column(name = "venue")
    private String venue;

    @Column(name = "timing")
    private String timing;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "organizer")
    private String organizer;

    @Column(name = "event_category")
    private String eventCategory;

    public Event() {}

    public Event(String eventName, String date) {
        this.eventName = eventName;
        this.date = date;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEventName() { return eventName; }
    public void setEventName(String eventName) { this.eventName = eventName; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getVenue() { return venue; }
    public void setVenue(String venue) { this.venue = venue; }

    public String getTiming() { return timing; }
    public void setTiming(String timing) { this.timing = timing; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getOrganizer() { return organizer; }
    public void setOrganizer(String organizer) { this.organizer = organizer; }

    public String getEventCategory() { return eventCategory; }
    public void setEventCategory(String eventCategory) { this.eventCategory = eventCategory; }
}
