package com.kiwes.backend.member.domain;

import com.kiwes.backend.comment.domain.Comment;
import com.kiwes.backend.post.domain.Post;
import com.kiwes.backend.qna.domain.Qna;
import com.kiwes.backend.reply.domain.Reply;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    private Long kakaoId;

    private String profileImg;

    private String kakaoEmail;

    private String birthday;

    private String gender;

    private String nickname;

    private String category;

    private String language;

    private Boolean privacyAgreement;

    private Boolean termAgreement;

    private String memberIntro;

    private String memberToken;

    private String email;

    @Column(length = 1000)
    private String refreshToken;

    @CreationTimestamp
    private Timestamp createTime;

    @UpdateTimestamp
    private Timestamp lastModifiedTime;

    @OneToMany(mappedBy = "host", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Post> postList = new ArrayList<>();

    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Qna> qnaList = new ArrayList<>();

    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Reply> replyList = new ArrayList<>();

    //== 연관 관계 메서드 ==//
    public void addPost(Post post) {
        postList.add(post);
    }

    public void addComment(Comment comment) {
        commentList.add(comment);
    }

    public void addQna(Qna qna) {
        qnaList.add(qna);
    }

    public void addReply(Reply reply) {
        replyList.add(reply);
    }



    //== 정보 수정용 메서드 ==//
    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void updateProfileImage(String kakaoProfileImg) {
        this.profileImg = kakaoProfileImg;
    }

    public void updateMember(MemberCreate memberCreate) {
        this.nickname = memberCreate.getNickname();
        this.gender = memberCreate.getGender();
        this.birthday = memberCreate.getBirthday();
        this.email = memberCreate.getEmail();
    }



}
