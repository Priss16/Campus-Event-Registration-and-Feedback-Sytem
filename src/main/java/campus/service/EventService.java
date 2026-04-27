package campus.service;

import campus.exception.InvalidDateFormatException;
import campus.model.Event;
import campus.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    public Event addEvent(Event event) {
        if (!event.getDate().matches("\\d{2}-\\d{2}-\\d{4}")) {
            throw new InvalidDateFormatException("Date format must be dd-mm-yyyy");
        }
        // Validate that the event date is in the future (not today or past)
        try {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate eventDate = LocalDate.parse(event.getDate(), fmt);
            LocalDate today = LocalDate.now();
            if (!eventDate.isAfter(today)) {
                throw new InvalidDateFormatException("Event date must be a future date (not today or past).");
            }
        } catch (DateTimeParseException e) {
            throw new InvalidDateFormatException("Invalid date. Please use dd-mm-yyyy format.");
        }
        return eventRepository.save(event);
    }

    public boolean deleteEvent(Long id) {
        if (eventRepository.existsById(id)) {
            eventRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
