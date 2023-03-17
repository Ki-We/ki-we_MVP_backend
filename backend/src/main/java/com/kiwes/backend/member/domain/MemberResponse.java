package com.kiwes.backend.member.domain;

import lombok.*;

@Data
@ToString
@AllArgsConstructor
@Builder
public class MemberResponse {

    private Long memberId;

    private Long kakaoId;

    private String profileImg;

    private String nickname;

    private String memberToken;

    private String gender;

    private String birth;

    private Boolean isMe;
}
