package com.kiwes.backend.reply.repository;

import com.kiwes.backend.qna.domain.Qna;
import com.kiwes.backend.reply.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    Optional<Reply> findByQna(Qna qna);
}
