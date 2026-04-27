package campus.controller;

import campus.exception.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidDateFormatException.class)
    public String handleInvalidDate(InvalidDateFormatException ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        return "error";
    }

    @ExceptionHandler(InvalidPhoneNumberException.class)
    public String handleInvalidPhone(InvalidPhoneNumberException ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        return "error";
    }

    @ExceptionHandler(InvalidRatingException.class)
    public String handleInvalidRating(InvalidRatingException ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneral(Exception ex, Model model) {
        model.addAttribute("error", "Something went wrong: " + ex.getMessage());
        return "error";
    }
}
