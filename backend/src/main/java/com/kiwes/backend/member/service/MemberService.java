package com.kiwes.backend.member.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kiwes.backend.global.utils.SecurityUtil;
import com.kiwes.backend.jwt.service.JwtService;
import com.kiwes.backend.member.domain.Member;
import com.kiwes.backend.member.domain.MemberCreate;
import com.kiwes.backend.member.domain.MemberResponse;
import com.kiwes.backend.member.domain.kakao.KakaoProfile;
import com.kiwes.backend.member.domain.kakao.OAuthToken;
import com.kiwes.backend.member.exception.MemberException;
import com.kiwes.backend.member.exception.MemberExceptionType;
import com.kiwes.backend.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtService jwtService;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    String client_id;

    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    String client_secret;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    String redirect_uri;

    public OAuthToken getAccessToken(String code) {
        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", client_id);
        params.add("redirect_uri", redirect_uri);
        params.add("code", code);
        params.add("client_secret", client_secret);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(params, headers);

        ResponseEntity<String> accessTokenResponse = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oauthToken = null;
        try {
            oauthToken = objectMapper.readValue(accessTokenResponse.getBody(), OAuthToken.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return oauthToken;
    }

    public KakaoProfile findProfile(String token) {

        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest =
                new HttpEntity<>(headers);

        ResponseEntity<String> kakaoProfileResponse = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        KakaoProfile kakaoProfile = null;
        try {
            kakaoProfile = objectMapper.readValue(kakaoProfileResponse.getBody(), KakaoProfile.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return kakaoProfile;
    }

    public String saveMemberAndGetToken(String token) {
        KakaoProfile profile = findProfile(token);

        if(memberRepository.findByKakaoId(profile.getId()).isEmpty()) {
            Member member;
            member = Member.builder()
                    .kakaoId(profile.getId())
                    .profileImg(profile.getKakao_account().getProfile().getProfile_image_url())
                    .nickname(profile.getKakao_account().getProfile().getNickname())
                    .kakaoEmail(profile.getKakao_account().getEmail())
                    .memberToken(createMemberToken())
                    .build();

            if(memberRepository.findByMemberToken(member.getMemberToken()).isEmpty()) {
                memberRepository.save(member);
                member.updateRefreshToken(jwtService.createRefreshToken());
                return jwtService.createAccessToken(member);
            }else {
                throw new MemberException(MemberExceptionType.ALREADY_EXIST_USERNAME);
            }


        }else {
            Member member = memberRepository.findByNickname(profile.getProperties().getNickname()).orElseThrow();
            member.updateRefreshToken(jwtService.createRefreshToken());
            return jwtService.createAccessToken(member);
        }

    }

    public String createMemberToken() {
        Random random = new Random();
        int length = random.nextInt(5)+5;

        StringBuffer newWord = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int choice = random.nextInt(3);
            switch(choice) {
                case 0:
                    newWord.append((char)(random.nextInt(25) +97));
                    break;
                case 1:
                    newWord.append((char)(random.nextInt(25) +65));
                    break;
                case 2:
                    newWord.append((char)(random.nextInt(10) +48));
                    break;
                default:
                    break;
            }
        }
        return newWord.toString();
    }

    public String getMemberTokenByToken(String token) {
        KakaoProfile profile = findProfile(token);
        Member member = memberRepository.findByNickname(profile.getProperties().getNickname()).orElseThrow();

        return member.getMemberToken();
    }


    public void saveMember(MemberCreate memberCreate, MultipartFile multipartFile) { // s3 bucket 설정 이후 수정할것
        String loginUsername = SecurityUtil.getLoginUsername();
        Member member = memberRepository.findByKakaoId(Long.valueOf(loginUsername)).orElseThrow();
        member.updateMember(memberCreate);
    }

    public MemberResponse getMyInfo() {

        Member member = memberRepository.findByNickname((SecurityUtil.getLoginUsername()))
                .orElseThrow();

        return MemberResponse.builder()
                .memberId(member.getMemberId())
                .kakaoId(member.getKakaoId())
                .profileImg(member.getProfileImg())
                .nickname(member.getNickname())
                .memberToken(member.getMemberToken())
                .gender(member.getGender())
                .birth(member.getBirthday())
                .build();
    }





}
