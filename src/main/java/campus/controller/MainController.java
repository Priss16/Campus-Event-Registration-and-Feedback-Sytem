package campus.controller;

import campus.model.*;
import campus.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class MainController {

    @Autowired private StudentService studentService;
    @Autowired private EventService eventService;
    @Autowired private RegistrationService registrationService;
    @Autowired private FeedbackService feedbackService;

    // ─── HOME ────────────────────────────────────────────────────────────────
    @GetMapping("/")
    public String home() {
        return "index";
    }

    // ─── ADMIN LOGIN ─────────────────────────────────────────────────────────
    @GetMapping("/admin/login")
    public String adminLoginPage() {
        return "admin-login";
    }

    @PostMapping("/admin/login")
    public String adminLogin(@RequestParam String username,
                             @RequestParam String password,
                             HttpSession session,
                             RedirectAttributes ra) {
        if ("admin".equals(username) && "admin123".equals(password)) {
            session.setAttribute("adminLoggedIn", true);
            return "redirect:/admin/dashboard";
        }
        ra.addFlashAttribute("error", "Invalid credentials");
        return "redirect:/admin/login";
    }

    @GetMapping("/admin/logout")
    public String adminLogout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard(HttpSession session, Model model) {
        if (session.getAttribute("adminLoggedIn") == null) return "redirect:/admin/login";
        model.addAttribute("students", studentService.getAllStudents());
        model.addAttribute("events", eventService.getAllEvents());
        model.addAttribute("registrations", registrationService.getAllRegistrations());
        model.addAttribute("feedbacks", feedbackService.getAllFeedbacks());
        return "admin-dashboard";
    }

    // ─── STUDENT SIGNUP ──────────────────────────────────────────────────────
    @GetMapping("/student/signup")
    public String studentSignupPage(Model model) {
        model.addAttribute("student", new Student());
        return "student-signup";
    }

    @PostMapping("/student/signup")
    public String studentSignup(@ModelAttribute Student student,
                                RedirectAttributes ra) {
        try {
            if (studentService.getStudentByRegNo(student.getRegNo()).isPresent()) {
                ra.addFlashAttribute("error", "Registration number already exists. Please login.");
                return "redirect:/student/signup";
            }
            studentService.addStudent(student);
            ra.addFlashAttribute("success", "Account created successfully! Please login.");
            return "redirect:/student/login";
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
            return "redirect:/student/signup";
        }
    }

    // ─── STUDENT LOGIN ───────────────────────────────────────────────────────
    @GetMapping("/student/login")
    public String studentLoginPage() {
        return "student-login";
    }

    @PostMapping("/student/login")
    public String studentLogin(@RequestParam String regNo,
                               @RequestParam String password,
                               HttpSession session,
                               RedirectAttributes ra) {
        Student s = studentService.authenticate(regNo, password);
        if (s != null) {
            session.setAttribute("loggedStudent", s);
            return "redirect:/student/dashboard";
        }
        ra.addFlashAttribute("error", "Invalid credentials");
        return "redirect:/student/login";
    }

    @GetMapping("/student/logout")
    public String studentLogout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/student/dashboard")
    public String studentDashboard(HttpSession session, Model model) {
        Student s = (Student) session.getAttribute("loggedStudent");
        if (s == null) return "redirect:/student/login";
        model.addAttribute("student", s);
        model.addAttribute("events", eventService.getAllEvents());
        model.addAttribute("myRegistrations", registrationService.getRegistrationsByStudent(s.getRegNo()));
        return "student-dashboard";
    }

    // ─── STUDENT REGISTRATION (Admin) ────────────────────────────────────────
    @GetMapping("/admin/students/add")
    public String addStudentPage(HttpSession session, Model model) {
        if (session.getAttribute("adminLoggedIn") == null) return "redirect:/admin/login";
        model.addAttribute("student", new Student());
        return "add-student";
    }

    @PostMapping("/admin/students/add")
    public String addStudent(@ModelAttribute Student student,
                             HttpSession session,
                             RedirectAttributes ra) {
        if (session.getAttribute("adminLoggedIn") == null) return "redirect:/admin/login";
        try {
            studentService.addStudent(student);
            ra.addFlashAttribute("success", "Student added successfully!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/admin/students/delete/{regNo}")
    public String deleteStudent(@PathVariable String regNo,
                                HttpSession session,
                                RedirectAttributes ra) {
        if (session.getAttribute("adminLoggedIn") == null) return "redirect:/admin/login";
        studentService.deleteStudent(regNo);
        ra.addFlashAttribute("success", "Student deleted.");
        return "redirect:/admin/dashboard";
    }

    // ─── EVENTS (Admin) ───────────────────────────────────────────────────────
    @GetMapping("/admin/events/add")
    public String addEventPage(HttpSession session, Model model) {
        if (session.getAttribute("adminLoggedIn") == null) return "redirect:/admin/login";
        model.addAttribute("event", new Event());
        return "add-event";
    }

    @PostMapping("/admin/events/add")
    public String addEvent(@ModelAttribute Event event,
                           HttpSession session,
                           RedirectAttributes ra) {
        if (session.getAttribute("adminLoggedIn") == null) return "redirect:/admin/login";
        try {
            eventService.addEvent(event);
            ra.addFlashAttribute("success", "Event added successfully!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/admin/events/delete/{id}")
    public String deleteEvent(@PathVariable Long id,
                              HttpSession session,
                              RedirectAttributes ra) {
        if (session.getAttribute("adminLoggedIn") == null) return "redirect:/admin/login";
        eventService.deleteEvent(id);
        ra.addFlashAttribute("success", "Event deleted.");
        return "redirect:/admin/dashboard";
    }

    // ─── EVENT REGISTRATION (Student) ────────────────────────────────────────
    // Student clicks Register — auto-fills today's date as registration date
    @PostMapping("/student/register-event")
    public String registerForEvent(@RequestParam String eventName,
                                   HttpSession session,
                                   RedirectAttributes ra) {
        Student s = (Student) session.getAttribute("loggedStudent");
        if (s == null) return "redirect:/student/login";
        try {
            String today = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            Registration reg = new Registration(eventName, s.getRegNo(), today);
            registrationService.registerStudent(reg);
            ra.addFlashAttribute("success", "Registered for " + eventName + " successfully!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/student/dashboard";
    }

    // ─── FEEDBACK (Student) ───────────────────────────────────────────────────
    @GetMapping("/student/feedback")
    public String feedbackPage(HttpSession session, Model model) {
        Student s = (Student) session.getAttribute("loggedStudent");
        if (s == null) return "redirect:/student/login";
        model.addAttribute("events", eventService.getAllEvents());
        return "feedback";
    }

    @PostMapping("/student/feedback")
    public String submitFeedback(@RequestParam String eventName,
                                 @RequestParam int rating,
                                 @RequestParam String comments,
                                 HttpSession session,
                                 RedirectAttributes ra) {
        Student s = (Student) session.getAttribute("loggedStudent");
        if (s == null) return "redirect:/student/login";
        try {
            Feedback fb = new Feedback(eventName, rating, comments);
            feedbackService.submitFeedback(fb);
            ra.addFlashAttribute("success", "Feedback submitted!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/student/dashboard";
    }

    // ─── CERTIFICATE ──────────────────────────────────────────────────────────
    @GetMapping("/student/certificate/{eventName}")
    public String certificate(@PathVariable String eventName,
                              HttpSession session,
                              Model model) {
        Student s = (Student) session.getAttribute("loggedStudent");
        if (s == null) return "redirect:/student/login";
        model.addAttribute("studentName", s.getName());
        model.addAttribute("eventName", eventName);
        return "certificate";
    }
}
