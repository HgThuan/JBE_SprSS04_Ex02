package com.example.coursemanagement.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;

    @OneToMany(mappedBy = "course")
    private List<StudentEnrollment> enrollments;

    public Course() {}

    public Course(String title, String status, Instructor instructor) {
        this.title = title;
        this.status = status;
        this.instructor = instructor;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public Instructor getInstructor() { return instructor; }
    public void setInstructor(Instructor instructor) { this.instructor = instructor; }
    
    public List<StudentEnrollment> getEnrollments() { return enrollments; }
    public void setEnrollments(List<StudentEnrollment> enrollments) { this.enrollments = enrollments; }
}