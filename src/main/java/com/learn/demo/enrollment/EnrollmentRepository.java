package com.learn.demo.enrollment;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository


public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer> {
    @Query("SELECT COUNT(e) > 0 FROM Enrollment e WHERE e.user.userId = :userId AND e.course.courseId = :courseId")
    boolean existsByUserAndCourse(@Param("userId") int userId, @Param("courseId") int courseId);
}
