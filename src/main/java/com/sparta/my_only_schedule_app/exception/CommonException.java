package com.sparta.my_only_schedule_app.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Map;

@Getter
public class CommonException extends Exception {
    private ExceptionCode errorCode;
    private String message;
    private HttpStatus httpStatus;
    private LocalDateTime TimeStamp;

    public CommonException(ExceptionCode errorCode) {
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
        this.httpStatus = HttpStatus.BAD_REQUEST;
        this.TimeStamp = LocalDateTime.now(Clock.systemDefaultZone());
    }
}
