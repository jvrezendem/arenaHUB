package com.arenahub.arenahub.dto;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public record QuadraUpdateDto(
        String nome,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm") LocalTime horaAbertura,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm") LocalTime horaFechamento,

        Boolean ativa) {

}
