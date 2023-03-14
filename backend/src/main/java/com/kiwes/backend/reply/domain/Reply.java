package com.kiwes.backend.reply.domain;

import com.kiwes.backend.member.domain.Member;
import com.kiwes.backend.post.domain.Post;
import com.kiwes.backend.qna.domain.Qna;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long replyId;

    @CreationTimestamp
    private Timestamp createTime;

    @UpdateTimestamp
    private Timestamp lastModifiedTime;

    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToOne
    @JoinColumn(name = "qna_id")
    private Qna qna;
}
