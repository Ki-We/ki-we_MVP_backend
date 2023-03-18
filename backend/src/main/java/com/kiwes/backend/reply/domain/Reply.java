package com.kiwes.backend.reply.domain;

import com.kiwes.backend.member.domain.Member;
import com.kiwes.backend.post.domain.Post;
import com.kiwes.backend.qna.domain.Qna;
import com.kiwes.backend.qna.domain.QnaEditor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    public ReplyEditor.ReplyEditorBuilder toEditor() {
        return ReplyEditor.builder()
                .body(body);
    }

    public void edit(ReplyEditor replyEditor) {
        body = replyEditor.getBody();
    }
}
