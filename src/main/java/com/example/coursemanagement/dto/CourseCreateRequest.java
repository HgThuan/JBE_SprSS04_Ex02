package com.example.coursemanagement.dto;

public class CourseCreateRequest {
    private String title;
    private String status;
    private Long instructorId;

    public CourseCreateRequest() {
    }

    public CourseCreateRequest(String title, String status, Long instructorId) {
        this.title = title;
        this.status = status;
        this.instructorId = instructorId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(Long instructorId) {
        this.instructorId = instructorId;
    }
}
