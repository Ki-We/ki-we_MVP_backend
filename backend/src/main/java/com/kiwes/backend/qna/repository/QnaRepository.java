package com.kiwes.backend.qna.repository;

import com.kiwes.backend.post.domain.Post;
import com.kiwes.backend.qna.domain.Qna;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QnaRepository extends JpaRepository<Qna, Long> {
    Optional<Qna> findByPost(Post post);
    List<Qna> findAllByPost(Post post);
}
