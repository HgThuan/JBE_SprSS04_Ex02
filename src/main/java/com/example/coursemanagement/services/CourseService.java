package com.example.coursemanagement.services;

import com.example.coursemanagement.exceptions.ResourceNotFoundException;
import com.example.coursemanagement.models.Course;
import com.example.coursemanagement.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));
    }

    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    public Course updateCourse(Long id, Course course) {
        Course existingCourse = getCourseById(id);
        existingCourse.setTitle(course.getTitle());
        existingCourse.setStatus(course.getStatus());
        existingCourse.setInstructor(course.getInstructor());
        return courseRepository.save(existingCourse);
    }

    public Course deleteCourseById(Long id) {
        Course course = getCourseById(id);
        courseRepository.delete(course);
        return course;
    }
}