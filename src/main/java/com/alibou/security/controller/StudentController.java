package com.alibou.security.controller;

import com.alibou.security.model.Student;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StudentController {

    private List<Student> students = new ArrayList<>(List.of(
            new Student(1,"Navin", 60),
            new Student(2,"Kiran", 65)
    ));
    @GetMapping("/students")
    public List<Student> getStudents() {
        return students;
    }
    //any CSRF tocken works on get only, you have to adjust in post and use the token that you have
    @PostMapping("/students")
    public Student addStudent(@RequestBody Student student) {
        students.add(student);
        return student;
    }
    //you can either adjust it from postman and see if it works, but lets try to send the token from here
    @GetMapping("/csrf-token")
    public CsrfToken getCsrfToken(HttpServletRequest request) {
        return (CsrfToken) request.getAttribute("_csrf");
    }
}
