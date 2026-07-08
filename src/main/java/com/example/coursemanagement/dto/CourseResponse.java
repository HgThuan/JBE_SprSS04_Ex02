package com.example.coursemanagement.dto;

public class CourseResponse {
    private Long id;
    private String title;
    private String status;
    private CourseInstructorResponse instructor;

    public CourseResponse() {
    }

    public CourseResponse(Long id, String title, String status, CourseInstructorResponse instructor) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.instructor = instructor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public CourseInstructorResponse getInstructor() {
        return instructor;
    }

    public void setInstructor(CourseInstructorResponse instructor) {
        this.instructor = instructor;
    }
}
