package org.example.e_school_management.post.controller;

import org.example.e_school_management.post.model.Post;
import org.example.e_school_management.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping
    public String listPosts(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> postPage = postService.findAll(pageable);
        model.addAttribute("postPage", postPage);
        return "admin/posts";
    }

    @GetMapping("/add")
    public String addPostForm(Model model) {
        model.addAttribute("post", new Post());
        return "admin/add-post";
    }

    @PostMapping("/add")
    public String createPost(@Valid @ModelAttribute Post post, BindingResult result, @RequestParam("file") MultipartFile file) {
        if (result.hasErrors()) {
            return "admin/add-post";
        }
        postService.save(post, file);
        return "redirect:/admin/posts";
    }

    @GetMapping("/edit/{id}")
    public String editPostForm(@PathVariable Long id, Model model) {
        postService.findById(id).ifPresent(post -> model.addAttribute("post", post));
        return "admin/edit-post";
    }

    @PostMapping("/edit/{id}")
    public String updatePost(@PathVariable Long id, @Valid @ModelAttribute Post post, BindingResult result, @RequestParam("file") MultipartFile file) {
        if (result.hasErrors()) {
            return "admin/edit-post";
        }
        post.setId(id);
        postService.save(post, file);
        return "redirect:/admin/posts";
    }

    @GetMapping("/delete/{id}")
    public String deletePost(@PathVariable Long id) {
        postService.deleteById(id);
        return "redirect:/admin/posts";
    }
}
