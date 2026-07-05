package com.example.coursemanagement.dto;

public class EnrollCourseRequest {
    private String studentName;
    private Long courseId;

    public EnrollCourseRequest() {
    }

    public EnrollCourseRequest(String studentName, Long courseId) {
        this.studentName = studentName;
        this.courseId = courseId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
}
