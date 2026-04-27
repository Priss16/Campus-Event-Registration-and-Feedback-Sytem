package campus.repository;

import campus.model.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    List<Registration> findByRegNo(String regNo);
    List<Registration> findByEventName(String eventName);
}
