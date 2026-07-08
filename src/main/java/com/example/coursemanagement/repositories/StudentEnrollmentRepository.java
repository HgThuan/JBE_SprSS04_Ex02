package com.example.coursemanagement.repositories;

import com.example.coursemanagement.models.StudentEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

@Repository
public interface StudentEnrollmentRepository extends JpaRepository<StudentEnrollment, Long> {
    List<StudentEnrollment> findByCourseId(Long courseId);

    boolean existsByCourseIdAndStudentId(Long courseId, Long studentId);

    Optional<StudentEnrollment> findByCourseIdAndStudentId(Long courseId, Long studentId);

    @Query("SELECT se FROM StudentEnrollment se WHERE se.course.id = :courseId AND LOWER(se.student.name) LIKE LOWER(CONCAT('%', :studentName, '%'))")
    List<StudentEnrollment> searchStudentsInCourse(@Param("courseId") Long courseId, @Param("studentName") String studentName);
}
