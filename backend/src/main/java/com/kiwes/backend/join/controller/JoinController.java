package com.kiwes.backend.join.controller;

import com.kiwes.backend.heart.domain.HeartRequest;
import com.kiwes.backend.join.domain.JoinCreate;
import com.kiwes.backend.join.service.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class JoinController {

    private final JoinService joinService;

    @PostMapping("/post/join")
    @ResponseStatus(HttpStatus.OK)
    public void postJoin(@RequestBody JoinCreate joinCreate) throws Exception{
        joinService.submitJoin(joinCreate);
    }

}
