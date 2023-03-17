package com.kiwes.backend.reply.service;

import com.kiwes.backend.global.utils.SecurityUtil;
import com.kiwes.backend.member.domain.Member;
import com.kiwes.backend.member.repository.MemberRepository;
import com.kiwes.backend.post.domain.Post;
import com.kiwes.backend.post.repository.PostRepository;
import com.kiwes.backend.qna.domain.Qna;
import com.kiwes.backend.qna.repository.QnaRepository;
import com.kiwes.backend.reply.domain.Reply;
import com.kiwes.backend.reply.domain.ReplyCreate;
import com.kiwes.backend.reply.domain.ReplyResponse;
import com.kiwes.backend.reply.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final QnaRepository qnaRepository;
    private final ReplyRepository replyRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    public void saveReply(ReplyCreate replyCreate) throws Exception {
        Qna qna = qnaRepository.findById(replyCreate.getQnaId()).orElseThrow();
        Post post = postRepository.findById(replyCreate.getPostId()).orElseThrow();
        Member loginMember = memberRepository.findByKakaoId(Long.parseLong(SecurityUtil.getLoginUsername())).orElseThrow();

        if(!Objects.equals(loginMember.getMemberToken(), post.getHost().getMemberToken())) {
            throw new Exception();
        }

        Reply reply = Reply.builder()
                .body(replyCreate.getBody())
                .build();

        qna.addReply(reply);
        post.addReply(reply);
        loginMember.addReply(reply);

        replyRepository.save(reply);

    }

    public ReplyResponse getReply(Long qnaId) {
        Qna qna = qnaRepository.findById(qnaId).orElseThrow();
        Reply reply = replyRepository.findByQna(qna).orElseThrow();
        Member loginMember = memberRepository.findByKakaoId(Long.parseLong(SecurityUtil.getLoginUsername())).orElseThrow();

        ReplyResponse result = ReplyResponse.builder()
                .replyId(reply.getReplyId())
                .body(reply.getBody())
                .lastModifiedTime(reply.getLastModifiedTime())
                .isWriter(Objects.equals(loginMember.getMemberToken(), reply.getWriter().getMemberToken()))
                .build();

        return result;

    }



}
