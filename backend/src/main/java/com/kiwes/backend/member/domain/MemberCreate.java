package com.kiwes.backend.member.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class MemberCreate {
    private String profileImage;
    private String nickname;
    private String gender;
    private String birthday;
    private String email;
    private String memberIntro;

}
