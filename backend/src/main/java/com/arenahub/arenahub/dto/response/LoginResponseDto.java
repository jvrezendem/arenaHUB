package com.arenahub.arenahub.dto.response;

public record LoginResponseDto(

        String token,

        long expiration) {

}
