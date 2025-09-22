package org.example.e_school_management.student.controller;

import org.example.e_school_management.post.service.PostService;
import org.example.e_school_management.student.model.Assignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private PostService postService;


    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("posts", postService.findAll());
        model.addAttribute("totalCourses", 5); // Placeholder
        model.addAttribute("topSemester", "Semester 3"); // Placeholder
        model.addAttribute("currentYear", "Year 2"); // Placeholder
        model.addAttribute("upcomingAssignments", 15); // Placeholder

        List<Assignment> assignments = new ArrayList<>();
        assignments.add(new Assignment("Java", 5));
        assignments.add(new Assignment("Web", 3));
        assignments.add(new Assignment("Database", 4));
        assignments.add(new Assignment("UX/UI", 2));
        assignments.add(new Assignment("Network IT", 1));

        model.addAttribute("assignments", assignments);

        return "student/dashboard";
    }

    @GetMapping("/announcements")
    public String announcements(Model model) {
        model.addAttribute("posts", postService.findAll());
        return "student/announcements";
    }
}
