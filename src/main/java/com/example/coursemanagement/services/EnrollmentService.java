package com.example.coursemanagement.services;

import com.example.coursemanagement.dto.EnrollCourseRequest;
import com.example.coursemanagement.dto.EnrollmentDetail;
import com.example.coursemanagement.exceptions.BusinessException;
import com.example.coursemanagement.exceptions.ResourceNotFoundException;
import com.example.coursemanagement.models.Course;
import com.example.coursemanagement.models.StudentEnrollment;
import com.example.coursemanagement.models.Student;
import com.example.coursemanagement.repositories.StudentEnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollmentService {
    private final StudentEnrollmentRepository studentEnrollmentRepository;
    private final CourseService courseService;
    private final InstructorService instructorService;
    private final StudentService studentService;

    @Autowired
    public EnrollmentService(StudentEnrollmentRepository studentEnrollmentRepository, 
                             CourseService courseService, 
                             InstructorService instructorService,
                             StudentService studentService) {
        this.studentEnrollmentRepository = studentEnrollmentRepository;
        this.courseService = courseService;
        this.instructorService = instructorService;
        this.studentService = studentService;
    }

    public List<StudentEnrollment> getAllEnrollments() {
        return studentEnrollmentRepository.findAll();
    }

    public StudentEnrollment getEnrollmentById(Long id) {
        return studentEnrollmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found with id: " + id));
    }

    public StudentEnrollment createEnrollment(StudentEnrollment enrollment) {
        return studentEnrollmentRepository.save(enrollment);
    }

    public StudentEnrollment updateEnrollment(Long id, StudentEnrollment enrollment) {
        StudentEnrollment existing = getEnrollmentById(id);
        existing.setCourse(enrollment.getCourse());
        existing.setStudent(enrollment.getStudent());
        return studentEnrollmentRepository.save(existing);
    }

    public StudentEnrollment deleteEnrollmentById(Long id) {
        StudentEnrollment enrollment = getEnrollmentById(id);
        studentEnrollmentRepository.delete(enrollment);
        return enrollment;
    }

    public EnrollmentDetail enrollCourse(EnrollCourseRequest request) {
        Course course = courseService.getCourseById(request.getCourseId());
        
        if (!"Active".equalsIgnoreCase(course.getStatus())) {
            throw new BusinessException("Course is not active");
        }
        
        Student student = studentService.getStudentById(request.getStudentId());
        
        StudentEnrollment enrollment = new StudentEnrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        
        enrollment = studentEnrollmentRepository.save(enrollment);
        
        return new EnrollmentDetail(enrollment, course);
    }
}