package com.kiwes.backend.comment.repository;

import com.kiwes.backend.comment.domain.Comment;
import com.kiwes.backend.member.domain.Member;
import com.kiwes.backend.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findByPost(Post post);
    List<Comment> findAllByPost(Post post);
    List<Comment> findAllByWriter(Member member);
}
