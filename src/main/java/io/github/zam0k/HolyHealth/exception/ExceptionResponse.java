package io.github.zam0k.HolyHealth.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ExceptionResponse {
    private LocalDateTime timestamp;
    private String message;
    private String detail;
}
