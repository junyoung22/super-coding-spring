package com.github.supercoding.web.direction;

import com.github.supercoding.service.exceptions.CAuthenticationEntryPointException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("/exceptions")
public class ExceptionController {

    @GetMapping(value = "/entrypoint")
    public void entrypointException() {
        throw new CAuthenticationEntryPointException();
    }
    @GetMapping(value = "/access-denied")
    public void accessdeniedException() {
        throw new AccessDeniedException("");
    }
}
