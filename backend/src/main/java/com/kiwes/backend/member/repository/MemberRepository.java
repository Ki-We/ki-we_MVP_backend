package com.kiwes.backend.member.repository;

import com.kiwes.backend.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByKakaoId(String kakaoId);
    Optional<Member> findByKakaoNickname(String kakaoNickname);
    Optional<Member> findByRefreshToken(String refreshToken);
}
