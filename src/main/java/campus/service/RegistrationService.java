package campus.service;

import campus.exception.InvalidDateFormatException;
import campus.model.Registration;
import campus.repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistrationService {

    @Autowired
    private RegistrationRepository registrationRepository;

    public List<Registration> getAllRegistrations() {
        return registrationRepository.findAll();
    }

    public List<Registration> getRegistrationsByStudent(String regNo) {
        return registrationRepository.findByRegNo(regNo);
    }

    public List<Registration> getRegistrationsByEvent(String eventName) {
        return registrationRepository.findByEventName(eventName);
    }

    public Registration registerStudent(Registration registration) {
        if (!registration.getDate().matches("\\d{2}-\\d{2}-\\d{4}")) {
            throw new InvalidDateFormatException("Date format must be dd-mm-yyyy");
        }
        return registrationRepository.save(registration);
    }
}
