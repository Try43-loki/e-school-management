package org.example.e_school_management.student.service;

import org.example.e_school_management.student.model.Student;
import org.example.e_school_management.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public List<Student> findTop5ByOrderByIdDesc() {
        return studentRepository.findTop5ByOrderByIdDesc();
    }

    public Page<Student> findAll(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }

    public Optional<Student> findById(Long id) {
        return studentRepository.findById(id);
    }

    public void save(Student student) {
        if (student.getPassword() != null && !student.getPassword().isEmpty()) {
            student.setPassword(passwordEncoder.encode(student.getPassword()));
        }
        studentRepository.save(student);
    }

    public void deleteById(Long id) {
        studentRepository.deleteById(id);
    }

    public long count() {
        return studentRepository.count();
    }
}
