package com.kiwes.backend.heart.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.kiwes.backend.heart.domain.Heart;
import com.kiwes.backend.heart.domain.HeartRequest;
import com.kiwes.backend.heart.repository.HeartRepository;
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
public class HeartService {

    private final HeartRepository heartRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    public void submitHeart(HeartRequest heartRequest) throws Exception{
        Member member = memberRepository.findById(heartRequest.getMemberId()).orElseThrow();
        Post post = postRepository.findById(heartRequest.getPostId()).orElseThrow();

        if(heartRepository.findByMemberAndPost(member, post).isPresent()) {
            throw new Exception();
        }

        Heart heart = Heart.builder()
                .member(member)
                .post(post)
                .build();

        heartRepository.save(heart);

    }

    public void deleteHeart(HeartRequest heartRequest) {
        Member member = memberRepository.findById(heartRequest.getMemberId())
                .orElseThrow(() -> new NotFoundException("Could not found member id : " + heartRequest.getMemberId()));

        Post post = postRepository.findById(heartRequest.getPostId())
                .orElseThrow(() -> new NotFoundException("Could not found board id : " + heartRequest.getPostId()));

        Heart heart = heartRepository.findByMemberAndPost(member, post)
                .orElseThrow(() -> new NotFoundException("Could not found heart id"));

        heartRepository.delete(heart);
    }
}
