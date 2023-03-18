package com.kiwes.backend.post.controller;

import com.kiwes.backend.post.domain.PostCreate;
import com.kiwes.backend.post.domain.PostEdit;
import com.kiwes.backend.post.domain.PostResponse;
import com.kiwes.backend.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/post")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@ModelAttribute PostCreate postCreate, @RequestPart(required = false)MultipartFile multipartFile) {
        postService.savePost(postCreate, multipartFile);
    }

    @GetMapping("/post/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public PostResponse getPost(@PathVariable Long postId) {
        return postService.getPost(postId);
    }

    @GetMapping("/posts")
    @ResponseStatus(HttpStatus.OK)
    public List<PostResponse> getPostList() {
        return postService.getPostList();
    }

    @GetMapping("/posts/{memberToken}")
    public List<PostResponse> getPostListMember(@PathVariable String memberToken) {
        return postService.getPostListMember(memberToken);
    }

    @GetMapping("/posts/recommend")
    public List<PostResponse> getRecommendList() {
        return postService.getRecommendPost();
    }

    @GetMapping("/posts/{category}")
    public List<PostResponse> getFilter(@PathVariable String category) {
        return postService.getFilterPost(category);
    }

    @GetMapping("/posts/{keyword}")
    public List<PostResponse> getSearch(@PathVariable String keyword) {
        return postService.getSearchPost(keyword);
    }

    @PatchMapping("/post/{postId}")
    public void editPost(@PathVariable Long postId, @ModelAttribute PostEdit postEdit, @RequestPart(required = false) MultipartFile multipartFile) throws Exception {
        postService.edit(postId, postEdit, multipartFile);
    }

    @DeleteMapping("/post/{postId}")
    public void deletePost(@PathVariable Long postId) throws Exception {
        postService.deletePost(postId);
    }

}
