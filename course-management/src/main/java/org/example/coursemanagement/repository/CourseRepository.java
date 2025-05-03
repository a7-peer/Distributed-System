package org.example.coursemanagement.repository;

import org.example.coursemanagement.JPO.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
