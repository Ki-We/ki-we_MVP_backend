package com.kiwes.backend.reply.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
public class ReplyResponse {
    private Long replyId;
    private String body;
    private Timestamp lastModifiedTime;
    private Boolean isWriter;
}
