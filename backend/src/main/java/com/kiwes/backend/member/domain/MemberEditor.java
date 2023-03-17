package com.kiwes.backend.member.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MemberEditor {
    private String nickname;
    private String gender;
    private String birthday;
    private String email;
    private String memberIntro;
}
