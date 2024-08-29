package com.sparta.my_only_schedule_app.dto.schedule.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ScheduleReadRequestDto {
    @Nullable
    @Size(min = 1, max = 20, message = "작성 유저명은 20자 이하이어야 합니다.")
    private String createrName;

    @Nullable
    @Size(min = 1, max = 100, message = "일정 제목은 100자 이하이어야 합니다.")
    private String title;

    @Nullable
    @Size(min = 1, max = 200, message = "일정 내용은 200자 이하이어야 합니다.")
    private String toDo;

    @Nullable
    @Min(value = 0, message = "댓글 개수는 양의 정수이어야 합니다.")
    private Long commentCnt;

    @Nullable
    private LocalDateTime createdAt;

    @Nullable
    private LocalDateTime modifiedAt;

    public ScheduleReadRequestDto(@Nullable String createrName, @Nullable String title, @Nullable String toDo, @Nullable Long commentCnt, @Nullable LocalDateTime createdAt, @Nullable LocalDateTime modifiedAt) {
        this.createrName = createrName;
        this.title = title;
        this.toDo = toDo;
        this.commentCnt = commentCnt;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
