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

    private Long currentNum;

    private String meetingGender;

    private String language;

    private String category;

    private String hashtag;

    private String chatLink;

    private Long viewCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member host;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Qna> qnaList = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reply> replyList = new ArrayList<>();


    public void addComment(Comment comment) {
        commentList.add(comment);
    }

    public void addQna(Qna qna) {
        qnaList.add(qna);
    }

    public void addReply(Reply reply) {
        replyList.add(reply);
    }

    public void updatePostImage(String postImage) {
        this.postImage = postImage;
    }

    public void confirmHost(Member member) {
        this.host = member;
        member.addPost(this);
    }

    public void upperView() {
        this.viewCount += 1L;
    }

    public void upperMember() {
        this.currentNum += 1L;
    }

    public void cancelMember() {
        this.currentNum -= 1L;
    }

    public PostEditor.PostEditorBuilder toEditor() {
        return PostEditor.builder()
                .title(title)
                .body(body)
                .meetingDate(meetingDate)
                .deadLineDate(deadLineDate)
                .place(place)
                .price(price)
                .recruitNum(recruitNum)
                .meetingGender(meetingGender)
                .language(language)
                .category(category)
                .hashtag(hashtag)
                .chatLink(chatLink);
    }

    public void edit(PostEditor postEditor) {
        title = postEditor.getTitle();
        body = postEditor.getBody();
        meetingDate = postEditor.getMeetingDate();
        deadLineDate = postEditor.getDeadLineDate();
        place = postEditor.getPlace();
        price = postEditor.getPrice();
        recruitNum = postEditor.getRecruitNum();
        meetingGender = postEditor.getMeetingGender();
        language = postEditor.getLanguage();
        category = postEditor.getCategory();
        hashtag = postEditor.getHashtag();
        chatLink = postEditor.getChatLink();
    }


}
