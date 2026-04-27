package campus.service;

import campus.exception.InvalidPhoneNumberException;
import campus.model.Student;
import campus.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentByRegNo(String regNo) {
        return studentRepository.findById(regNo);
    }

    public Student addStudent(Student student) {
        if (!student.getPhone().matches("\\d{10}")) {
            throw new InvalidPhoneNumberException("Phone number must contain exactly 10 digits");
        }
        return studentRepository.save(student);
    }

    public boolean deleteStudent(String regNo) {
        if (studentRepository.existsById(regNo)) {
            studentRepository.deleteById(regNo);
            return true;
        }
        return false;
    }

    public Student authenticate(String regNo, String password) {
        Optional<Student> student = studentRepository.findById(regNo);
        if (student.isPresent() && student.get().getPassword().equals(password)) {
            return student.get();
        }
        return null;
    }
}
