package org.example.e_school_management.admin.controller;

import org.example.e_school_management.admin.repository.AdminRepository;
import org.example.e_school_management.student.model.Student;
import org.example.e_school_management.student.repository.StudentRepository;
import org.example.e_school_management.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.example.e_school_management.post.service.PostService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PostService postService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("studentCount", studentService.count());
        model.addAttribute("latestPosts", postService.findLatest5());
        model.addAttribute("graduatedCount", 150); // Placeholder
        model.addAttribute("departmentCount", 5); // Placeholder
        model.addAttribute("scholarshipStudentCount", 25); // Placeholder
        model.addAttribute("recentStudents", studentService.findTop5ByOrderByIdDesc());
        return "admin/dashboard";
    }

    @GetMapping("/students")
    public String listStudents(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Student> studentPage = studentService.findAll(pageable);
        model.addAttribute("studentPage", studentPage);
        return "admin/students";
    }

    @GetMapping("/students/add")
    public String addStudent(Model model) {
        model.addAttribute("student", new Student());
        return "admin/add-student";
    }

    @PostMapping("/students/add")
    public String createStudent(@Valid @ModelAttribute Student student, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/add-student";
        }
        studentService.save(student);
        return "redirect:/admin/students";
    }

    @GetMapping("/students/edit/{id}")
    public String editStudent(@PathVariable Long id, Model model) {
        studentService.findById(id).ifPresent(student -> model.addAttribute("student", student));
        return "admin/edit-student";
    }

    @PostMapping("/students/edit/{id}")
    public String updateStudent(@PathVariable Long id, @Valid @ModelAttribute Student student, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/edit-student";
        }
        student.setId(id);
        studentService.save(student);
        return "redirect:/admin/students";
    }

    @GetMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteById(id);
        return "redirect:/admin/students";
    }
}
