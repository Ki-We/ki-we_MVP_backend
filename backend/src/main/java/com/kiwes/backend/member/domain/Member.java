package com.kiwes.backend.member.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    private Long kakaoId;

    private String kakaoProfileImg;

    private String kakaoEmail;

    private String birthday;

    private String gender;

    private String nickname;

    private Boolean privacyAgreement;

    private Boolean termAgreement;

    private String memberIntro;

    @Column(length = 1000)
    private String refreshToken;

    @CreationTimestamp
    private Timestamp createTime;

    @UpdateTimestamp
    private Timestamp lastModifiedTime;


    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void addKakaoProfileImage(String kakaoProfileImg) {
        this.kakaoProfileImg = kakaoProfileImg;
    }



}
