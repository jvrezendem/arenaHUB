package com.arenahub.arenahub.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arenahub.arenahub.dto.request.LoginRequestDto;
import com.arenahub.arenahub.dto.request.RegisterRequestDto;
import com.arenahub.arenahub.dto.response.LoginResponseDto;
import com.arenahub.arenahub.service.AuthenticationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("registerG")
    public ResponseEntity<String> registerGestor(@RequestBody @Valid RegisterRequestDto registerRequestDto) {
        authenticationService.registerGestor(registerRequestDto);
        return new ResponseEntity<>("Gestor cadastrado com sucesso!", HttpStatus.OK);
    }

    @PostMapping("loginG")
    public ResponseEntity<LoginResponseDto> loginGestor(@RequestBody @Valid LoginRequestDto loginRequestDto) {
        LoginResponseDto login = authenticationService.loginGestor(loginRequestDto);
        return new ResponseEntity<>(login, HttpStatus.OK);
    }
}
