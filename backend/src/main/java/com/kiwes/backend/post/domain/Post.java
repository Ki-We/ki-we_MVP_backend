package com.kiwes.backend.post.domain;

import com.kiwes.backend.comment.domain.Comment;
import com.kiwes.backend.member.domain.Member;
import com.kiwes.backend.qna.domain.Qna;
import com.kiwes.backend.reply.domain.Reply;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    @CreationTimestamp
    private Timestamp createTime;

    @UpdateTimestamp
    private Timestamp lastModifiedTime;

    private String title;

    private String postImage;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member host;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Qna> qnaList = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reply> replyList = new ArrayList<>();

    public void updatePostImage(String postImage) {
        this.postImage = postImage;
    }

    public void confirmHost(Member member) {
        this.host = member;
        member.addPost(this);
    }
}
