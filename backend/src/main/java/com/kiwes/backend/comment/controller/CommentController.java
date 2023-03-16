package com.kiwes.backend.comment.controller;

import com.kiwes.backend.comment.domain.CommentCreate;
import com.kiwes.backend.comment.domain.CommentResponse;
import com.kiwes.backend.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment")
    @ResponseStatus(HttpStatus.CREATED)
    public void createComment(@ModelAttribute CommentCreate commentCreate) {
        commentService.saveComment(commentCreate);
    }

    @GetMapping("/comment/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentResponse> getComment(@PathVariable Long postId) {
        return commentService.getComment(postId);
    }

    
}
