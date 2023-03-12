package com.kiwes.backend.member.controller;

import com.kiwes.backend.member.domain.Member;
import com.kiwes.backend.member.domain.MemberCreate;
import com.kiwes.backend.member.domain.MemberResponse;
import com.kiwes.backend.member.domain.kakao.OAuthToken;
import com.kiwes.backend.member.repository.MemberRepository;
import com.kiwes.backend.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @GetMapping("/")
    public String start() {
        return "이도하 짱";
    }

    @GetMapping("/oauth/token")
    public ResponseEntity getLogin(@RequestParam("code") String code) {
        OAuthToken oAuthToken = memberService.getAccessToken(code);

        String jwtToken = memberService.saveMemberAndGetToken(oAuthToken.getAccess_token());

        String memberToken = memberService.getMemberTokenByToken(oAuthToken.getAccess_token());

        Member member = memberRepository.findByMemberToken(memberToken).orElseThrow();

        HttpHeaders headers = new HttpHeaders();
        headers.add("AccessToken", jwtToken);
        headers.add("RefreshToken", member.getRefreshToken());

        return ResponseEntity.ok().headers(headers).body(memberToken);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("member")
    public void completeLogin(@ModelAttribute MemberCreate memberCreate, @RequestPart(required = false)MultipartFile multipartFile) {
        memberService.saveMember(memberCreate, multipartFile);

    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/oauth/me")
    public MemberResponse getMyInfo(HttpServletResponse response) {
        MemberResponse memberResponse = memberService.getMyInfo();
        return memberResponse;
    }


}
