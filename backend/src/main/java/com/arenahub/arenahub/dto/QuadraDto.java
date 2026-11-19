package com.arenahub.arenahub.dto;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record QuadraDto(

        @NotBlank(message = "O nome é necessário") String nome,

        @NotNull(message = "A hora de abertura é necessária") @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm") LocalTime horaAbertura,

        @NotNull(message = "A hora de fechamento é necessária") @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm") LocalTime horaFechamento,

        @NotNull(message = "O status é necessário") boolean ativa

) {

}
