package com.github.supercoding.web.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class LoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String method = request.getMethod();
        String uri = request.getRequestURI();

        log.info(method + uri + "요청이 들어왔습니다.");

        filterChain.doFilter(request,response); // 다음 필터 또는 서블릿으로 요청을 전달합니다.

        log.info(method + uri + "가 상태 " + response.getStatus() + " 로 응답이 나갑니다.");
    }
}
