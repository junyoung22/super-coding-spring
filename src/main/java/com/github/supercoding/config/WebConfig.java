package com.github.supercoding.config;

import com.github.supercoding.web.interceptors.RequestTimeLoggingInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final RequestTimeLoggingInterceptor requestTimeLoggingInterceptor;

    @Override       // 인터셉터 레지스터에 우리것 추가
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestTimeLoggingInterceptor);
    }
}
