package campus.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @Column(name = "reg_no", nullable = false, unique = true)
    private String regNo;

    @NotBlank(message = "Name is required")
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank(message = "Department is required")
    @Column(name = "dept", nullable = false)
    private String dept;

    @Pattern(regexp = "\\d{10}", message = "Phone number must be exactly 10 digits")
    @Column(name = "phone", nullable = false)
    private String phone;

    @NotBlank(message = "Password is required")
    @Column(name = "password", nullable = false)
    private String password;

    public Student() {}

    public Student(String regNo, String name, String dept, String phone, String password) {
        this.regNo = regNo;
        this.name = name;
        this.dept = dept;
        this.phone = phone;
        this.password = password;
    }

    // Getters & Setters
    public String getRegNo() { return regNo; }
    public void setRegNo(String regNo) { this.regNo = regNo; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDept() { return dept; }
    public void setDept(String dept) { this.dept = dept; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
