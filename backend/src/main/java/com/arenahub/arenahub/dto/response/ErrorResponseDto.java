package com.arenahub.arenahub.dto.response;

import java.time.LocalDateTime;

public record ErrorResponseDto( 
        LocalDateTime timestamp,
        int status,
        String erro,
        String msg
    )
{}
