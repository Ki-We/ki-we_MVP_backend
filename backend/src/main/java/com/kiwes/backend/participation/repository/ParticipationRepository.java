package com.kiwes.backend.participation.repository;

import com.kiwes.backend.participation.domain.Participation;
import com.kiwes.backend.member.domain.Member;
import com.kiwes.backend.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {
    Optional<Participation> findByMemberAndPost(Member member, Post post);

}
