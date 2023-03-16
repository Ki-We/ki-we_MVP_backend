package com.kiwes.backend.heart.controller;

import com.kiwes.backend.heart.domain.HeartRequest;
import com.kiwes.backend.heart.service.HeartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class HeartController {

    private final HeartService heartService;

    @PostMapping("/post/heart")
    @ResponseStatus(HttpStatus.OK)
    public void postHeart(@RequestBody HeartRequest heartRequest) throws Exception {
        heartService.submitHeart(heartRequest);
    }

    @DeleteMapping("/post/heart")
    @ResponseStatus(HttpStatus.OK)
    public void deleteHeart(@RequestBody HeartRequest heartRequest) {
        heartService.deleteHeart(heartRequest);
    }
}
