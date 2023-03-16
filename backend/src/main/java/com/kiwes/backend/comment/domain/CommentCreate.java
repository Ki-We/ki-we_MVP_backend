package com.kiwes.backend.comment.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CommentCreate {

    private Long postId;
    private String body;

}
