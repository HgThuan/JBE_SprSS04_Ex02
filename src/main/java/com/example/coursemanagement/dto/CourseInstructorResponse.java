package com.example.coursemanagement.dto;

public class CourseInstructorResponse {
    private Long id;
    private String name;

    public CourseInstructorResponse() {
    }

    public CourseInstructorResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
