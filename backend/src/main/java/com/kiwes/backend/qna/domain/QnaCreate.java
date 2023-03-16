package com.kiwes.backend.qna.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class QnaCreate {
    private Long postId;
    private String body;
}
