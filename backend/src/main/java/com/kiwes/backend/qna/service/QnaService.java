package com.kiwes.backend.qna.service;

import com.kiwes.backend.global.utils.SecurityUtil;
import com.kiwes.backend.member.domain.Member;
import com.kiwes.backend.member.repository.MemberRepository;
import com.kiwes.backend.post.domain.Post;
import com.kiwes.backend.post.repository.PostRepository;
import com.kiwes.backend.qna.domain.*;
import com.kiwes.backend.qna.repository.QnaRepository;
import com.kiwes.backend.reply.domain.Reply;
import com.kiwes.backend.reply.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class QnaService {

    private final QnaRepository qnaRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final ReplyRepository replyRepository;

    public void saveQna(QnaCreate qnaCreate) {
        Post post = postRepository.findById(qnaCreate.getPostId()).orElseThrow();
        Member loginMember = memberRepository.findByKakaoId(Long.parseLong(SecurityUtil.getLoginUsername())).orElseThrow();

        Qna qna = Qna.builder()
                .body(qnaCreate.getBody())
                .build();

        loginMember.addQna(qna);
        post.addQna(qna);
        qnaRepository.save(qna);
    }

    public List<QnaResponse> getQna(Long postId) {
        Member loginMember = memberRepository.findByKakaoId(Long.parseLong(SecurityUtil.getLoginUsername())).orElseThrow();
        Post post = postRepository.findById(postId).orElseThrow();
        List<Qna> qnaList = qnaRepository.findAllByPost(post);
        List<QnaResponse> result = new ArrayList<>();
        qnaList.forEach(qna ->
                result.add(QnaResponse.builder()
                        .qnaId(qna.getQnaId())
                        .body(qna.getBody())
                        .lastModifiedTime(qna.getLastModifiedTime())
                        .writerToken(qna.getWriter().getMemberToken())
                        .writerNickname(qna.getWriter().getNickname())
                        .writerProfileImg(qna.getWriter().getProfileImg())
                        .isWriter(Objects.equals(qna.getWriter().getMemberToken(), loginMember.getMemberToken()))
                        .hasReply(replyRepository.findByQna(qna).isPresent())
                        .build())
        );

        return result;
    }

    public List<QnaReplyResponse> getQnaMember(String memberToken) {
        Member loginMember = memberRepository.findByKakaoId(Long.parseLong(SecurityUtil.getLoginUsername())).orElseThrow();
        Member member = memberRepository.findByMemberToken(memberToken).orElseThrow();
        List<Qna> qnaList = qnaRepository.findAllByWriter(member);
        List<Reply> replyList = replyRepository.findAllByWriter(member);
        List<QnaReplyResponse> result = new ArrayList<>();
        qnaList.forEach(qna ->
                result.add(QnaReplyResponse.builder()
                        .id(qna.getQnaId())
                        .postId(qna.getPost().getPostId())
                        .body(qna.getBody())
                        .lastModifiedTime(qna.getLastModifiedTime())
                        .writerToken(qna.getWriter().getMemberToken())
                        .writerNickname(qna.getWriter().getNickname())
                        .writerProfileImg(qna.getWriter().getProfileImg())
                        .isWriter(Objects.equals(memberToken, loginMember.getMemberToken()))
                        .build())
        );

        replyList.forEach(reply ->
                result.add(QnaReplyResponse.builder()
                        .id(reply.getReplyId())
                        .postId(reply.getPost().getPostId())
                        .body(reply.getBody())
                        .lastModifiedTime(reply.getLastModifiedTime())
                        .writerToken(reply.getWriter().getMemberToken())
                        .writerNickname(reply.getWriter().getNickname())
                        .writerProfileImg(reply.getWriter().getProfileImg())
                        .isWriter(Objects.equals(memberToken, loginMember.getMemberToken()))
                        .build())
        );

        Comparator<QnaReplyResponse> comparator = Comparator.comparing(QnaReplyResponse::getLastModifiedTime);
        result.sort(comparator);

        return result;
    }

    public void editQna(Long qnaId, QnaEdit qnaEdit) throws Exception{
        Member loginMember = memberRepository.findByKakaoId(Long.parseLong(SecurityUtil.getLoginUsername())).orElseThrow();
        Qna qna = qnaRepository.findById(qnaId).orElseThrow();
        if(!Objects.equals(qna.getWriter().getMemberToken(), loginMember.getMemberToken())) {
            throw new Exception();
        }

        QnaEditor.QnaEditorBuilder editorBuilder = qna.toEditor();
        if(qnaEdit.getBody() != null) {
            editorBuilder.body(qnaEdit.getBody());
        }
        qna.edit(editorBuilder.build());
    }

    public void deleteQna(Long qnaId) throws Exception {
        Member loginMember = memberRepository.findByKakaoId(Long.parseLong(SecurityUtil.getLoginUsername())).orElseThrow();
        Qna qna = qnaRepository.findById(qnaId).orElseThrow();
        if(!Objects.equals(qna.getWriter().getMemberToken(), loginMember.getMemberToken())) {
            throw new Exception();
        }
        qnaRepository.delete(qna);
    }
}
