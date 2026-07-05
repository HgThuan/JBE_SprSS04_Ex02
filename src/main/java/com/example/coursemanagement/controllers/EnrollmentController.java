package com.example.coursemanagement.controllers;

import com.example.coursemanagement.dto.ApiResponse;
import com.example.coursemanagement.dto.EnrollCourseRequest;
import com.example.coursemanagement.dto.EnrollmentDetail;
import com.example.coursemanagement.exceptions.BusinessException;
import com.example.coursemanagement.exceptions.ResourceNotFoundException;
import com.example.coursemanagement.models.Enrollment;
import com.example.coursemanagement.services.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    @Autowired
    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Enrollment>>> getEnrollments() {
        List<Enrollment> data = enrollmentService.getAllEnrollments();
        return ResponseEntity.ok(new ApiResponse<>(true, "Fetched all enrollments successfully", data));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Enrollment>> getEnrollmentById(@PathVariable Long id) {
        try {
            Enrollment enrollment = enrollmentService.getEnrollmentById(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Enrollment found", enrollment));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, ex.getMessage(), null));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Enrollment>> createEnrollment(@RequestBody Enrollment enrollment) {
        Enrollment created = enrollmentService.createEnrollment(enrollment);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Enrollment created successfully", created));
    }

    @PostMapping("/enroll-course")
    public ResponseEntity<ApiResponse<EnrollmentDetail>> enrollCourse(@RequestBody EnrollCourseRequest request) {
        try {
            EnrollmentDetail detail = enrollmentService.enrollCourse(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Enrollment successful", detail));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Enrollment>> updateEnrollment(@PathVariable Long id, @RequestBody Enrollment enrollment) {
        try {
            Enrollment updated = enrollmentService.updateEnrollment(id, enrollment);
            return ResponseEntity.ok(new ApiResponse<>(true, "Enrollment updated successfully", updated));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, ex.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteEnrollment(@PathVariable Long id) {
        try {
            enrollmentService.deleteEnrollmentById(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Enrollment deleted successfully", null));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, ex.getMessage(), null));
        }
    }
}