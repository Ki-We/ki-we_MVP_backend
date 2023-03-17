package com.kiwes.backend.reply.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ReplyCreate {
    private Long postId;
    private Long qnaId;
    private String body;
}
