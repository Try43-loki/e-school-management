package org.example.e_school_management.student.repository;

import org.example.e_school_management.student.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByUsername(String username);
    List<Student> findTop5ByOrderByIdDesc();
}
