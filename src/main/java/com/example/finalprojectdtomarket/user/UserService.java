package com.example.finalprojectdtomarket.user;


import com.example.finalprojectdtomarket._core.errors.exception.LoginFailException;
import com.example.finalprojectdtomarket._core.errors.exception.UserExistException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserJPARepository userRepo;

    //로그인 용
    public UserResponse.LoginDTO login(UserRequest.LoginDTO requestDTO) {
        User sessionUser = userRepo.findByUsernameAndPassword(requestDTO.getUsername(), requestDTO.getPassword())
                .orElseThrow(() -> new LoginFailException());

        //sessionUser.getRole 에서 2가 들어오면 true (2면 유저) 유저냐? 예스예스
        Boolean isCheck = sessionUser.getRole() == 2;

        return new UserResponse.LoginDTO(sessionUser, isCheck);

    }

    //회원정보 수정 페이지 뷰가 없음
    //회원정보수정
    public User findById(int id, UserRequest.UpdateDTO reqDTO) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new LoginFailException());
        user.setPassword(reqDTO.getPassword());
        return user; //더티체킹

    }

    // 회원가입 중복체크
    public User getUsername(String username) {
        return userRepo.findByUsername(username);
    }


    @Transactional
    public UserResponse.JoinDTO joinUser(UserRequest.JoinDTO reqDTO) {
        User user = userRepo.save(reqDTO.toEntity());
        user.setRole(2);
        userRepo.save(user);
        return new UserResponse.JoinDTO(user);
    }

    // user에서 쓰는 거 아님
    public User findUserId(Integer id) {

        System.out.println("10. UserService에 findUserId 실행함");

        User user = userRepo.findById(id)
                .orElseThrow(() -> new UserExistException());

        return user;
    }

    public User kakaoLogin(String code) {
        // 1.1 RestTemplate 설정
        RestTemplate rt = new RestTemplate();


        // 1.2 http header 설정
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // 1.3 http body 설정
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "a09e968e05cf3368fa5d3c415043fcab");
        body.add("redirect_uri", "http://localhost:8080/oauth/callback");
        body.add("code", code);

        // 1.4 body+header 객체 만들기
        HttpEntity<MultiValueMap<String, String>> request =
                new HttpEntity<>(body, headers);

        // 1.5 api 요청하기 (토큰 받기)
        ResponseEntity<KakaoResponse.TokenDTO> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                request,
                KakaoResponse.TokenDTO.class);

        // 1.6 값 확인
        System.out.println(response.getBody().toString());

        // 2. 토큰으로 사용자 정보 받기 (PK, Email)
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        headers2.add("Authorization", "Bearer "+response.getBody().getAccessToken());

        HttpEntity<MultiValueMap<String, String>> request2 =
                new HttpEntity<>(headers2);

        ResponseEntity<KakaoResponse.KakaoUserDTO> response2 = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.GET,
                request2,
                KakaoResponse.KakaoUserDTO.class);

        System.out.println("response2 : "+response2.getBody().toString());

        return null;
    }
}
