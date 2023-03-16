package com.kiwes.backend.heart.repository;

import com.kiwes.backend.heart.domain.Heart;
import com.kiwes.backend.member.domain.Member;
import com.kiwes.backend.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HeartRepository extends JpaRepository<Heart, Long> {
    Optional<Heart> findByMemberAndPost(Member member, Post post);
}
