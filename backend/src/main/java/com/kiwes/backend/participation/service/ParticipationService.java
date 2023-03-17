package com.kiwes.backend.participation.service;

import com.kiwes.backend.participation.domain.Participation;
import com.kiwes.backend.participation.domain.ParticipationCreate;
import com.kiwes.backend.participation.repository.ParticipationRepository;
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
public class ParticipationService {

    private final ParticipationRepository participationRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    public void submitParticipation(ParticipationCreate joinCreate) throws Exception{
        Member member = memberRepository.findById(joinCreate.getMemberId()).orElseThrow();
        Post post = postRepository.findById(joinCreate.getPostId()).orElseThrow();

        if(participationRepository.findByMemberAndPost(member, post).isPresent()) {
            throw new Exception();
        }

        Participation join = Participation.builder()
                .member(member)
                .post(post)
                .build();

        participationRepository.save(join);
    }
}
