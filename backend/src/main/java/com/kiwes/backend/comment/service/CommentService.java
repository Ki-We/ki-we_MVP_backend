package com.kiwes.backend.comment.service;

import com.kiwes.backend.comment.domain.*;
import com.kiwes.backend.comment.repository.CommentRepository;
import com.kiwes.backend.global.utils.SecurityUtil;
import com.kiwes.backend.member.domain.Member;
import com.kiwes.backend.member.repository.MemberRepository;
import com.kiwes.backend.post.domain.Post;
import com.kiwes.backend.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public void saveComment(CommentCreate commentCreate) {
        Post post = postRepository.findById(commentCreate.getPostId()).orElseThrow();
        Member loginMember = memberRepository.findByKakaoId(Long.parseLong(SecurityUtil.getLoginUsername())).orElseThrow();

        Comment comment = Comment.builder()
                .body(commentCreate.getBody())
                .build();

        loginMember.addComment(comment);
        post.addComment(comment);
        commentRepository.save(comment);
    }

    public List<CommentResponse> getComment(Long postId) {
        Member loginMember = memberRepository.findByKakaoId(Long.parseLong(SecurityUtil.getLoginUsername())).orElseThrow();
        Post post = postRepository.findById(postId).orElseThrow();
        List<Comment> commentList = commentRepository.findAllByPost(post);
        List<CommentResponse> result = new ArrayList<>();
        commentList.forEach(comment ->
            result.add(CommentResponse.builder()
                    .commentId(comment.getCommentId())
                    .body(comment.getBody())
                    .lastModifiedTime(comment.getLastModifiedTime())
                    .writerToken(comment.getWriter().getMemberToken())
                    .writerNickname(comment.getWriter().getNickname())
                    .writerProfileImg(comment.getWriter().getProfileImg())
                    .isWriter(Objects.equals(comment.getWriter().getMemberToken(), loginMember.getMemberToken()))
                    .build())
        );

        return result;
    }

    public List<CommentResponse> getCommentMember(String memberToken) {
        Member loginMember = memberRepository.findByKakaoId(Long.parseLong(SecurityUtil.getLoginUsername())).orElseThrow();
        Member member = memberRepository.findByMemberToken(memberToken).orElseThrow();
        List<Comment> commentList = commentRepository.findAllByWriter(member);
        List<CommentResponse> result = new ArrayList<>();
        commentList.forEach(comment ->
                result.add(CommentResponse.builder()
                        .commentId(comment.getCommentId())
                        .body(comment.getBody())
                        .lastModifiedTime(comment.getLastModifiedTime())
                        .writerToken(comment.getWriter().getMemberToken())
                        .writerNickname(comment.getWriter().getNickname())
                        .writerProfileImg(comment.getWriter().getProfileImg())
                        .isWriter(Objects.equals(memberToken, loginMember.getMemberToken()))
                        .build())
        );

        return result;
    }

    public void editComment(Long commentId, CommentEdit commentEdit) throws Exception{
        Member loginMember = memberRepository.findByKakaoId(Long.parseLong(SecurityUtil.getLoginUsername())).orElseThrow();
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        if(!Objects.equals(comment.getWriter().getMemberToken(), loginMember.getMemberToken())) {
            throw new Exception();
        }

        CommentEditor.CommentEditorBuilder editorBuilder = comment.toEditor();
        if(commentEdit.getBody() != null) {
            editorBuilder.body(commentEdit.getBody());
        }
        comment.edit(editorBuilder.build());
    }

    public void deleteComment(Long commentId) throws Exception {
        Member loginMember = memberRepository.findByKakaoId(Long.parseLong(SecurityUtil.getLoginUsername())).orElseThrow();
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        if(!Objects.equals(comment.getWriter().getMemberToken(), loginMember.getMemberToken())) {
            throw new Exception();
        }
        commentRepository.delete(comment);
    }
}
