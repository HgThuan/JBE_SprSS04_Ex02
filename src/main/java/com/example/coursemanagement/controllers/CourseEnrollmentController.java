package com.example.coursemanagement.controllers;

import com.example.coursemanagement.dto.ApiResponse;
import com.example.coursemanagement.dto.CourseEnrollmentRequest;
import com.example.coursemanagement.dto.CourseEnrollmentResponse;
import com.example.coursemanagement.exceptions.BusinessException;
import com.example.coursemanagement.exceptions.ResourceNotFoundException;
import com.example.coursemanagement.services.StudentEnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses/{courseId}/enrollments")
public class CourseEnrollmentController {

    private final StudentEnrollmentService studentEnrollmentService;

    @Autowired
    public CourseEnrollmentController(StudentEnrollmentService studentEnrollmentService) {
        this.studentEnrollmentService = studentEnrollmentService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CourseEnrollmentResponse>> enrollStudent(
            @PathVariable Long courseId,
            @RequestBody CourseEnrollmentRequest request) {
        try {
            CourseEnrollmentResponse response = studentEnrollmentService.enrollStudentInCourse(courseId, request.getStudentId());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Student enrolled successfully", response));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, ex.getMessage(), null));
        } catch (BusinessException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, ex.getMessage(), null));
        }
    }

    @DeleteMapping("/students/{studentId}")
    public ResponseEntity<Void> dropoutStudent(
            @PathVariable Long courseId,
            @PathVariable Long studentId) {
        try {
            studentEnrollmentService.dropoutStudentFromCourse(courseId, studentId);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/students")
    public ResponseEntity<ApiResponse<List<CourseEnrollmentResponse>>> searchStudents(
            @PathVariable Long courseId,
            @RequestParam(value = "search", required = false) String search) {
        try {
            List<CourseEnrollmentResponse> responses = studentEnrollmentService.searchStudentsInCourse(courseId, search);
            return ResponseEntity.ok(new ApiResponse<>(true, "Students retrieved successfully", responses));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, ex.getMessage(), null));
        }
    }
}
