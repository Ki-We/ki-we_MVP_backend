package com.kiwes.backend.participation.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ParticipationCreate {
    private Long memberId;
    private Long postId;

    public ParticipationCreate(Long memberId, Long postId) {
        this.memberId = memberId;
        this.postId = postId;
    }
}
