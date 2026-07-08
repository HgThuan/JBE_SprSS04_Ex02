package com.example.coursemanagement.services;

import com.example.coursemanagement.dto.CourseCreateRequest;
import com.example.coursemanagement.dto.CourseUpdateRequest;
import com.example.coursemanagement.exceptions.ResourceNotFoundException;
import com.example.coursemanagement.models.Course;
import com.example.coursemanagement.models.Instructor;
import com.example.coursemanagement.repositories.CourseRepository;
import com.example.coursemanagement.repositories.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final InstructorRepository instructorRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository, InstructorRepository instructorRepository) {
        this.courseRepository = courseRepository;
        this.instructorRepository = instructorRepository;
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));
    }

    public Course createCourse(CourseCreateRequest req) {
        Instructor instructor = instructorRepository.findById(req.getInstructorId())
                .orElseThrow(() -> new ResourceNotFoundException("Instructor not found with id: " + req.getInstructorId()));
        
        Course course = new Course();
        course.setTitle(req.getTitle());
        course.setStatus(req.getStatus());
        course.setInstructor(instructor);
        return courseRepository.save(course);
    }

    public Course updateCourse(Long id, CourseUpdateRequest req) {
        Course existingCourse = getCourseById(id);
        Instructor instructor = instructorRepository.findById(req.getInstructorId())
                .orElseThrow(() -> new ResourceNotFoundException("Instructor not found with id: " + req.getInstructorId()));

        existingCourse.setTitle(req.getTitle());
        existingCourse.setStatus(req.getStatus());
        existingCourse.setInstructor(instructor);
        return courseRepository.save(existingCourse);
    }

    public Course deleteCourseById(Long id) {
        Course course = getCourseById(id);
        courseRepository.delete(course);
        return course;
    }

    public boolean existsByIdAndStatus(Long id, String status) {
        return courseRepository.existsByIdAndStatus(id, status);
    }

    public List<com.example.coursemanagement.dto.CourseResponse> getAllCourseResponses() {
        return getAllCourses().stream()
                .map(this::mapToCourseResponse)
                .collect(java.util.stream.Collectors.toList());
    }

    public com.example.coursemanagement.dto.CourseResponse getCourseResponseById(Long id) {
        Course course = getCourseById(id);
        return mapToCourseResponse(course);
    }

    private com.example.coursemanagement.dto.CourseResponse mapToCourseResponse(Course course) {
        com.example.coursemanagement.dto.CourseInstructorResponse instructorResponse = null;
        if (course.getInstructor() != null) {
            instructorResponse = new com.example.coursemanagement.dto.CourseInstructorResponse(
                    course.getInstructor().getId(),
                    course.getInstructor().getName()
            );
        }
        return new com.example.coursemanagement.dto.CourseResponse(
                course.getId(),
                course.getTitle(),
                course.getStatus(),
                instructorResponse
        );
    }
}