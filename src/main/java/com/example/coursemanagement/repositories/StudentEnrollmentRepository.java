package com.example.coursemanagement.repositories;

import com.example.coursemanagement.models.StudentEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentEnrollmentRepository extends JpaRepository<StudentEnrollment, Long> {
    List<StudentEnrollment> findByCourseId(Long courseId);
}
