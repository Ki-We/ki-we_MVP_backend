package com.kiwes.backend.join.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class JoinCreate {
    private Long memberId;
    private Long postId;

    public JoinCreate(Long memberId, Long postId) {
        this.memberId = memberId;
        this.postId = postId;
    }
}
