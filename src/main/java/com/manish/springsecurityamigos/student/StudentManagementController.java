package com.manish.springsecurityamigos.student;

import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("management/api/v1/students")
public class StudentManagementController {


    private static final List<Student> STUDENTS = Arrays.asList(
            new Student(1, "Manish Kumar"),
            new Student(2, "Rohit Kaushal"),
            new Student(3, "Sakshi Uniyal")
    );

    @GetMapping
    public List<Student> getAllStudents() {
        System.out.println("getAllStudents");
        return STUDENTS;
    }


    @PostMapping
    public void registerNewStudent(@RequestBody Student student) {
        System.out.println("registerNewStudent");
        System.out.println(student);
    }


    @DeleteMapping(value = "{studentId}")
    public void deleteStudent(@PathVariable("studentId") Integer studentId) {
        System.out.println("deleteStudent");
        System.out.println(studentId);
    }

    @PutMapping(value = "{studentId}")
    public void updateStudent(@PathVariable("studentId") Integer studentId,@RequestBody Student student) {
        System.out.println("updateStudent");
        System.out.println(studentId +"->" + student);
    }

}
