package com.example.coursemanagement.dto;

public class StudentCreateRequest {
    private String name;
    private String email;

    public StudentCreateRequest() {
    }

    public StudentCreateRequest(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
