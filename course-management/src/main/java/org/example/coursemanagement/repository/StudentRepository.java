package org.example.coursemanagement.repository;

import org.example.coursemanagement.JPO.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {

}
