package com.example.coursemanagement.services;

import com.example.coursemanagement.exceptions.ResourceNotFoundException;
import com.example.coursemanagement.models.Student;
import com.example.coursemanagement.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import com.example.coursemanagement.dto.PageResponse;
import com.example.coursemanagement.dto.StudentResponse;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
    }

    public Student createStudent(com.example.coursemanagement.dto.StudentCreateRequest req) {
        Student student = new Student();
        student.setName(req.getName());
        student.setEmail(req.getEmail());
        return studentRepository.save(student);
    }

    private StudentResponse mapToStudentResponse(Student student) {
        return new StudentResponse(student.getId(), student.getName());
    }

    public PageResponse<StudentResponse> getPagedStudents(int page, int size, String sortBy, Sort.Direction direction, String keyword) {
        if (page < 0) {
            page = 0;
        }

        Pageable pageable;
        if (sortBy != null && !sortBy.trim().isEmpty()) {
            Sort sort = (direction != null) ? Sort.by(direction, sortBy) : Sort.by(sortBy);
            pageable = PageRequest.of(page, size, sort);
        } else {
            pageable = PageRequest.of(page, size);
        }

        Page<Student> studentPage = studentRepository.searchStudents(keyword, pageable);
        
        Page<StudentResponse> mappedPage = studentPage.map(this::mapToStudentResponse);

        return new PageResponse<>(
                mappedPage.getContent(),
                mappedPage.getNumber(),
                mappedPage.getSize(),
                mappedPage.getTotalElements(),
                mappedPage.getTotalPages(),
                mappedPage.isLast()
        );
    }
}
