package com.example.finalprojectdtomarket.user;

import com.example.finalprojectdtomarket._core.errors.exception.UkException;
import com.example.finalprojectdtomarket._core.util.ApiUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
    private final HttpSession session;

    // http://localhost:8080/oauth/callback?code=3u9fk
    @GetMapping("/oauth/callback")
    public String oauthCallback(String code) {
        System.out.println("콜백 " + code);
        User sessionUser = userService.kakaoLogin(code);
        session.setAttribute("sessionUser", sessionUser);
        return "redirect:/";
    }

    // 회원가입
    @PostMapping("/join")
    public String join(UserRequest.JoinDTO reqDTO) {
//        System.out.println("age 받나요? " + reqDTO);
        userService.joinUser(reqDTO);
        return "redirect:/login-form";
    }

    @GetMapping("/join-form")
    public String joinForm() {
        session.setAttribute("role", 2);
        return "user/join-form";
    }

    //회원가입 중복체크
    @GetMapping("/api/username-same-check")
    public @ResponseBody ApiUtil<?> usernameSameCheck(@RequestParam("username") String username) {
        User user = userService.getUsername(username);
        if (user == null) { // 회원가입 해도 된다.
            return new ApiUtil<>(true);
        } else { // 회원가입 하면 안된다.
            throw new UkException();
        }
    }

    // 로그인
    @PostMapping("/login")
    public String login(UserRequest.LoginDTO requestDTO) {
        UserResponse.LoginDTO sessionUser = userService.login(requestDTO);
        session.setAttribute("sessionUser", sessionUser.getUser());
        session.setAttribute("isCheck", sessionUser.getIsCheck());

        return "redirect:/";
    }

    @GetMapping("/login-form")
    public String loginForm() {
        return "user/login-form";
    }


    // 회원 정보 수정
    @PostMapping("/user/update")
    public String update(UserRequest.UpdateDTO reqDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        User newSessionUser = userService.findById(sessionUser.getId(), reqDTO);
        session.setAttribute("sessionUser", newSessionUser);
        return "redirect:/";
    }

    @GetMapping("/user/update-form")
    public String updateForm(HttpServletRequest request, UserRequest.UpdateDTO reqDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        User user = userService.findById(sessionUser.getId(), reqDTO);
        request.setAttribute("user", user);
        return "user/update-form";
    }

    // 로그아웃
    @GetMapping("/logout")
    public String logout() {
        session.invalidate();
        return "redirect:/";
    }
}
