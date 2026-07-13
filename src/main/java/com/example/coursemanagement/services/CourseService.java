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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public com.example.coursemanagement.dto.PageResponse<com.example.coursemanagement.dto.CourseResponse> getPagedCourses(int page, int size, String sortBy, Sort.Direction direction) {
        if (page < 0) {
            page = 0;
        }
        if (sortBy == null || sortBy.trim().isEmpty()) {
            sortBy = "id";
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Page<Course> coursePage = courseRepository.findAll(pageable);
        
        Page<com.example.coursemanagement.dto.CourseResponse> mappedPage = coursePage.map(this::mapToCourseResponse);

        return new com.example.coursemanagement.dto.PageResponse<>(
                mappedPage.getContent(),
                mappedPage.getNumber(),
                mappedPage.getSize(),
                mappedPage.getTotalElements(),
                mappedPage.getTotalPages(),
                mappedPage.isLast()
        );
    }

}