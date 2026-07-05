package com.example.coursemanagement.controllers;

import com.example.coursemanagement.dto.ApiResponse;
import com.example.coursemanagement.models.Instructor;
import com.example.coursemanagement.services.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/instructors")
public class InstructorController {
    private final InstructorService instructorService;

    @Autowired
    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Instructor>>> getInstructors() {
        List<Instructor> data = instructorService.getAllInstructors();
        return ResponseEntity.ok(new ApiResponse<>(true, "Fetched all instructors successfully", data));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Instructor>> getInstructorById(@PathVariable Long id) {
        Instructor instructor = instructorService.getInstructorById(id);
        if (instructor != null) {
            return ResponseEntity.ok(new ApiResponse<>(true, "Instructor found", instructor));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Instructor not found", null));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Instructor>> createInstructor(@RequestBody Instructor instructor) {
        Instructor created = instructorService.createInstructor(instructor);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Instructor created successfully", created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Instructor>> updateInstructor(@PathVariable Long id, @RequestBody Instructor instructor) {
        Instructor updated = instructorService.updateInstructor(id, instructor);
        if (updated != null) {
            return ResponseEntity.ok(new ApiResponse<>(true, "Instructor updated successfully", updated));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Instructor not found", null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteInstructor(@PathVariable Long id) {
        Instructor deleted = instructorService.deleteInstructorById(id);
        if (deleted != null) {
            // Note: 204 No Content doesn't return a body, but for consistency if we want to return the wrapper, we might use 200 OK.
            // If we keep 204, the body is ignored by HTTP clients. We will return 200 OK with the wrapper as is common practice when wrapping.
            return ResponseEntity.ok(new ApiResponse<>(true, "Instructor deleted successfully", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Instructor not found", null));
        }
    }
}