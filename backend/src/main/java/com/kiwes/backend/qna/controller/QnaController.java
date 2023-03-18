package com.kiwes.backend.qna.controller;

import com.kiwes.backend.qna.domain.QnaCreate;
import com.kiwes.backend.qna.domain.QnaEdit;
import com.kiwes.backend.qna.domain.QnaReplyResponse;
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
    public void createQna(@RequestBody QnaCreate qnaCreate) {
        qnaService.saveQna(qnaCreate);
    }

    @GetMapping("/qna/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public List<QnaResponse> getQna(@PathVariable Long postId) {
        return qnaService.getQna(postId);
    }
    
    @GetMapping("/qna/{memberToken}")
    public List<QnaReplyResponse> getQnaReply(@PathVariable String memberToken) {
        return qnaService.getQnaMember(memberToken);
    }

    @PatchMapping("/qna/{qnaId}")
    public void editQna(@PathVariable Long qnaId, @RequestBody QnaEdit qnaEdit) throws Exception {
        qnaService.editQna(qnaId, qnaEdit);
    }

    @DeleteMapping("/qna/{qnaId}")
    public void deleteQna(@PathVariable Long qnaId) throws Exception{
        qnaService.deleteQna(qnaId);
    }
}
