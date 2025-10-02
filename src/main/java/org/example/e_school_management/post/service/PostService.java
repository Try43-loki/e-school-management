package org.example.e_school_management.post.service;

import org.example.e_school_management.filestorage.FileStorageService;
import org.example.e_school_management.post.model.Post;
import org.example.e_school_management.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private FileStorageService fileStorageService;

    public List<Post> findLatest5() {
        return postRepository.findTop5ByOrderByCreatedAtDesc();
    }

    public Page<Post> findAll(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }

    public Post save(Post post, MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            // If there's an existing file, delete it
            if (post.getId() != null) {
                postRepository.findById(post.getId()).ifPresent(existingPost -> {
                    if (existingPost.getFileName() != null) {
                        fileStorageService.deleteFile(existingPost.getFileName());
                    }
                });
            }

            java.util.Map<String, String> fileNames = fileStorageService.storeFile(file);
            post.setFileName(fileNames.get("fileName"));
            post.setOriginalFileName(fileNames.get("originalFileName"));
            post.setFilePath("/uploads/" + fileNames.get("fileName"));
        } else {
            // If no new file is uploaded, retain the existing file info
            if (post.getId() != null) {
                postRepository.findById(post.getId()).ifPresent(existingPost -> {
                    post.setFileName(existingPost.getFileName());
                    post.setOriginalFileName(existingPost.getOriginalFileName());
                    post.setFilePath(existingPost.getFilePath());
                });
            }
        }
        return postRepository.save(post);
    }

    public void deleteById(Long id) {
        postRepository.findById(id).ifPresent(post -> {
            if (post.getFileName() != null) {
                fileStorageService.deleteFile(post.getFileName());
            }
            postRepository.deleteById(id);
        });
    }
}
