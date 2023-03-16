package com.kiwes.backend.join.service;

import com.kiwes.backend.join.domain.Join;
import com.kiwes.backend.join.domain.JoinCreate;
import com.kiwes.backend.join.repository.JoinRepository;
import com.kiwes.backend.member.domain.Member;
import com.kiwes.backend.member.repository.MemberRepository;
import com.kiwes.backend.post.domain.Post;
import com.kiwes.backend.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class JoinService {

    private final JoinRepository joinRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    public void submitJoin(JoinCreate joinCreate) throws Exception{
        Member member = memberRepository.findById(joinCreate.getMemberId()).orElseThrow();
        Post post = postRepository.findById(joinCreate.getPostId()).orElseThrow();

        if(joinRepository.findByMemberAndPost(member, post).isPresent()) {
            throw new Exception();
        }

        Join join = Join.builder()
                .member(member)
                .post(post)
                .build();

        joinRepository.save(join);
    }
}
