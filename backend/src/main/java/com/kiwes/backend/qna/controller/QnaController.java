package com.kiwes.backend.qna.controller;

import com.kiwes.backend.qna.domain.QnaCreate;
import com.kiwes.backend.qna.domain.QnaResponse;
import com.kiwes.backend.qna.service.QnaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class QnaController {

    private final QnaService qnaService;

    @PostMapping("/qna")
    @ResponseStatus(HttpStatus.CREATED)
    public void createQna(@ModelAttribute QnaCreate qnaCreate) {
        qnaService.saveQna(qnaCreate);
    }

    @GetMapping("/qna/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public List<QnaResponse> getQna(@PathVariable Long postId) {
        return qnaService.getQna(postId);
    }
}
