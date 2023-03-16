package com.kiwes.backend.heart.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HeartRequest {
    private Long memberId;
    private Long postId;

    public HeartRequest(Long memberId, Long postId) {
        this.memberId = memberId;
        this.postId = postId;
    }
}
