package com.example.coursemanagement.services;

import com.example.coursemanagement.dto.EnrollCourseRequest;
import com.example.coursemanagement.dto.EnrollmentDetail;
import com.example.coursemanagement.exceptions.BusinessException;
import com.example.coursemanagement.exceptions.ResourceNotFoundException;
import com.example.coursemanagement.models.Course;
import com.example.coursemanagement.models.Enrollment;
import com.example.coursemanagement.models.Instructor;
import com.example.coursemanagement.repositories.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final CourseService courseService;
    private final InstructorService instructorService;

    @Autowired
    public EnrollmentService(EnrollmentRepository enrollmentRepository, 
                             CourseService courseService, 
                             InstructorService instructorService) {
        this.enrollmentRepository = enrollmentRepository;
        this.courseService = courseService;
        this.instructorService = instructorService;
    }

    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    public Enrollment getEnrollmentById(Long id) {
        return enrollmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found with id: " + id));
    }

    public Enrollment createEnrollment(Enrollment enrollment) {
        return enrollmentRepository.create(enrollment);
    }

    public Enrollment updateEnrollment(Long id, Enrollment enrollment) {
        return enrollmentRepository.update(id, enrollment);
    }

    public Enrollment deleteEnrollmentById(Long id) {
        return enrollmentRepository.deleteById(id);
    }

    public EnrollmentDetail enrollCourse(EnrollCourseRequest request) {
        // 1. Kiểm tra Course tồn tại
        Course course = courseService.getCourseById(request.getCourseId());
        
        // 2. Kiểm tra trạng thái Course
        if (!"Active".equalsIgnoreCase(course.getStatus())) {
            throw new BusinessException("Course is not active");
        }
        
        // 3. Kiểm tra Instructor tồn tại
        Instructor instructor = instructorService.getInstructorById(course.getInstructorId());
        
        // 4. Thực hiện lưu Enrollment
        Enrollment enrollment = new Enrollment();
        enrollment.setStudentName(request.getStudentName());
        enrollment.setCourseId(request.getCourseId());
        
        enrollment = enrollmentRepository.create(enrollment);
        
        return new EnrollmentDetail(enrollment, course);
    }
}