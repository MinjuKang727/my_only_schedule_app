package com.sparta.my_only_schedule_app.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {
    ID, StringData,
    INVALID_SC_ID("존재하지 않는 일정 고유 번호입니다."),
    INVALID_CO_ID("존재하지 않는 댓글 고유 번호입니다."),
    SAVE_FAILED("저장이 정상적으로 되지 않았습니다."),
    NO_RESULT("조회된 결과가 존재하지 않습니다."),
    NO_SUCH_SCHEDULE("해당 고유 번호와 작성 유저명을 가진 일정이 존재하지 않습니다."),
    NO_SUCH_COMMENT("해당 고유 번호와 작성 유저명을 가진 댓글이 존재하지 않습니다."),
    INVALID_SC_CREATER("일정 수정 및 삭제는 작성 유저만 가능합니다."),
    INVALID_CO_CREATER("댓글 수정 및 삭제는 작성 유저만 가능합니다.");

    private String message;

    ExceptionCode() {
    }

    ExceptionCode(String message) {
        this.message = message;
    }
}
