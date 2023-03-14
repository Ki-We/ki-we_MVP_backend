package com.kiwes.backend.qna.domain;

import com.kiwes.backend.member.domain.Member;
import com.kiwes.backend.post.domain.Post;
import com.kiwes.backend.reply.domain.Reply;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Qna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qna_id")
    private Long qnaId;

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

    @OneToOne(mappedBy = "qna", cascade = CascadeType.ALL, orphanRemoval = true)
    private Reply reply;

    public void addReply(Reply reply) {
        this.reply = reply;
    }
}
