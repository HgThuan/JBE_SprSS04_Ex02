package com.example.coursemanagement.dto;

public class CourseDropoutRequest {
    private Long studentId;

    public CourseDropoutRequest() {
    }

    public CourseDropoutRequest(Long studentId) {
        this.studentId = studentId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
}
