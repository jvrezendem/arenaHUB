package com.arenahub.arenahub.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.arenahub.arenahub.config.TokenProvider;
import com.arenahub.arenahub.database.models.Gestor;
import com.arenahub.arenahub.database.models.enums.UserRoles;
import com.arenahub.arenahub.database.repository.IGestorRepository;
import com.arenahub.arenahub.dto.request.LoginRequestDto;
import com.arenahub.arenahub.dto.request.RegisterRequestDto;
import com.arenahub.arenahub.dto.response.LoginResponseDto;
import com.arenahub.arenahub.exception.EmailJaCadastradoException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final IGestorRepository gestorRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

    @Value("${jwt.expiration}")
    private long expiration;

    public void registerGestor(RegisterRequestDto requestDto) {

        Gestor gestor = gestorRepository.findByEmail(requestDto.email());

        if (gestor != null) {
            throw new EmailJaCadastradoException(requestDto.email());
        }

        gestorRepository.save(
                Gestor.builder()
                        .nome(requestDto.nome())
                        .email(requestDto.email())
                        .senhaHash(passwordEncoder.encode(requestDto.senha()))
                        .role(UserRoles.GESTOR)
                        .criadoEm(LocalDateTime.now())
                        .build());
    }

    public LoginResponseDto loginGestor(LoginRequestDto requestDto) {
        Authentication auth = authenticationManager
                            .authenticate(new UsernamePasswordAuthenticationToken(requestDto.email(), requestDto.senha()));
        String token = tokenProvider.generateToken(auth);

        return new LoginResponseDto(token, expiration);
    }
}
