package com.kiwes.backend.qna.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
public class QnaResponse {
    private Long qnaId;
    private String body;
    private Timestamp lastModifiedTime;
    private String writerToken;
    private String writerNickname;
    private String writerProfileImg;
    private Boolean isWriter;
    private Boolean hasReply;
}
