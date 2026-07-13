package com.example.coursemanagement.repositories;

import com.example.coursemanagement.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import com.example.coursemanagement.enums.CourseStatus;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    boolean existsByIdAndStatus(Long id, String status);

    @Query("SELECT c FROM Course c WHERE c.status = :#{#status.name()}")
    Page<Course> findAllByStatus(@Param("status") CourseStatus status, Pageable pageable);
}
