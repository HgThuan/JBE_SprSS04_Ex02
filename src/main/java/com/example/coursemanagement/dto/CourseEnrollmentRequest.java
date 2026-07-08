package com.example.coursemanagement.dto;

public class CourseEnrollmentRequest {
    private Long studentId;

    public CourseEnrollmentRequest() {
    }

    public CourseEnrollmentRequest(Long studentId) {
        this.studentId = studentId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
}
