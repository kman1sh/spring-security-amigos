package com.manish.springsecurityamigos.student;

public class Student {

    private final int StudentId;
    private final String StudentName;

    public Student(int studentId, String studentName) {
        StudentId = studentId;
        StudentName = studentName;
    }

    public int getStudentId() {
        return StudentId;
    }

    public String getStudentName() {
        return StudentName;
    }
}
