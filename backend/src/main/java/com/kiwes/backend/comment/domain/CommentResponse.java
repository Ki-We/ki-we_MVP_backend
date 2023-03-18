package com.kiwes.backend.comment.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
public class CommentResponse {
    private Long commentId;
    private Long postId;
    private String body;
    private String writerNickname;
    private String writerProfileImg;
    private String writerToken;
    private Timestamp lastModifiedTime;
    private Boolean isWriter;
}
