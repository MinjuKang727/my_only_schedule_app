package com.sparta.my_only_schedule_app.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Getter
public class CommonException extends Exception {
    private ExceptionCode errorValueType;
    private long errorLongValue;
    private String errorStrValue;
    private ExceptionCode errorCode;
    private String message;
    private HttpStatus httpStatus;
    private Date TimeStamp;

    public CommonException(ExceptionCode errorCode) {
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        this.TimeStamp = new Date();
    }

    public CommonException(ExceptionCode errorCode, long id) {
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
        this.httpStatus = HttpStatus.BAD_REQUEST;
        this.errorValueType = ExceptionCode.ID;
        this.errorLongValue = id;
        this.TimeStamp = new Date();
    }

    public CommonException(ExceptionCode errorCode, String value) {
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
        this.httpStatus = HttpStatus.BAD_REQUEST;
        this.errorValueType = ExceptionCode.StringData;
        this.errorStrValue = value;
        this.TimeStamp = new Date();
    }
}
