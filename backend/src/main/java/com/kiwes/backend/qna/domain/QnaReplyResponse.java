package com.kiwes.backend.qna.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
public class QnaReplyResponse {
    private Long id;
    private Long postId;
    private String body;
    private Timestamp lastModifiedTime;
    private String writerToken;
    private String writerNickname;
    private String writerProfileImg;
    private Boolean isWriter;
}
