package com.example.coursemanagement.models;

import jakarta.persistence.*;

@Entity
@Table(name = "student_enrollments", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"student_id", "course_id"})
})
public class StudentEnrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "enrolled_at", nullable = false, updatable = false)
    private java.time.LocalDateTime enrolledAt;

    public StudentEnrollment() {}

    public StudentEnrollment(Student student, Course course) {
        this.student = student;
        this.course = course;
    }

    @PrePersist
    protected void onCreate() {
        this.enrolledAt = java.time.LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }

    public java.time.LocalDateTime getEnrolledAt() { return enrolledAt; }
    public void setEnrolledAt(java.time.LocalDateTime enrolledAt) { this.enrolledAt = enrolledAt; }
}
