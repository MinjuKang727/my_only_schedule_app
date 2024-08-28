package com.sparta.my_only_schedule_app.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {
    ID, StringData,
    INVALID_SC_ID("존재하지 않는 일정 고유 번호입니다."),
    SAVE_FAILED("저장이 정상적으로 되지 않았습니다.");

    private String message;

    ExceptionCode() {
    }

    ExceptionCode(String message) {
        this.message = message;
    }
}
