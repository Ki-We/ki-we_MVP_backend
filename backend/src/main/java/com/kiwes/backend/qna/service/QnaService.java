package com.kiwes.backend.qna.service;

import com.kiwes.backend.global.utils.SecurityUtil;
import com.kiwes.backend.member.domain.Member;
import com.kiwes.backend.member.repository.MemberRepository;
import com.kiwes.backend.post.domain.Post;
import com.kiwes.backend.post.repository.PostRepository;
import com.kiwes.backend.qna.domain.Qna;
import com.kiwes.backend.qna.domain.QnaCreate;
import com.kiwes.backend.qna.domain.QnaResponse;
import com.kiwes.backend.qna.repository.QnaRepository;
import com.kiwes.backend.reply.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
}
