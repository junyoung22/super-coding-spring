package com.github.supercoding.web.controller;

import com.github.supercoding.service.AuthService;
import com.github.supercoding.web.dto.auth.Login;
import com.github.supercoding.web.dto.auth.SignUp;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/api/sign")
public class SignController {
    private final AuthService authService;

    // 회원가입
    @PostMapping(value = "/register")
    public String register(@RequestBody SignUp signUpRequest){
        boolean isSuccess = authService.signUp(signUpRequest);
        return isSuccess ? "회원가입 성공하였습니다." : "회원가입 실패하였습니다.";
    }
    // 로그인
    @PostMapping(value = "/login")  // HttpServletResponse == 토큰을 response에 넣어서 진행할것이다.
    public String login(@RequestBody Login loginRequest, HttpServletResponse httpServletResponse){
        String token = authService.login(loginRequest);
        httpServletResponse.setHeader("X-AUTH-TOKEN", token);   // 헤더에 값을 넣는다.
        return "로그인이 성공하였습니다.";
    }

}
