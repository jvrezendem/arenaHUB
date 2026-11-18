package com.arenahub.arenahub.dto.request;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto(

        @NotBlank(message = "Email é necessário") String email,

        @NotBlank(message = "Senha é necessario") String senha) {

}
