package com.kiwes.backend.participation.controller;

import com.kiwes.backend.participation.domain.ParticipationCreate;
import com.kiwes.backend.participation.service.ParticipationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ParticipationController {

    private final ParticipationService joinService;

    @PostMapping("/post/join")
    @ResponseStatus(HttpStatus.OK)
    public void postParticipation(@RequestBody ParticipationCreate participationCreate) throws Exception{
        joinService.submitParticipation(participationCreate);
    }

}
