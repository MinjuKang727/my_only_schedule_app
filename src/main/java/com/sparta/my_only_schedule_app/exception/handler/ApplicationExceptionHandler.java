package com.sparta.my_only_schedule_app.exception.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.my_only_schedule_app.exception.CommonException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handlerInvalidArgument(MethodArgumentNotValidException e) throws JsonProcessingException {
        Map<String, String> errorMap = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(errorMap);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CommonException.class)
    public String handlerCommonException(CommonException e) throws JsonProcessingException {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorCode", e.getErrorCode().toString());
        errorMap.put("message", e.getMessage());
        errorMap.put("httpStatus", e.getHttpStatus().toString());
        errorMap.put("TimeStamp", e.getTimeStamp().toString());
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(errorMap);
    }
}
