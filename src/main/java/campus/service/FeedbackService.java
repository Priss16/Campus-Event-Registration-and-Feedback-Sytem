package campus.service;

import campus.exception.InvalidRatingException;
import campus.model.Feedback;
import campus.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    public List<Feedback> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }

    public List<Feedback> getFeedbackByEvent(String eventName) {
        return feedbackRepository.findByEventName(eventName);
    }

    public Feedback submitFeedback(Feedback feedback) {
        if (feedback.getRating() < 1 || feedback.getRating() > 5) {
            throw new InvalidRatingException("Rating must be between 1 and 5");
        }
        return feedbackRepository.save(feedback);
    }
}
