package com.example.coursemanagement.dto;

import com.example.coursemanagement.models.Course;
import com.example.coursemanagement.models.StudentEnrollment;

public class EnrollmentDetail {
    private StudentEnrollment enrollment;
    private Course course;

    public EnrollmentDetail() {
    }

    public EnrollmentDetail(StudentEnrollment enrollment, Course course) {
        this.enrollment = enrollment;
        this.course = course;
    }

    public StudentEnrollment getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(StudentEnrollment enrollment) {
        this.enrollment = enrollment;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
