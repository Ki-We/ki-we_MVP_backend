package com.kiwes.backend.member.repository;

import com.kiwes.backend.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByKakaoId(Long kakaoId);
    Optional<Member> findByNickname(String Nickname);
    Optional<Member> findByRefreshToken(String refreshToken);
    Optional<Member> findByMemberToken(String memberToken);

}
