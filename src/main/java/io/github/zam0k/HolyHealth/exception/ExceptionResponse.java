package io.github.zam0k.HolyHealth.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
public class ExceptionResponse {
    private LocalDateTime timestamp;
    private List<String> message;
    private String detail;

    public ExceptionResponse(LocalDateTime timestamp, String message, String detail) {
        this.timestamp = timestamp;
        this.message = Arrays.asList(message);
        this.detail = detail;
    }
}
