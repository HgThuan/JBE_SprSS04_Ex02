package com.example.coursemanagement.dto;

import com.example.coursemanagement.models.Course;
import com.example.coursemanagement.models.Enrollment;

public class EnrollmentDetail {
    private Enrollment enrollment;
    private Course course;

    public EnrollmentDetail() {
    }

    public EnrollmentDetail(Enrollment enrollment, Course course) {
        this.enrollment = enrollment;
        this.course = course;
    }

    public Enrollment getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(Enrollment enrollment) {
        this.enrollment = enrollment;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
