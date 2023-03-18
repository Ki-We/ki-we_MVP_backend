package com.kiwes.backend.post.service;

import com.kiwes.backend.comment.repository.CommentRepository;
import com.kiwes.backend.global.service.S3Uploader;
import com.kiwes.backend.global.utils.SecurityUtil;
import com.kiwes.backend.heart.repository.HeartRepository;
import com.kiwes.backend.participation.repository.ParticipationRepository;
import com.kiwes.backend.member.domain.Member;
import com.kiwes.backend.member.repository.MemberRepository;
import com.kiwes.backend.post.domain.*;
import com.kiwes.backend.post.repository.PostRepository;
import com.kiwes.backend.qna.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PostService {
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final HeartRepository heartRepository;
    private final ParticipationRepository participationRepository;
    private final CommentRepository commentRepository;
    private final QnaRepository qnaRepository;
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
                .viewCount(0L)
                .currentNum(1L)
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
        post.upperView();
        return PostResponse.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .body(post.getBody())
                .meetingDate(post.getMeetingDate())
                .deadLineDate(post.getDeadLineDate())
                .place(post.getPlace())
                .price(post.getPrice())
                .recruitNum(post.getRecruitNum())
                .currentNum(post.getCurrentNum())
                .meetingGender(post.getMeetingGender())
                .language(post.getLanguage())
                .category(post.getCategory())
                .hashtag(post.getHashtag())
                .chatLink(post.getChatLink())
                .hostId(post.getHost().getMemberId())
                .hostProfileImg(post.getHost().getProfileImg())
                .isHost(Objects.equals(loginMember.getMemberId(), post.getHost().getMemberId()))
                .hasLike(heartRepository.findByMemberAndPost(loginMember, post).isPresent())
                .hasJoin(participationRepository.findByMemberAndPost(loginMember, post).isPresent())
                .hasComment(commentRepository.findByPost(post).isPresent())
                .hasQna(qnaRepository.findByPost(post).isPresent())
                .build();
    }

    public List<PostResponse> getPostList() {
        Member loginMember = memberRepository.findByKakaoId(Long.parseLong(SecurityUtil.getLoginUsername())).orElseThrow();
        List<Post> postList = postRepository.findAll();
        List<PostResponse> result = new ArrayList<>();
        postList.forEach(post -> {
            result.add(
                    PostResponse.builder()
                            .postId(post.getPostId())
                            .title(post.getTitle())
                            .body(post.getBody())
                            .meetingDate(post.getMeetingDate())
                            .deadLineDate(post.getDeadLineDate())
                            .place(post.getPlace())
                            .price(post.getPrice())
                            .recruitNum(post.getRecruitNum())
                            .currentNum(post.getCurrentNum())
                            .meetingGender(post.getMeetingGender())
                            .language(post.getLanguage())
                            .category(post.getCategory())
                            .hashtag(post.getHashtag())
                            .chatLink(post.getChatLink())
                            .hostId(post.getHost().getMemberId())
                            .hostProfileImg(post.getHost().getProfileImg())
                            .isHost(Objects.equals(loginMember.getMemberId(), post.getHost().getMemberId()))
                            .hasLike(heartRepository.findByMemberAndPost(loginMember, post).isPresent())
                            .hasJoin(participationRepository.findByMemberAndPost(loginMember, post).isPresent())
                            .hasComment(commentRepository.findByPost(post).isPresent())
                            .hasQna(qnaRepository.findByPost(post).isPresent())
                            .build()
            );
        });

        return result;
    }

    public List<PostResponse> getPostListMember(String memberToken) {
        Member loginMember = memberRepository.findByKakaoId(Long.parseLong(SecurityUtil.getLoginUsername())).orElseThrow();
        Member member = memberRepository.findByMemberToken(memberToken).orElseThrow();
        List<Post> postList = postRepository.findAllByHost(member);
        List<PostResponse> result = new ArrayList<>();
        postList.forEach(post -> {
            result.add(
                    PostResponse.builder()
                            .postId(post.getPostId())
                            .title(post.getTitle())
                            .body(post.getBody())
                            .meetingDate(post.getMeetingDate())
                            .deadLineDate(post.getDeadLineDate())
                            .place(post.getPlace())
                            .price(post.getPrice())
                            .recruitNum(post.getRecruitNum())
                            .currentNum(post.getCurrentNum())
                            .meetingGender(post.getMeetingGender())
                            .language(post.getLanguage())
                            .category(post.getCategory())
                            .hashtag(post.getHashtag())
                            .chatLink(post.getChatLink())
                            .hostId(post.getHost().getMemberId())
                            .hostProfileImg(post.getHost().getProfileImg())
                            .isHost(Objects.equals(loginMember.getMemberToken(), memberToken))
                            .hasLike(heartRepository.findByMemberAndPost(loginMember, post).isPresent())
                            .hasJoin(participationRepository.findByMemberAndPost(loginMember, post).isPresent())
                            .hasComment(commentRepository.findByPost(post).isPresent())
                            .hasQna(qnaRepository.findByPost(post).isPresent())
                            .build()
            );
        });

        return result;
    }

    public List<PostResponse> getRecommendPost() {
        Member loginMember = memberRepository.findByKakaoId(Long.parseLong(SecurityUtil.getLoginUsername())).orElseThrow();
        List<Post> postList = postRepository.recommendPost();
        List<PostResponse> result = new ArrayList<>();
        postList.forEach(post -> {
            result.add(
                    PostResponse.builder()
                            .postId(post.getPostId())
                            .title(post.getTitle())
                            .body(post.getBody())
                            .meetingDate(post.getMeetingDate())
                            .deadLineDate(post.getDeadLineDate())
                            .place(post.getPlace())
                            .price(post.getPrice())
                            .recruitNum(post.getRecruitNum())
                            .currentNum(post.getCurrentNum())
                            .meetingGender(post.getMeetingGender())
                            .language(post.getLanguage())
                            .category(post.getCategory())
                            .hashtag(post.getHashtag())
                            .chatLink(post.getChatLink())
                            .hostId(post.getHost().getMemberId())
                            .hostProfileImg(post.getHost().getProfileImg())
                            .isHost(Objects.equals(loginMember.getMemberToken(), post.getHost().getMemberToken()))
                            .hasLike(heartRepository.findByMemberAndPost(loginMember, post).isPresent())
                            .hasJoin(participationRepository.findByMemberAndPost(loginMember, post).isPresent())
                            .hasComment(commentRepository.findByPost(post).isPresent())
                            .hasQna(qnaRepository.findByPost(post).isPresent())
                            .build()
            );
        });

        return result;
    }

    public List<PostResponse> getSearchPost(String keyword) {
        Member loginMember = memberRepository.findByKakaoId(Long.parseLong(SecurityUtil.getLoginUsername())).orElseThrow();
        List<Post> postList = postRepository.filterPostByKeyword(keyword);
        List<PostResponse> result = new ArrayList<>();
        postList.forEach(post -> {
            result.add(
                    PostResponse.builder()
                            .postId(post.getPostId())
                            .title(post.getTitle())
                            .body(post.getBody())
                            .meetingDate(post.getMeetingDate())
                            .deadLineDate(post.getDeadLineDate())
                            .place(post.getPlace())
                            .price(post.getPrice())
                            .recruitNum(post.getRecruitNum())
                            .currentNum(post.getCurrentNum())
                            .meetingGender(post.getMeetingGender())
                            .language(post.getLanguage())
                            .category(post.getCategory())
                            .hashtag(post.getHashtag())
                            .chatLink(post.getChatLink())
                            .hostId(post.getHost().getMemberId())
                            .hostProfileImg(post.getHost().getProfileImg())
                            .isHost(Objects.equals(loginMember.getMemberToken(), post.getHost().getMemberToken()))
                            .hasLike(heartRepository.findByMemberAndPost(loginMember, post).isPresent())
                            .hasJoin(participationRepository.findByMemberAndPost(loginMember, post).isPresent())
                            .hasComment(commentRepository.findByPost(post).isPresent())
                            .hasQna(qnaRepository.findByPost(post).isPresent())
                            .build()
            );
        });

        return result;
    }

    public List<PostResponse> getFilterPost(String category) {
        Member loginMember = memberRepository.findByKakaoId(Long.parseLong(SecurityUtil.getLoginUsername())).orElseThrow();
        List<Post> postList = postRepository.filterPostByKeyword(category);
        List<PostResponse> result = new ArrayList<>();
        postList.forEach(post -> {
            result.add(
                    PostResponse.builder()
                            .postId(post.getPostId())
                            .title(post.getTitle())
                            .body(post.getBody())
                            .meetingDate(post.getMeetingDate())
                            .deadLineDate(post.getDeadLineDate())
                            .place(post.getPlace())
                            .price(post.getPrice())
                            .recruitNum(post.getRecruitNum())
                            .currentNum(post.getCurrentNum())
                            .meetingGender(post.getMeetingGender())
                            .language(post.getLanguage())
                            .category(post.getCategory())
                            .hashtag(post.getHashtag())
                            .chatLink(post.getChatLink())
                            .hostId(post.getHost().getMemberId())
                            .hostProfileImg(post.getHost().getProfileImg())
                            .isHost(Objects.equals(loginMember.getMemberToken(), post.getHost().getMemberToken()))
                            .hasLike(heartRepository.findByMemberAndPost(loginMember, post).isPresent())
                            .hasJoin(participationRepository.findByMemberAndPost(loginMember, post).isPresent())
                            .hasComment(commentRepository.findByPost(post).isPresent())
                            .hasQna(qnaRepository.findByPost(post).isPresent())
                            .build()
            );
        });

        return result;
    }

    public void edit(Long postId, PostEdit postEdit, MultipartFile multipartFile) throws Exception {
        Member loginMember = memberRepository.findByKakaoId(Long.parseLong(SecurityUtil.getLoginUsername())).orElseThrow();
        Post post = postRepository.findById(postId).orElseThrow();
        if(!Objects.equals(post.getHost().getMemberToken(), loginMember.getMemberToken())) {
            throw new Exception();
        }

        PostEditor.PostEditorBuilder editorBuilder = post.toEditor();
        if(postEdit.getTitle() != null) {
            editorBuilder.title(postEdit.getTitle());
        }
        if(postEdit.getBody() != null) {
            editorBuilder.body(postEdit.getBody());
        }
        if(postEdit.getMeetingDate() != null) {
            editorBuilder.meetingDate(postEdit.getMeetingDate());
        }
        if(postEdit.getDeadLineDate() != null) {
            editorBuilder.deadLineDate(postEdit.getDeadLineDate());
        }
        if(postEdit.getPlace() != null) {
            editorBuilder.place(postEdit.getPlace());
        }
        if(postEdit.getPrice() != null) {
            editorBuilder.price(postEdit.getPrice());
        }
        if(postEdit.getRecruitNum() != null) {
            editorBuilder.recruitNum(postEdit.getRecruitNum());
        }
        if(postEdit.getMeetingGender() != null) {
            editorBuilder.meetingGender(postEdit.getMeetingGender());
        }
        if(postEdit.getLanguage() != null) {
            editorBuilder.language(postEdit.getLanguage());
        }
        if(postEdit.getCategory() != null) {
            editorBuilder.category(postEdit.getCategory());
        }
        if(postEdit.getHashtag() != null) {
            editorBuilder.hashtag(postEdit.getHashtag());
        }
        if(postEdit.getChatLink() != null) {
            editorBuilder.chatLink(postEdit.getChatLink());
        }

        if(!multipartFile.isEmpty()) {
            post.updatePostImage(s3Uploader.getThumbnailPath(s3Uploader.uploadImage(multipartFile)));
        }

        post.edit(editorBuilder.build());
    }

    public void deletePost(Long postId) throws Exception {
        Member loginMember = memberRepository.findByKakaoId(Long.parseLong(SecurityUtil.getLoginUsername())).orElseThrow();
        Post post = postRepository.findById(postId).orElseThrow();
        if(!Objects.equals(post.getHost().getMemberToken(), loginMember.getMemberToken())) {
            throw new Exception();
        }
        postRepository.delete(post);
    }
}
