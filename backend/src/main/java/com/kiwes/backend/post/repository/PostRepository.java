package com.kiwes.backend.post.repository;

import com.kiwes.backend.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
