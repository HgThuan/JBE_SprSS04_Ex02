package com.example.coursemanagement.dto;

public class EnrollCourseRequest {
    private Long studentId;
    private Long courseId;

    public EnrollCourseRequest() {
    }

    public EnrollCourseRequest(Long studentId, Long courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
}
