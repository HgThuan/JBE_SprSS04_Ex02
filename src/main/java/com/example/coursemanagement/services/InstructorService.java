package com.example.coursemanagement.services;

import com.example.coursemanagement.dto.InstructorDetail;
import com.example.coursemanagement.exceptions.ResourceNotFoundException;
import com.example.coursemanagement.models.Course;
import com.example.coursemanagement.models.StudentEnrollment;
import com.example.coursemanagement.models.Instructor;
import com.example.coursemanagement.repositories.StudentEnrollmentRepository;
import com.example.coursemanagement.repositories.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InstructorService {
    private final InstructorRepository instructorRepository;
    private final CourseService courseService;
    private final StudentEnrollmentRepository studentEnrollmentRepository;

    @Autowired
    public InstructorService(InstructorRepository instructorRepository,
                             CourseService courseService,
                             StudentEnrollmentRepository studentEnrollmentRepository) {
        this.instructorRepository = instructorRepository;
        this.courseService = courseService;
        this.studentEnrollmentRepository = studentEnrollmentRepository;
    }

    public List<Instructor> getAllInstructors() {
        return instructorRepository.findAll();
    }

    public Instructor getInstructorById(Long id) {
        return instructorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Instructor not found with id: " + id));
    }

    public Instructor createInstructor(Instructor instructor) {
        return instructorRepository.save(instructor);
    }

    public Instructor updateInstructor(Long id, Instructor instructor) {
        Instructor existingInstructor = getInstructorById(id);
        existingInstructor.setName(instructor.getName());
        existingInstructor.setEmail(instructor.getEmail());
        return instructorRepository.save(existingInstructor);
    }

    public Instructor deleteInstructorById(Long id) {
        Instructor instructor = getInstructorById(id);
        instructorRepository.delete(instructor);
        return instructor;
    }

    public List<InstructorDetail> getInstructorDetails() {
        List<Instructor> instructors = instructorRepository.findAll();
        List<Course> allCourses = courseService.getAllCourses();
        List<StudentEnrollment> allEnrollments = studentEnrollmentRepository.findAll();

        return instructors.stream().map(instructor -> {
            List<Course> validCourses = allCourses.stream()
                    .filter(course -> course.getInstructor() != null && course.getInstructor().getId().equals(instructor.getId()))
                    .filter(course -> "Active".equalsIgnoreCase(course.getStatus()))
                    .filter(course -> allEnrollments.stream()
                            .anyMatch(e -> e.getCourse().getId().equals(course.getId())))
                    .collect(Collectors.toList());
            
            return new InstructorDetail(instructor, validCourses);
        }).collect(Collectors.toList());
    }
}