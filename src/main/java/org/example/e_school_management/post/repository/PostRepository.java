package org.example.e_school_management.post.repository;

import org.example.e_school_management.post.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findTop5ByOrderByCreatedAtDesc();
}
