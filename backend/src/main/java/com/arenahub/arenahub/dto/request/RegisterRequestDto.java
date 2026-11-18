package com.arenahub.arenahub.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequestDto(

        @NotBlank(message = "Email é necessário") @Email(message = "Insira um email válido") String email,

        @NotBlank String nome,

        @NotBlank(message = "Senha é necessário") @Size(min = 8, message = "Senha com no mínimo 8 caracteres") @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).+$", message = "Senha deve conter: maiúscula, minúscula, número e caractere especial") String senha) {

}
