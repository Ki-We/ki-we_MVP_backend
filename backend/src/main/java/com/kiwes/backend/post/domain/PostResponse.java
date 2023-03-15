package com.kiwes.backend.post.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class PostResponse {
    private String title;
    private String body;
    private LocalDateTime meetingDate;
    private LocalDateTime deadLineDate;
    private String place;
    private String price;
    private Long recruitNum;
    private String meetingGender;
    private String language;
    private String category;
    private String hashtag;
    private String chatLink;
    private Long hostId;
    private String hostProfileImg;
    private Boolean isHost;
    private Boolean hasJoin;
    private Boolean hasLike;
}
