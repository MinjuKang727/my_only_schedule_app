package com.sparta.my_only_schedule_app.exception.handler;

import com.sparta.my_only_schedule_app.dto.response.ScheduleResponseDto;
import com.sparta.my_only_schedule_app.exception.CommonException;
import com.sparta.my_only_schedule_app.exception.ExceptionCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> hndlerInvalidArgument(MethodArgumentNotValidException e) {
        Map<String, String> errorMap = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });
        return errorMap;
    }

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<CommonException> handleCommonException(CommonException e) {
        return new ResponseEntity<>(e, e.getHttpStatus());
    }
}
