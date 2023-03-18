package com.kiwes.backend.post.repository;

import com.kiwes.backend.member.domain.Member;
import com.kiwes.backend.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByHost(Member member);

    @Query(value = "SELECT * FROM post ORDER BY view_count DESC limit 10", nativeQuery = true)
    List<Post> recommendPost();
}
