package com.kiwes.backend.post.service;

import com.kiwes.backend.global.service.S3Uploader;
import com.kiwes.backend.global.utils.SecurityUtil;
import com.kiwes.backend.member.domain.Member;
import com.kiwes.backend.member.repository.MemberRepository;
import com.kiwes.backend.post.domain.Post;
import com.kiwes.backend.post.domain.PostCreate;
import com.kiwes.backend.post.domain.PostResponse;
import com.kiwes.backend.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class PostService {
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final S3Uploader s3Uploader;

    public void savePost(PostCreate postCreate, MultipartFile multipartFile) {
        Post post = Post.builder()
                .title(postCreate.getTitle())
                .body(postCreate.getBody())
                .meetingDate(postCreate.getMeetingDate())
                .deadLineDate(postCreate.getDeadLineDate())
                .place(postCreate.getPlace())
                .price(postCreate.getPrice())
                .recruitNum(postCreate.getRecruitNum())
                .meetingGender(postCreate.getMeetingGender())
                .language(postCreate.getLanguage())
                .category(postCreate.getCategory())
                .hashtag(postCreate.getHashtag())
                .chatLink(postCreate.getChatLink())
                .build();

        Member member = memberRepository.findByKakaoId(Long.parseLong(SecurityUtil.getLoginUsername())).orElseThrow();
        post.confirmHost(member);

        if(!multipartFile.isEmpty()) {
            post.updatePostImage(s3Uploader.getThumbnailPath(s3Uploader.uploadImage(multipartFile)));
        }
        postRepository.save(post);
    }

    public PostResponse getPost(Long postId) {
        Member loginMember = memberRepository.findByKakaoId(Long.parseLong(SecurityUtil.getLoginUsername())).orElseThrow();
        Post post = postRepository.findById(postId).orElseThrow();
        PostResponse.builder()
                .title(post.getTitle())
                .body(post.getBody())
                .meetingDate(post.getMeetingDate())
                .deadLineDate(post.getDeadLineDate())
                .place(post.getPlace())
                .price(post.getPrice())
                .recruitNum(post.getRecruitNum())
                .meetingGender(post.getMeetingGender())
                .language(post.getLanguage())
                .category(post.getCategory())
                .hashtag(post.getHashtag())
                .chatLink(post.getChatLink())
                .hostId(post.getHost().getMemberId())
                .hostProfileImg(post.getHost().getProfileImg())




    }
}
