package com.kiwes.backend.join.repository;

import com.kiwes.backend.heart.domain.Heart;
import com.kiwes.backend.join.domain.Join;
import com.kiwes.backend.member.domain.Member;
import com.kiwes.backend.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JoinRepository extends JpaRepository<Join, Long> {
    Optional<Join> findByMemberAndPost(Member member, Post post);

}
