package com.kiwes.backend.reply.controller;

import com.kiwes.backend.qna.domain.QnaCreate;
import com.kiwes.backend.reply.domain.ReplyCreate;
import com.kiwes.backend.reply.domain.ReplyResponse;
import com.kiwes.backend.reply.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping("/reply")
    @ResponseStatus(HttpStatus.CREATED)
    public void createReply(@ModelAttribute ReplyCreate replyCreate) throws Exception {
        replyService.saveReply(replyCreate);
    }

    @GetMapping("/reply/{qnaId}")
    @ResponseStatus(HttpStatus.OK)
    public ReplyResponse getReply(@PathVariable Long qnaId) {
        return replyService.getReply(qnaId);
    }
}
